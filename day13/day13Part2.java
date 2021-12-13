import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
  String FOLD_PREFIX = "fold along";

  void printSet(Set<List<Integer>> paper) {
    int maxX = 0;
    int maxY = 0;
    for (List<Integer> coord : paper) {
      int x = coord.get(0);
      int y = coord.get(1);
      maxX = Math.max(maxX, x);
      maxY = Math.max(maxY, y);
    }
    char[][] arr = new char[maxY + 1][maxX + 1];
    for (List<Integer> coord : paper) {
      arr[coord.get(1)][coord.get(0)] = '#';
    }
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        char c = arr[i][j];
        System.out.print(c == '#' ? c : ' ');
      }
      System.out.println("");
    }
  }

  Set<List<Integer>> fold(Set<List<Integer>> paper, String axis, int value) {
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
        if (x >= value) {
          int distance = x - value;
          int newX = value - distance;
          List<Integer> newCoord = new ArrayList<>();
          newCoord.add(newX);
          newCoord.add(y);
          newPaper.add(newCoord);
        } else {
          newPaper.add(coord);
        }
      }
    }
    return newPaper;
  } 

  void foldInput() {
    Set<List<Integer>> paper = new HashSet<>();
    try {
        Path path = Paths.get("input.txt");
        List<String> fileContents = Files.readAllLines(path);
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
            paper = fold(paper, xOrY, value);
          }
        }
    } catch (Exception ex) {
        System.out.println(ex);
    }
    printSet(paper);
  }
}

class Day13Part2 {
    public static void main(String[] args) {
      Solution solution = new Solution();
      solution.foldInput();
    }
}