import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    public static void createBackup() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String backupFolder = "backup_" + timestamp;

        try {
            Path backupPath = Paths.get(backupFolder);
            Files.createDirectories(backupPath);

            Files.copy(Paths.get("event.csv"), Paths.get(backupFolder, "event.csv"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get("recurrent.csv"), Paths.get(backupFolder, "recurrent.csv"), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Backup has been completed to " + new File(backupFolder).getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }

    public static boolean restoreFromBackup(String folderName) {
        try {
            Files.copy(Paths.get(folderName, "event.csv"), Paths.get("event.csv"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(folderName, "recurrent.csv"), Paths.get("recurrent.csv"), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Restore completed from " + folderName);
            return true;
        } catch (IOException e) {
            System.out.println("Error during restore: " + e.getMessage());
            return false
        }
    }
}
