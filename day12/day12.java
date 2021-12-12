import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Solution {
  private Map<String, LinkedList<String>> adj;
  Integer numPaths = 0;

  boolean isBigCave(String str) {
    char c = str.charAt(0);
    return c <= 'Z' && c >= 'A';
  }

  void DFSUtil(String curr, Set<String> visited) {
    if (curr.equals("end")) {
      numPaths++;
      return;
    }
    if (!isBigCave(curr)) {
      visited.add(curr);
    }
    Iterator<String> i = adj.get(curr).listIterator();
    while (i.hasNext()) {
      String n = i.next();
      if (!visited.contains(n)) {
        Set<String> newVisited = new HashSet<>();
        newVisited.addAll(visited);
        DFSUtil(n, newVisited);
      }
    }
  }

  int findNumPaths() {
    parseFile();
    Set<String> visited = new HashSet<>();
    DFSUtil("start", visited);
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
        neighbors.add(fromTo[1]);
        LinkedList<String> neighbors2 = adj.getOrDefault(fromTo[1], new LinkedList<>());
        neighbors2.add(fromTo[0]);
        adj.put(fromTo[0], neighbors);
        adj.put(fromTo[1], neighbors2);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}

class Day12 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.findNumPaths());
  }
}
