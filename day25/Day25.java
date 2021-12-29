import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Day25 {
  static boolean isOutOfBounds(char[][] seaCucumbers, int i, int j) {
    return i >= 0 && j >= 0 && i < seaCucumbers.length && j < seaCucumbers[0].length;
  }

  static char[][] getNextStep(char[][] seaCucumbers) {
    char[][] newCucumbers1 = copyArr(seaCucumbers);
    for (int i = 0; i < seaCucumbers.length; i++) {
      for (int j = 0; j < seaCucumbers[0].length; j++) {
        if (seaCucumbers[i][j] == '>') {
          int newJ = (j + 1) % (seaCucumbers[0].length);
          if (seaCucumbers[i][newJ] == '.') {
            newCucumbers1[i][newJ] = '>';
            newCucumbers1[i][j] = '.';
          }
        }
      }
    }
    char[][] newCucumbers2 = copyArr(newCucumbers1);
    for (int j = 0; j < newCucumbers1[0].length; j++) {
      for (int i = 0; i < newCucumbers1.length; i++) {
        if (newCucumbers1[i][j] == 'v') {
          int newI = (i + 1) % (newCucumbers1.length);
          if (newCucumbers1[newI][j] == '.') {
            newCucumbers2[i][j] = '.';
            newCucumbers2[newI][j] = 'v';
          }
        }
      }
    }
    return newCucumbers2;
  }

  static void print(char[][] board) {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        System.out.print(board[i][j]);
      }
      System.out.println("");
    }
    System.out.println("");
  }
  
  static boolean isEqual(char[][] board1, char[][] board2) {
    for (int i = 0; i < board1.length; i++) {
      for (int j = 0; j < board1[0].length; j++) {
        if (board1[i][j] != board2[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  static char[][] copyArr(char[][] board) {
    char[][] board2 = new char[board.length][board[0].length];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        board2[i][j] = board[i][j];
      }
    }
    return board2;
  }

  static int getStepWhereCucumbersStop(char[][] seaCucumbers) {
    char[][] prevCucumbers = copyArr(seaCucumbers);
    int step = 1;
    while (true) {
      seaCucumbers = getNextStep(seaCucumbers);
      if (isEqual(seaCucumbers, prevCucumbers)) {
        break;
      }
      prevCucumbers = copyArr(seaCucumbers);
      step++;
    }
    return step;
  }
  public static void main(String[] args) throws Exception {
    Path path = Paths.get("input.txt");
    List<String> lines = Files.readAllLines(path);
    char[][] seaCucumbers = new char[lines.size()][lines.get(0).length()];
    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(0).length(); j++) {
        seaCucumbers[i][j] = lines.get(i).charAt(j);
      }
    }
    System.out.println(getStepWhereCucumbersStop(seaCucumbers));
  }
}
