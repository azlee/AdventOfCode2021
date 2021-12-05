import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Day1 {
  public static void main(String[] args) {
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      int num = Integer.parseInt(fileContents.get(0));
      int numTimes = 0;
      int prev = Integer.parseInt(fileContents.get(0)) + Integer.parseInt(fileContents.get(1)) + Integer.parseInt(fileContents.get(2));
      for (int i = 1; i + 2 < fileContents.size(); i++) {
        int curr = Integer.parseInt(fileContents.get(i)) + Integer.parseInt(fileContents.get(i + 1)) + Integer.parseInt(fileContents.get(i + 2));
        if (curr > prev) {
          numTimes++;
        }
        prev = curr;
      }
      System.out.println(numTimes);
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
}