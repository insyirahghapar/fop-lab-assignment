import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Calendar Application ===");
System.out.println("1. Backup Data");
System.out.println("2. Restore Data");
System.out.println("3. Search Events by Date");
System.out.println("4. Search Events by Date Range");
System.out.print("Choose: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                FileManager.createBackup();
                break;
            case 2:
                System.out.print("Enter backup folder name: ");
                String folderName = scanner.next();
                FileManager.restoreFromBackup(folderName);
                break;
            case 3:
                System.out.print("Enter date (YYYY-MM-DD): ");
                String date = scanner.next();
                SearchManager.searchByDate(date);
                break;
            case 4:
                System.out.print("Start date (YYYY-MM-DD): ");
                String startDate = scanner.next();
                System.out.print("End date (YYYY-MM-DD): ");
                String endDate = scanner.next();
                SearchManager.searchByDateRange(startDate, endDate);
                break;
            default:
                System.out.println("Invalid option.");
        }

        scanner.close();
    }
}