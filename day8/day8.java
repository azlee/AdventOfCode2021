import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Day8 {

  static int getDigit(long size) {
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

  static long countUniqChar(String str) {
    return str.chars()
              .distinct()
              .count();
  }
  public static void main(String[] args) {
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
      System.out.println(numbers[1] + numbers[4] + numbers[7] + numbers[8]);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
