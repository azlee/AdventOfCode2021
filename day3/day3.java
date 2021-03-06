import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Solution {
  int[] calculateGammaEpsilon(int[] numberOfOnes, int length) {
    int[] gammaEpsilon = new int[2];
    int base = 1;
    for (int i = numberOfOnes.length - 1; i >= 0; i--) {
      if (numberOfOnes[i] > length / 2) {
        gammaEpsilon[0] += base;
      } else {
        gammaEpsilon[1] += base;
      }
      base *= 2;
    }
    return gammaEpsilon;
  }

  int getPower() {
    int power = 0;
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      int[] numberOfOnes = new int[fileContents.get(0).length()];
      for (int i = 0; i < fileContents.size(); i++) {
        String line = fileContents.get(i);
        for (int j = 0; j < line.length(); j++) {
          numberOfOnes[j] += line.charAt(j) - '0';
        }
      }
      int[] gammaEpsilon = calculateGammaEpsilon(numberOfOnes, fileContents.size());
      power = gammaEpsilon[0] * gammaEpsilon[1];
    } catch (Exception ex) {
      System.out.println(ex);
    }
    return power;
  }
}

class Day3 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.getPower());  
  }
}
