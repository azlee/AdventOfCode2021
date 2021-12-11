import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {

  int flashes = 0;

  int[][] directions = {
    {1,0},
    {-1,0},
    {0,1},
    {0,-1},
    {1,1},
    {-1,1},
    {-1,-1},
    {1,-1}
  };

  void printArr(int[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        System.out.print(arr[i][j]);
      }
      System.out.println("");
    }
    System.out.println("============================");
  }

  int[][] getFlashesInStep(int[][] arr) {
    Queue<int[]> flashedOcto = new LinkedList<>();
    List<int[]> octopusToReset = new ArrayList<>();
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        if (arr[i][j] == 10) continue;
        arr[i][j]++;
        if (arr[i][j] == 10) {
          flashes++;
          int[] octopus = {i, j};
          flashedOcto.add(octopus);
        }
      }
    }
    while (!flashedOcto.isEmpty()){
      int[] octopus = flashedOcto.remove();
      octopusToReset.add(octopus);
      for (int[] dir : directions) {
        int x = octopus[0] + dir[0];
        int y = octopus[1] + dir[1];
        int[] adjOcto = {x, y};
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9 && arr[x][y] != 10) {
          arr[x][y]++;
          if (arr[x][y] == 10) {
            flashes++;
            flashedOcto.add(adjOcto);
            octopusToReset.add(octopus);
          }
        }
      }
    }
    // reset all octopus that flashed to 0
    for (int[] octo : octopusToReset) {
      arr[octo[0]][octo[1]] = 0;
    }
    return arr;
  }

  int getTotalFlashes() {
    int[][] arr = parseFile();
    for (int i = 0; i < 100; i++) {
      arr = getFlashesInStep(arr);
    }
    return flashes;
  }

  int[][] parseFile() {
    int[][] arr = new int[10][10];
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      int i = 0;
      for (String str : fileContents) {
        for (int j = 0; j < str.length(); j++) {
          arr[i][j] = str.charAt(j) - '0';
        }
        i++;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return arr;
  }
}

class Day11 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.getTotalFlashes());
  }
}
