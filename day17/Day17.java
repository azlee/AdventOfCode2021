import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Day17 {
 public static void main(String[] args) {
    try {
      Path path = Paths.get("input.txt");
      String line = Files.readAllLines(path).get(0);
      String[] splitStr = line.split("=");
      String[] xValues = splitStr[1].split(",")[0].split("\\.\\.");
      String[] yValues = splitStr[2].split("\\.\\.");
      Integer y1 = Integer.parseInt(yValues[0]);
      System.out.println(Math.abs(y1) * (Math.abs(y1) - 1) / 2);
    } catch (Exception e) {
      System.out.println(e);
    } 
  }
}
