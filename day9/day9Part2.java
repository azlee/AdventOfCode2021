import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
  static int basin1 = Integer.MIN_VALUE;
  static int basin2 = Integer.MIN_VALUE;
  static int basin3 = Integer.MIN_VALUE;

  static void addValue(int i) {
    int[] arr = { basin1, basin2, basin3, i };
    Arrays.sort(arr);
    basin1 = arr[1];
    basin2 = arr[2];
    basin3 = arr[3];
  }

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

  static int getSizeBasin(List<char[]> heightmap, int i, int j, int prev) {
    if (i < 0 || i >= heightmap.size() || j < 0 || j >= heightmap.get(i).length 
        || heightmap.get(i)[j] == '9' || heightmap.get(i)[j] == '\0') {
      return 0;
    }

    int current = heightmap.get(i)[j] - '0';
    int basinSides = 0;

    if (current > prev) {
      char[] arr = heightmap.get(i);
      arr[j] = '\0';
      heightmap.set(i, arr);
      basinSides += getSizeBasin(heightmap, i - 1, j, current);
      basinSides += getSizeBasin(heightmap, i + 1, j, current);
      basinSides += getSizeBasin(heightmap, i, j - 1, current);
      basinSides += getSizeBasin(heightmap, i, j + 1, current);
      return 1 + basinSides;
    }
    return 0;
  }
  int getBasin() {
    int basin = 0;
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      List<char[]> chars = new ArrayList<>();
      for (String str : fileContents) {
        chars.add(str.toCharArray());
      }
      for (int i = 0; i < chars.size(); i++) {
        char[] line = fileContents.get(i).toCharArray();
        for (int j = 0; j < line.length; j++) {
          if (isLowPoint(fileContents, i, j)) {
            int basinSize = getSizeBasin(chars, i, j, Integer.MIN_VALUE);
            addValue(basinSize);
          }
        }
      }
      basin = basin1 * basin2 * basin3;
    } catch (Exception e) {
      System.out.println(e);
    }
    return basin;
  }
}

class Day9Part2 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.getBasin());
  }
}
