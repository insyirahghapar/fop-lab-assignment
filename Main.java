import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventManager manager = new EventManager();
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Welcome to the Calendar App!");

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. View Calendar by Week");
            System.out.println("2. View Calender by Month");
            System.out.println("3. Create Event");
            System.out.println("4. View All Events");
            System.out.println("5. Update Event");
            System.out.println("6. Delete Event");
            System.out.println("7. Exit");
            System.out.println("8. Backup Data");
            System.out.println("9. Restore Data");
            System.out.println("10. Search by Date");
            System.out.println("11. Search by Date Range");
            System.out.println("12. Advanced Search");
            System.out.print("Choose an option: ");
            input = scanner.nextLine();

            switch (input) {
                case "1"://view calendar by week
                     while(true){
                try{
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    System.out.print("Enter week number (1 - 52): ");
                    int week = scanner.nextInt();     
                    DisplayCalendar displayCalendar = new DisplayCalendar(year,week,0,manager);
                    displayCalendar.displayWeek(week);
                    break;
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage() + " Please try again.");
                }}
                break;

                case "2"://view calendar by month
                    while(true){
                try{
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    System.out.print("Enter month number (1 - 12): ");
                    int month = scanner.nextInt();     
                    DisplayCalendar displayCalendar = new DisplayCalendar(year,0,month,manager);
                    displayCalendar.displayMonthly(month,year);
                    break;
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage() + " Please try again.");
                }}
                break;

                case "3": // Create
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String desc = scanner.nextLine();
                    LocalDateTime start = promptForDate(scanner, "Start (yyyy-MM-ddTHH:mm:ss): ");
                    LocalDateTime end = promptForDate(scanner, "End   (yyyy-MM-ddTHH:mm:ss): ");
                    
                    if (start != null && end != null) {
                        manager.createEvent(title, desc, start, end);
                    }
                    break;

                case "4": // Read
                    manager.listAllEvents();
                    break;

                case "5": // Update
                    manager.listAllEvents();
                    System.out.print("Enter Event ID to update: ");
                    try {
                        int updateId = Integer.parseInt(scanner.nextLine());
                        Event existing = manager.findEventById(updateId);
                        if (existing != null) {
                            System.out.print("New Title (" + existing.getTitle() + "): ");
                            String newTitle = scanner.nextLine();
                            System.out.print("New Desc (" + existing.getDescription() + "): ");
                            String newDesc = scanner.nextLine();
                            LocalDateTime newStart = promptForDate(scanner, "New Start: ");
                            LocalDateTime newEnd = promptForDate(scanner, "New End: ");
                            manager.updateEvent(updateId, newTitle, newDesc, newStart, newEnd);
                        } else {
                            System.out.println("ID not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                    }
                    break;

                case "6": // Delete
                    manager.listAllEvents();
                    System.out.print("Enter Event ID to delete: ");
                    try {
                        int deleteId = Integer.parseInt(scanner.nextLine());
                        manager.deleteEvent(deleteId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                    }
                    break;

                case "7": // Exit
                    System.out.println("Exiting...");
                    return;

                case "8": // Backup Data (by SZA)
                    FileManager.createBackup();
                    break;

                case "9": // Restore Data (by SZA)
                    System.out.print("Enter backup folder name: ");
                    String folderName = scanner.nextLine();
                    FileManager.restoreFromBackup(folderName);
                    break;

                case "10": // Search by Date (by SZA)
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    SearchManager.searchByDate(date);
                    break;

                case "11": // Search by Date Range (by SZA)
                    System.out.print("Start date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("End date (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    SearchManager.searchByDateRange(startDate, endDate);
                    break;

                case "12":
                    EventManager eventManager = new EventManager();
                    List<Event> events = eventManager.getAllEvents(); 

                    Scanner sc = new Scanner(System.in);
                    EventSearchCriteria criteria = new EventSearchCriteria();

                    System.out.print("Search by Event ID (press Enter to skip): ");
                    String idInput = sc.nextLine();
                    if (!idInput.isEmpty()) {
                        criteria.setId(Integer.parseInt(idInput));
                    }

                    System.out.print("Search by Title (press Enter to skip): ");
                    criteria.setTitle(sc.nextLine());

                    System.out.print("Search by Description (press Enter to skip): ");
                    criteria.setDescription(sc.nextLine());

                    List<Event> results =
                            EventAdvancedSearch.search(events, criteria);

                    System.out.println("\n--- Advanced Search Results ---");
                    if (results.isEmpty()) {
                        System.out.println("No matching events found.");
                    } else {
                        for (Event e : results) {
                            System.out.println(e);
                        }
                    }

                    default:
                        System.out.println("Invalid option.");
                        }
                    }
                }

    // Helper to get date in a user-friendly way
    private static LocalDateTime promptForDate(Scanner sc, String label) {
        System.out.println("--- Enter " + label + " ---");
        
        int year = 0, month = 0, day = 0, hour = 0, minute = 0;
        
        while (true) {
            try {
                System.out.print("  Year (e.g., 2025): ");
                year = Integer.parseInt(sc.nextLine());
                
                System.out.print("  Month (1-12): ");
                month = Integer.parseInt(sc.nextLine());
                
                System.out.print("  Day (1-31): ");
                day = Integer.parseInt(sc.nextLine());
                
                System.out.print("  Hour (0-23): ");
                hour = Integer.parseInt(sc.nextLine());
                
                System.out.print("  Minute (0-59): ");
                minute = Integer.parseInt(sc.nextLine());
                
                return LocalDateTime.of(year, month, day, hour, minute);
                
            } catch (NumberFormatException e) {
                System.out.println("  Invalid number! Please try again.");
            } catch (Exception e) {
                System.out.println("  Invalid date (e.g., Feb 30 doesn't exist). Try again.");
            }
        }
    }
}



