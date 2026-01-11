import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private int id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

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

    // Convert Event object to CSV String format
    public String toCSV() {
        return id + "," + title + "," + description + "," + 
               startDateTime.format(FORMATTER) + "," + endDateTime.format(FORMATTER);
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

    @Override
    public String toString() {
        return String.format("ID: %d | %s (%s) [%s to %s]", 
               id, title, description, startDateTime.format(FORMATTER), endDateTime.format(FORMATTER));
    }
}
