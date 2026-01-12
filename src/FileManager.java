import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    /**
     * Creates a backup of event.csv and recurrent.csv into a timestamped folder.
     */
    public static void createBackup() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String backupFolder = "backup_" + timestamp;

        try {
            // Create backup directory
            Path backupPath = Paths.get(backupFolder);
            Files.createDirectories(backupPath);

            // Copy CSV files into the backup folder
            Files.copy(Paths.get("event.csv"), Paths.get(backupFolder, "event.csv"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("recurrent.csv"), Paths.get(backupFolder, "recurrent.csv"), StandardCopyOption.REPLACE_EXISTING);

            // Print absolute path as required by FOP
            System.out.println("Backup has been completed to " + new File(backupFolder).getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }

    /**
     * Restores event.csv and recurrent.csv from a given backup folder.
     */
    public static void restoreFromBackup(String backupFolderName) {
        try {
            // Restore files from the backup folder
            Files.copy(Paths.get(backupFolderName, "event.csv"), Paths.get("event.csv"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(backupFolderName, "recurrent.csv"), Paths.get("recurrent.csv"), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Restore completed from " + backupFolderName);

        } catch (IOException e) {
            System.out.println("Error during restore: " + e.getMessage());
        }
    }
}