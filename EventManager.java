import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventManager {
    private static final String FILE_NAME = "event.csv";
    private static final String RECURRENT_FILE = "recurrent.csv";
    private List<Event> eventList;
    private List<Recurrence> recurrenceList;

    public List<Event> getAllEvents(){
        return eventList;
    }

    public EventManager() {
        this.eventList = new ArrayList<>();
        this.recurrenceList = new ArrayList<>();
        loadEvents(); // Load existing data when program starts
        loadRecurrences(); // Load recurrence rules on startup
    }

    public boolean isConflicting(LocalDateTime newStart, LocalDateTime newEnd) {
        for (Event existingEvent : eventList) {
            // a conflict occurs if:
            // (NewStart < ExistingEnd) AND (NewEnd > ExistingStart)
            if (newStart.isBefore(existingEvent.getEndDateTime()) && 
                newEnd.isAfter(existingEvent.getStartDateTime())) {
            
                System.out.println("Conflict Detected with Event: " + existingEvent.getTitle());
                return true; // there is a clash
            }
        }
        return false; // no clash found
    }

    // --- REQUIREMENT 1: EVENT CREATION ---
    public void createEvent(String title, String description, LocalDateTime start, LocalDateTime end) {
        if (isConflicting(start, end)) {
            System.out.println("Could not create event. Time slot is already occupied.");
            return; // Stop the method here
        }
        
        Event newEvent = new Event(title, description, start, end);
        // Auto-Increment Logic: Find max ID and add 1
        int maxId = 0;
        for (Event e : eventList) {
            if (e.getId() > maxId) {
                maxId = e.getId();
            }
        }
        newEvent.setId(maxId + 1);
        
        eventList.add(newEvent);
        saveEvents(); // Persist to file immediately
        System.out.println("Event created successfully with ID: " + newEvent.getId());
    }

    // --- REQUIREMENT 2: EVENT UPDATE ---
    public void updateEvent(int id, String newTitle, String newDesc, LocalDateTime newStart, LocalDateTime newEnd) {
        Event eventToUpdate = findEventById(id);
        if (eventToUpdate != null) {
            eventToUpdate.setTitle(newTitle);
            eventToUpdate.setDescription(newDesc);
            eventToUpdate.setStartDateTime(newStart);
            eventToUpdate.setEndDateTime(newEnd);
            saveEvents(); // Update file
            System.out.println("Event ID " + id + " updated successfully.");
        } else {
            System.out.println("Error: Event ID " + id + " not found.");
        }
    }

    // --- REQUIREMENT 2: EVENT DELETE ---
    public void deleteEvent(int id) {
        Event eventToDelete = findEventById(id);
        if (eventToDelete != null) {
            eventList.remove(eventToDelete);
            saveEvents(); // Rewrite file without this event
            System.out.println("Event ID " + id + " deleted.");
        } else {
            System.out.println("Error: Event ID " + id + " not found.");
        }
    }

    // Helper: Find event object by ID
    public Event findEventById(int id) {
        for (Event e : eventList) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    // Helper: Display all events
    public void listAllEvents() {
        if (eventList.isEmpty()) {
            System.out.println("No events found.");
        } else {
            System.out.println("--- All Events ---");
            for (Event e : eventList) {
                System.out.println(e);
            }
        }
    }

    public void createRecurringEvent(String title, String description, LocalDateTime start, 
                                 LocalDateTime end, String interval, int times, LocalDate endDate) {
    
        // create the base event first to get an ID
        createEvent(title, description, start, end);
        int newId = eventList.get(eventList.size() - 1).getId();

        // create the recurrence rule linked by that ID 
        Recurrence newRec = new Recurrence(newId, interval, times, endDate);
        recurrenceList.add(newRec);
    
        saveRecurrences(); // persist to recurrent.csv 
        System.out.println("Recurrence rule added for Event ID: " + newId);
    }
    // --- FILE I/O OPERATIONS ---
    
    // Load events from CSV to Memory (ArrayList)
    private void loadEvents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return; // Nothing to load if file doesn't exist

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Skip header if you have one, or handle logic
            br.readLine(); // Assuming first line is header: eventId, title...
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) { // Ensure valid row
                    int id = Integer.parseInt(data[0]);
                    String title = data[1];
                    String desc = data[2];
                    LocalDateTime start = LocalDateTime.parse(data[3], Event.FORMATTER);
                    LocalDateTime end = LocalDateTime.parse(data[4], Event.FORMATTER);
                    
                    eventList.add(new Event(id, title, desc, start, end));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading events: " + e.getMessage());
        }
    }
    private void loadRecurrences() {
        File file = new File(RECURRENT_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String interval = data[1].trim();
                int times = Integer.parseInt(data[2].trim());
                LocalDate endD = data[3].trim().equals("0") ? null : LocalDate.parse(data[3].trim());
            
                recurrenceList.add(new Recurrence(id, interval, times, endD));
            }
        } catch (IOException e) {
            System.out.println("Error loading recurrences: " + e.getMessage());
        }
    }

    // Save Memory (ArrayList) to CSV
    private void saveEvents() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // Write Header
            bw.write("eventId,title,description,startDateTime,endDateTime");
            bw.newLine();
            
            for (Event e : eventList) {
                bw.write(e.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving events: " + e.getMessage());
        }
    }
     private void saveRecurrences() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RECURRENT_FILE))) {
            bw.write("eventId,recurrentInterval,recurrentTimes,recurrentEndDate");
            bw.newLine();
            for (Recurrence r : recurrenceList) {
                bw.write(r.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving recurrences: " + e.getMessage());
        }
    }
}


