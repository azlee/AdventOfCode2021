import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

class Day5Part2 {

  int[] getDirection(int x1, int y1, int x2, int y2) {
    if (x1 == x2 && y2 > y1) {
      int[] arr = {0, 1};
      return arr;
    } else if (x1 == x2 && y2 < y1) {
      int[] arr = {0, -1};
      return arr;
    } else if (y1 == y2 && x2 > x1) {
      int[] arr = {1, 0};
      return arr;
    } else if (y1 == y2 && x2 < x1) {
      int[] arr = {-1, 0};
      return arr;
    } else if (y2 > y1 && x2 > x1) {
      int[] arr = {1, 1};
      return arr;
    } else if (y2 < y1 && x2 < x1) {
      int[] arr = {-1, -1};
      return arr;
    } else if (y2 > y1 && x2 < x1) {
      int[] arr = {-1, 1};
      return arr;
    } else if (y2 < y1 && x2 > x1) {
      int[] arr = {1, -1};
      return arr;
    }
    int[] arr = {0, 0};
    return arr;
  }
  public static void main(String[] args) {
    try {
      Day5Part2 solution = new Day5Part2();
      Path path = Paths.get("input.txt");
      int numDangerZones = 0;
      List<String> fileContents = Files.readAllLines(path);
      HashMap<Integer, HashMap<Integer, Integer>> diagram = new HashMap<>();
      for (String line : fileContents) {
        String[] splitStr = line.split("->");
        String coords1 = splitStr[0];
        String coords2 = splitStr[1];
        String[] coordinates1 = coords1.split(",");
        String[] coordinates2 = coords2.split(",");
        int x1 = Integer.parseInt(coordinates1[0].trim());
        int y1 = Integer.parseInt(coordinates1[1].trim());
        int x2 = Integer.parseInt(coordinates2[0].trim());
        int y2 = Integer.parseInt(coordinates2[1].trim());
        int[] dir = solution.getDirection(x1, y1, x2, y2);
        while (x1 != x2 || y1 != y2) {
          HashMap<Integer, Integer> map = diagram.getOrDefault(x1, new HashMap<>());
          map.put(y1, map.getOrDefault(y1, 0) + 1);
          diagram.put(x1, map);
          if (diagram.get(x1).get(y1) == 2) {
            numDangerZones++;
          }
          x1 = x1 + dir[0];
          y1 = y1 + dir[1];
        }
        HashMap<Integer, Integer> map = diagram.getOrDefault(x1, new HashMap<>());
        map.put(y1, map.getOrDefault(y1, 0) + 1);
        diagram.put(x1, map);
        if (diagram.get(x1).get(y1) == 2) {
          numDangerZones++;
        }
      }
      System.out.println("numDangerZones is " + numDangerZones);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}