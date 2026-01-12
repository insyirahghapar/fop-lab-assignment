import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SearchManager {

    /**
     * Searches for events on a specific date (YYYY-MM-DD).
     */
    public static void searchByDate(String targetDate) {
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("event.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("eventId")) {
                    continue; // Skip header
                }

                String[] parts = line.split(",", 5);
                if (parts.length < 5) {
                    continue; // Skip incomplete lines
                }

                try {
                    LocalDateTime eventStart = LocalDateTime.parse(parts[3]);
                    String eventDate = eventStart.toLocalDate().toString();

                    if (eventDate.equals(targetDate)) {
                        String eventName = parts[1];
                        String eventTime = eventStart.toLocalTime().toString().substring(0, 5);
                        System.out.println(eventName + " (" + eventTime + ")");
                        found = true;
                    }
                } catch (DateTimeParseException e) {
                    // Skip lines with invalid date format
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Cannot read event.csv");
        }

        if (!found) {
            System.out.println("No events found on " + targetDate);
        }
    }

    /**
     * Searches for events within a date range (inclusive).
     */
    public static void searchByDateRange(String startDate, String endDate) {
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("event.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("eventId")) {
                    continue;
                }

                String[] parts = line.split(",", 5);
                if (parts.length < 5) {
                    continue;
                }

                try {
                    LocalDateTime eventStart = LocalDateTime.parse(parts[3]);
                    String eventDate = eventStart.toLocalDate().toString();

                    if (eventDate.compareTo(startDate) >= 0 && eventDate.compareTo(endDate) <= 0) {
                        String eventName = parts[1];
                        String eventTime = eventStart.toLocalTime().toString().substring(0, 5);
                        System.out.println(eventName + " (" + eventTime + ")");
                        found = true;
                    }
                } catch (DateTimeParseException e) {
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Cannot read event.csv");
        }

        if (!found) {
            System.out.println("No events found between " + startDate + " and " + endDate);
        }
    }
}