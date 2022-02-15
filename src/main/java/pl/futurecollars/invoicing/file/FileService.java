package pl.futurecollars.invoicing.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileService {

  private final String filePath;

  public FileService(String filePath) {
    this.filePath = filePath;
  }

  private void writeToFile(String line, String path) {
    try {
      Files.writeString(Paths.get(path), line + "\n", StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<String> readFromDatabase() {
    try {
      return Files.readAllLines(Paths.get(filePath));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void writeToDatabase(String line) {
    writeToFile(line, filePath);
  }

  public void clearDatabase() {
    try {
      Files.writeString(Paths.get(filePath), "");
      Files.writeString(Paths.get(filePath), "");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}