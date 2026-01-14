package ReminderManager;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

public class ReminderManager {
    private final EventManager eventManager;

    public ReminderManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void checkAllReminders() {
        System.out.println("\n=== 🔔 Event Reminder Check 🔔 ===");
        LocalDateTime now = LocalDateTime.now();
        List<Event> allEvents = eventManager.listAllEvents();
        boolean hasRemind = false;

        for (Event event : allEvents) {
            if (event.getStartDateTime().isAfter(now) && event.getReminderLeadTime() > 0) {
                long leftMinutes = Duration.between(now, event.getStartDateTime()).toMinutes();
                if (leftMinutes <= event.getReminderLeadTime()) {
                    System.out.println("\n📅 Upcoming Event Alert:");
                    System.out.println(event);
                    System.out.println("⏰ Time Left: " + leftMinutes + " minutes");
                    hasRemind = true;
                }
            }
        }
        if (!hasRemind) {
            System.out.println("✅ No upcoming event reminders.");
        }
        System.out.println("===================================\n");
    }
}
