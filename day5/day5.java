import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

class Day5 {
  public static void main(String[] args) {
    try {
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
        if (x1 == x2) {
          for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
            HashMap<Integer, Integer> map = diagram.getOrDefault(i, new HashMap<>());
            map.put(x1, map.getOrDefault(x1, 0) + 1);
            diagram.put(i, map);
            if (diagram.get(i).get(x1) == 2) {
              numDangerZones++;
            }
          }
        } else if (y1 == y2) {
          for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
            HashMap<Integer, Integer> map = diagram.getOrDefault(y1, new HashMap<>());
            map.put(i, map.getOrDefault(i, 0) + 1);
            diagram.put(y1, map);
            if (diagram.get(y1).get(i) == 2) {
              numDangerZones++;
            }
          }
        }
      }
      System.out.println("numDangerZones is " + numDangerZones);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}