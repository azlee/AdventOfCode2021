import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Day9 {

  static boolean isLowPoint(List<String> content, int i, int j) {
    int point = content.get(i).charAt(j) - '0';
    // up
    if (i >  0 && content.get(i-1).charAt(j) - '0' <= point) {
      return false;
    }
    // down
    if (i + 1 <  content.size() && content.get(i+1).charAt(j) - '0' <= point) {
      return false;
    }

    // left
    if (j > 0 && content.get(i).charAt(j-1) - '0' <= point) {
      return false;
    }

    // right
    if (j + 1 < content.get(i).length() && content.get(i).charAt(j+1) - '0' <= point) {
      return false;
    }

    return true;
  }
  public static void main(String[] args) {
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      int riskLevelSum = 0;
      for (int i = 0; i < fileContents.size(); i++) {
        String line = fileContents.get(i);
        for (int j = 0; j < line.length(); j++) {
          if (isLowPoint(fileContents, i, j)) {
            riskLevelSum += line.charAt(j) - '0' + 1;
          }
        }
      }
      System.out.println("riskLevel is " + riskLevelSum);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
