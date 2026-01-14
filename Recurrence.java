import java.time.LocalDate;

public class Recurrence {
    private int eventId;
    private String interval; // e.g., "1d", "1w", "1m"
    private int times;       // 0 if using end date
    private LocalDate endDate; // null if using times

    public Recurrence(int eventId, String interval, int times, LocalDate endDate) {
        this.eventId = eventId;
        this.interval = interval;
        this.times = times;
        this.endDate = endDate;
    }

    public String toCSV() {
        String dateStr = (endDate == null) ? "0" : endDate.toString();
        return eventId + "," + interval + "," + times + "," + dateStr;
    }

    // Getters
    public int getEventId() { return eventId; }
    public String getInterval() { return interval; }
    public int getTimes() { return times; }
    public LocalDate getEndDate() { return endDate; }
}
