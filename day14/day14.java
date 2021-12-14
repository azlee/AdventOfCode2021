import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {

  Map<String, Long> generatePairMap(String str) {
    HashMap<String, Long> map = new HashMap<>();
    for (int i = 0; i < str.length() - 1; i++) {
      String pair = str.substring(i, i + 2);
      map.put(pair, map.getOrDefault(pair, 0L) + 1);
    }
    return map;
  }

  long getDifferenceMostCommonLeast(long[] count) {
    long mostCommonCount = Long.MIN_VALUE;
    long leastCommonCount = Long.MAX_VALUE;
    for (long i : count) {
      if (i > mostCommonCount) {
        mostCommonCount = i;
      } else if (i < leastCommonCount && i > 0) {
        leastCommonCount = i;
      }
    }
    return mostCommonCount - leastCommonCount;
  }

  long createNewPolymer(Map<String, String> rules, String originalStr, int numSteps) {
    Map<String, Long> oldMap = generatePairMap(originalStr);
    Map<String, Long> newMap = new HashMap<>();
    long[] count = new long[26];
    for (int i = 0; i < originalStr.length(); i++) {
      count[originalStr.charAt(i) - 'A']++;
    }
    for (int i = 0; i < numSteps; i++) {
      for (Map.Entry<String, Long> entry : oldMap.entrySet()) {
        String pair = entry.getKey();
        Long countPair = entry.getValue();
        if (rules.get(pair) == null) {
          newMap.put(pair, newMap.getOrDefault(pair, 0L) + countPair);
        } else {
          // add the inserted character
          char c = rules.get(pair).charAt(0);
          count[c - 'A'] += countPair;
          // add the 2 new pairs
          String pair1 = pair.substring(0, 1) + c;
          String pair2 = c + pair.substring(1, 2);
          newMap.put(pair1, newMap.getOrDefault(pair1, 0L) + countPair);
          newMap.put(pair2, newMap.getOrDefault(pair2, 0L) + countPair);
        }
      }
      oldMap = new HashMap<>();
      oldMap = newMap.entrySet().stream()
      .collect(Collectors.toMap(e -> e.getKey(), e -> (e.getValue())));
      newMap = new HashMap<>();
    }
    return getDifferenceMostCommonLeast(count);
  }

  long getResultingPolymerDiff(int numSteps) {
    Map<String, String> rules = new HashMap<>();
    long res = 0;
    try {
        Path path = Paths.get("input.txt");
        List<String> fileContents = Files.readAllLines(path);
        String original = fileContents.get(0);
        for (int i = 2; i < fileContents.size(); i++) {
          String[] rule = fileContents.get(i).split(" -> ");
          rules.put(rule[0], rule[1]);
        }
        res = createNewPolymer(rules, original, numSteps);
    } catch (Exception ex) {
        System.out.println(ex);
    }
    return res;
  }
}

class Day14 {
    public static void main(String[] args) {
      Solution solution = new Solution();
      long resultingPolymer = solution.getResultingPolymerDiff(40);
      System.out.println(resultingPolymer);
    }
}