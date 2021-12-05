import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Day4 {

  static class Board {
    int[][] board = new int[5][5];
    boolean isWinner = false;

    void markWinner() {
      isWinner = true;
    }

    Board(List<String> boardStr) {
      int i = 0;
      for (String row : boardStr) {
        String[] parsedRow = row.trim().split("\\s+");
        for (int k = 0; k < parsedRow.length; k++) {
          board[i][k] = Integer.parseInt(parsedRow[k]);
        }
        i++;
      }
    }

    int getUnmarkedSum() {
      int ret = 0;
      for (int i = 0; i < board.length; i++) {
        for ( int j = 0; j < board[0].length; j++) {
          if (board[i][j] > 0) {
            ret += board[i][j];
          }
        }
      }
      return ret;
    }

    void checkBoard(int num) {
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          if (board[i][j] == num) {
            board[i][j] = -1 * board[i][j];
            return;
          }
        }
      }
    }

    int isWinner() {
      // check rows
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          if (board[i][j] > 0) {
            break;
          }
          if (board[i][j] < 0 && j + 1 == board[0].length) {
            return getUnmarkedSum();
          }
        }
      }

      // check columns
      for (int col = 0; col < board[0].length; col++) {
        for (int row = 0; row < board.length; row++) {
          if (board[row][col] > 0) {
            break;
          }
          if (board[row][col] < 0 && row + 1 == board.length) {
            return getUnmarkedSum();
          }
        }
      }
      return 0;
    }
  }

  public static void main(String[] args) {
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      String firstLine = fileContents.get(0);
      String[] calledNumbers = firstLine.split(",");

      List<Board> boards = new ArrayList<>();
      int startLine = 2;
      List<String> boardLines = new ArrayList<>();
      int finalScore = 0;
      while (startLine < fileContents.size()) {
        for (int i = 0; i < 5; i++) {
          boardLines.add(fileContents.get(startLine + i));
        }
        boards.add(new Board(boardLines));
        boardLines.clear();
        startLine += 6;
      }
       for (String str : calledNumbers) {
         int calledNum = Integer.parseInt(str);
         for (Board board : boards) {
           if (board.isWinner) {
             continue;
           }
           board.checkBoard(calledNum);
           int winner = board.isWinner();
           if (winner != 0) {
             board.markWinner();
             System.out.println(winner * calledNum);
             finalScore = winner * calledNum;
           }
         }
       }
      System.out.println(finalScore);
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
}