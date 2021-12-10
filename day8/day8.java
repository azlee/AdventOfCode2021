import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Solution {
  int getDigit(long size) {
    if (size == 2) {
      return 1;
    } else if (size == 4) {
      return 4;
    } else if (size == 3) {
      return 7;
    } else if (size == 7) {
      return 8;
    }
    return -1;
  }

  long countUniqChar(String str) {
    return str.chars()
              .distinct()
              .count();
  }
  int getSum() {
    int sum = 0;
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      int[] numbers = new int[9];
      for (int i = 0; i < fileContents.size(); i++) {
        String[] calledNumbers = fileContents.get(i).split("\\|");
        String[] outputs = calledNumbers[1].trim().split(" ");
        for (int j = 0; j < outputs.length; j++) {
          long uniqueChar = countUniqChar(outputs[j]);
          int digit = getDigit(uniqueChar);
          if (digit == -1) continue;
          numbers[digit]++;
        }
      }
      sum = numbers[1] + numbers[4] + numbers[7] + numbers[8];
    } catch (Exception e) {
      System.out.println(e);
    }
    return sum;
  }
}

class Day8 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.getSum());
  }
}
