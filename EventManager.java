import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventManager {
    private static final String FILE_NAME = "event.csv";
    private List<Event> eventList;

    public EventManager() {
        this.eventList = new ArrayList<>();
        loadEvents(); // Load existing data when program starts
    }

    // --- REQUIREMENT 1: EVENT CREATION ---
    public void createEvent(String title, String description, LocalDateTime start, LocalDateTime end) {
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
}
