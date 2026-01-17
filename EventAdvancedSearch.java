import java.util.ArrayList;
import java.util.List;

public class EventAdvancedSearch {

    public static List<Event> search(
            List<Event> events,
            EventSearchCriteria criteria
    ) {

        List<Event> results = new ArrayList<>();

        for (Event event : events) {

            if (!matchId(event, criteria)) continue;
            if (!matchTitle(event, criteria)) continue;
            if (!matchDescription(event, criteria)) continue;
            if (!matchLocation(event, criteria)) continue;
            if (!matchAttendees(event, criteria)) continue;
            if (!matchCategory(event, criteria)) continue;

            results.add(event);
        }

        return results;
    }

    private static boolean matchId(Event e, EventSearchCriteria c) {
        if (c.getId() == null)
            return true;

        return e.getId() == c.getId();
    }

    private static boolean matchTitle(Event e, EventSearchCriteria c) {
        if (c.getTitle() == null || c.getTitle().isEmpty())
            return true;

        return e.getTitle()
                .toLowerCase()
                .contains(c.getTitle().toLowerCase());
    }

    private static boolean matchDescription(Event e, EventSearchCriteria c) {
        if (c.getDescription() == null || c.getDescription().isEmpty())
            return true;

        return e.getDescription()
                .toLowerCase()
                .contains(c.getDescription().toLowerCase());
    }

    private static boolean matchLocation(Event e, EventSearchCriteria c){
        if (c.getLocation() == null || c.getLocation().isEmpty())
            return true;

        return e.getLocation()
                .toLowerCase()
                .contains(c.getLocation().toLowerCase());
    }
    private static boolean matchAttendees(Event e, EventSearchCriteria c){
        if (c.getAttendees() == null || c.getAttendees().isEmpty())
            return true;

        return e.getAttendees()
                .toLowerCase()
                .contains(c.getAttendees().toLowerCase());
    }
    private static boolean matchCategory(Event e, EventSearchCriteria c){
        if (c.getCategory() == null || c.getCategory().isEmpty())
            return true;

        return e.getCategory()
                .toLowerCase()
                .contains(c.getCategory().toLowerCase());
    }
}
