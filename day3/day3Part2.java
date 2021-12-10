import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Solution {
  int convertBinaryToDecimal(String binary) {
    int base = 1;
    int ret = 0;
    for (int i = binary.length() - 1; i >= 0; i--) {
      ret += base * (binary.charAt(i) - '0');
      base *= 2;
    }
    return ret;
  }

  List<String> getMostSignificant(int[] numberOfOnes, List<String> nums, int i) {
    List<String> mostSignificant = new ArrayList<>();
    int mostSignificantDigit = numberOfOnes[i] > nums.size()/2 ? 1 : 0;
    if (numberOfOnes[i] * 2 == nums.size()) {
      mostSignificantDigit = 1;
    }
    for (String str : nums) {
      if (str.charAt(i) - '0' == mostSignificantDigit) {
        mostSignificant.add(str);
      }
    }
    return mostSignificant;
  }

  List<String> getLeastSignificant(int[] numberOfOnes, List<String> nums, int i) {
    List<String> leastSignificant = new ArrayList<>();
    for (String str : nums) {
      int leastSignificantDigit = numberOfOnes[i] <= nums.size()/2 ? 1 : 0;
      if (numberOfOnes[i] * 2 == nums.size()) {
        leastSignificantDigit = 0;
      }
      if (str.charAt(i) - '0' == leastSignificantDigit) {
        leastSignificant.add(str);
      }
    }
    return leastSignificant;
  }

  int[] getNumberOfOnes(List<String> binaryNums) {
    int[] numberOfOnes = new int[binaryNums.get(0).length()];
    for (int i = 0; i < binaryNums.size(); i++) {
      String line = binaryNums.get(i);
      for (int j = 0; j < line.length(); j++) {
        numberOfOnes[j] += line.charAt(j) - '0';
      }
    }
    return numberOfOnes;
  }

  getLifeSupport() {
    int lifeSupport = 0;
    try {
        Path path = Paths.get("input.txt");
        List<String> fileContents = Files.readAllLines(path);
        List<String> mostSignificant = new ArrayList<>(fileContents);
        List<String> leastSignificant = new ArrayList<>(fileContents);
        int i = 0;
        while (mostSignificant.size() != 1) {
          int[] numberOfOnes = getNumberOfOnes(mostSignificant);
          mostSignificant = new ArrayList<>(getMostSignificant(numberOfOnes, mostSignificant, i));
          i++;
        }
        i = 0;
        while (leastSignificant.size() != 1) {
          int[] numberOfOnes = getNumberOfOnes(leastSignificant);
          leastSignificant = new ArrayList<>(getLeastSignificant(numberOfOnes, leastSignificant, i));
          i++;
        }
        lifeSupport = convertBinaryToDecimal(mostSignificant.get(0)) * convertBinaryToDecimal(leastSignificant.get(0));
    } catch (Exception ex) {
        System.out.println(ex);
    }
    return lifeSupport;
  }
}

class Day3Part2 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.getLifeSupport());
  }
}
