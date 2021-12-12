import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {

  private Map<String, LinkedList<String>> adj;
  Integer numPaths = 0;

  boolean isBigCave(String str) {
    char c = str.charAt(0);
    return c <= 'Z' && c >= 'A';
  }

  void DFSUtil(String curr, Map<String, Integer> smallCaveVisits, boolean caveVisitedTwice) {
    if (curr.equals("end")) {
      numPaths++;
      return;
    }
    if (!isBigCave(curr)) {
      int numVisitsCave = smallCaveVisits.getOrDefault(curr, 0) + 1;
      if (numVisitsCave == 2) {
        caveVisitedTwice = true;
      } else if (numVisitsCave > 2) {
        return;
      }
      smallCaveVisits.put(curr, numVisitsCave);
    }
    Iterator<String> i = adj.get(curr).listIterator();
    while (i.hasNext()) {
      String n = i.next();
      Map<String, Integer> newVisited = smallCaveVisits.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
      if (smallCaveVisits.getOrDefault(n, 0) < 1 || !caveVisitedTwice) {
        DFSUtil(n, newVisited, caveVisitedTwice);
      }
    }
  }

  int findNumPaths() {
    parseFile();
    Map<String, Integer> visited = new HashMap<>();
    DFSUtil("start", visited, false);
    return numPaths;
  }

  void parseFile() {
    this.adj = new HashMap<>();
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      for (String line : fileContents) {
        String[] fromTo = line.split("-");
        LinkedList<String> neighbors = adj.getOrDefault(fromTo[0], new LinkedList<>());
        if (!fromTo[1].equals("start")) {
          neighbors.add(fromTo[1]);
        }
        LinkedList<String> neighbors2 = adj.getOrDefault(fromTo[1], new LinkedList<>());
        if (!fromTo[0].equals("start")) {
          neighbors2.add(fromTo[0]);
        }
        adj.put(fromTo[0], neighbors);
        adj.put(fromTo[1], neighbors2);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}

class Day12Part2 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    solution.findNumPaths();
    System.out.println(solution.numPaths);
  }
}
