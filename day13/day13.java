import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
  String FOLD_PREFIX = "fold along";

  private int calcDots(Set<List<Integer>> paper, String axis, int value) {
    Set<List<Integer>> newPaper = new HashSet<>();
    for (List<Integer> coord : paper) {
      int x = coord.get(0);
      int y = coord.get(1);
      if (axis.equals("y")) {
        if (y >= value) {
          int distance = value - y;
          int newY = value + distance;
          List<Integer> newCoord = new ArrayList<>();
          newCoord.add(x);
          newCoord.add(newY);
          newPaper.add(newCoord);
        } else {
          newPaper.add(coord);
        }
      } else {
        if (x <= value) {
          int distance = value - x;
          int newX = value + distance;
          List<Integer> newCoord = new ArrayList<>();
          newCoord.add(newX);
          newCoord.add(y);
          newPaper.add(newCoord);
        } else {
          newPaper.add(coord);
        }
      }
    }
    return newPaper.size();
  } 

  int foldInput() {
    int numDots = 0;
    try {
        Path path = Paths.get("input.txt");
        List<String> fileContents = Files.readAllLines(path);
        Set<List<Integer>> paper = new HashSet<>();
        for (String line : fileContents) {
          if (line.equals("")) continue;
          if (!line.startsWith(FOLD_PREFIX)) {
            String[] coords = line.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            List<Integer> coord = new ArrayList<>();
            coord.add(x);
            coord.add(y);
            paper.add(coord);
          } else {
            String[] instr = line.split("=");
            String xOrY = instr[0].substring(instr[0].length() - 1);
            int value = Integer.parseInt(instr[1]);
            numDots = calcDots(paper, xOrY, value);
            return numDots;
          }
        }
    } catch (Exception ex) {
        System.out.println(ex);
    }
    return numDots;
  }
}

class Day13 {
    public static void main(String[] args) {
      Solution solution = new Solution();
      System.out.println(solution.foldInput());
    }
}