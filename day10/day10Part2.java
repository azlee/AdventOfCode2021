import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

class Day10Part2 {

  static boolean isOpen(char c) {
    return (c == '(' || c == '[' || c == '{' || c == '<');
  }

  static boolean isClosed(char c) {
    return (c == ')' || c == ']' || c == '}' || c == '>');
  }

  static char getClosing(char c) {
    if (c == '(') {
      return ')';
    }
    if (c == '[') {
      return ']';
    }
    if (c == '{') {
      return '}';
    }
    if (c == '<') {
      return '>';
    }
    return '\0';
  }

  static int getScore(char closing) {
    if (closing == ')') {
      return 1;
    } else if (closing == ']') {
      return 2;
    } else if (closing == '}') {
      return 3;
    } else if (closing == '>') {
      return 4;
    }
    return 0;
  }
  public static void main(String[] args) {
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      Stack<Character> openBrackets = new Stack<Character>();
      List<Long> scores = new ArrayList<>();
      int broken = 0;
      for (String line : fileContents) {
        char[] charArr = line.toCharArray();
        openBrackets = new Stack<>();
        boolean broke = false;
        for (char c : charArr) {
          if (isOpen(c)) {
            openBrackets.push(c);
          } else if (isClosed(c)) {
            char open = openBrackets.pop();
            if (getClosing(open) != c) {
              // invalid line
              broke = true;
              broken++;
              break;
            }
          }
        }
        if (!broke) {
          long score = 0;
          while (!openBrackets.isEmpty()) {
            char c = openBrackets.pop();
            char close = getClosing(c);
            score *= 5;
            System.out.println("score " + score);
            score += getScore(close);
          }
          scores.add(score);
        }
      }
      Collections.sort(scores);
      int middle = scores.size() / 2;
      System.out.println(scores.get(middle));
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}