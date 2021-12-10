import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

class Solution {
  boolean isOpen(char c) {
    return (c == '(' || c == '[' || c == '{' || c == '<');
  }

  boolean isClosed(char c) {
    return (c == ')' || c == ']' || c == '}' || c == '>');
  }

  char getClosing(char c) {
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

  int getScore(char illegal) {
    if (illegal == ')') {
      return 3;
    } else if (illegal == ']') {
      return 57;
    } else if (illegal == '}') {
      return 1197;
    } else if (illegal == '>') {
      return 25137;
    }
    return 0;
  }

  int solution() {
    int syntaxErrorScore = 0;
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      Stack<Character> openBrackets = new Stack<Character>();
      for (String line : fileContents) {
        char[] charArr = line.toCharArray();
        for (char c : charArr) {
          if (isOpen(c)) {
            openBrackets.push(c);
          } else if (isClosed(c)) {
            char open = openBrackets.pop();
            if (getClosing(open) != c) {
              syntaxErrorScore += getScore(c);
              break;
            }
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return syntaxErrorScore;
  }
}

class Day10 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.solution());
  }
}