import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Solution {
  boolean isLowPoint(List<String> content, int i, int j) {
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

  int getRiskLevelSum() {
    int riskLevelSum = 0;
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      for (int i = 0; i < fileContents.size(); i++) {
        String line = fileContents.get(i);
        for (int j = 0; j < line.length(); j++) {
          if (isLowPoint(fileContents, i, j)) {
            riskLevelSum += line.charAt(j) - '0' + 1;
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return riskLevelSum;
  }
}

class Day9 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.getRiskLevelSum());
  }
}
