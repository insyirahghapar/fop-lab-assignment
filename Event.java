import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private int id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
//
    private String location;
    private String attendees;
    private String category;
    private int reminderLeadTime;
    
    // Formatter for "2025-10-05T11:00:00" format
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Constructor for new events (ID auto-generated later)
    public Event(String title, String description, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.description = description;
        this.startDateTime = start;
        this.endDateTime = end;
    }

    // Constructor for loading from CSV (ID already exists)
    public Event(int id, String title, String description, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDateTime = start;
        this.endDateTime = end;
    }
    
    //
    public Event(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        this.id = Integer.parseInt(parts[0].trim());
        this.title = parts[1].trim();
        this.description = parts[2].trim();
        this.startDateTime = LocalDateTime.parse(parts[3].trim(), FORMATTER);
        this.endDateTime = LocalDateTime.parse(parts[4].trim(), FORMATTER);
        this.location = parts.length >=6 && !parts[5].isEmpty() ? parts[5].trim() : "N/A";
        this.attendees = parts.length >=7 && !parts[6].isEmpty() ? parts[6].trim() : "None";
        this.category = parts.length >=8 && !parts[7].isEmpty() ? parts[7].trim() : "Uncategorized";
        this.reminderLeadTime = parts.length >=9 && !parts[8].isEmpty() ? Integer.parseInt(parts[8].trim()) : 0;
    }    

    // Convert Event object to CSV String format
    public String toCSV() {
        //
        return String.join(",",String.valueOf(id),title,description,startDateTime.format(FORMATTER),
                           endDateTime.format(FORMATTER),location,attendees,category,String.valueOf(reminderLeadTime));
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String desc) { this.description = desc; }
    
    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime start) { this.startDateTime = start; }
    
    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime end) { this.endDateTime = end; }

    //
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getAttendees() { return attendees; }
    public void setAttendees(String attendees) { this.attendees = attendees; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getReminderLeadTime() { return reminderLeadTime; }
    public void setReminderLeadTime(int reminderLeadTime) { this.reminderLeadTime = Math.max(0, reminderLeadTime); }

    @Override
    public String toString() {
    return String.format("ID: %d | %s (%s) | Location: %s | Attendees: %s | Category: %s | Time: %s ~ %s | Reminder: %d mins",
        id, title, description, location, attendees, category,
        startDateTime.format(FORMATTER), endDateTime.format(FORMATTER),
        reminderLeadTime);
    }
}

