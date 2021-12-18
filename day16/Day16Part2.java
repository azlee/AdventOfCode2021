import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Result {
  int end;
  long value;

  Result(int e, long v) {
    this.end = e;
    this.value = v;
  }

  @Override
  public String toString() {
    return end + ", " + value;
  }
}

class Day16Part2 {

  static String getBinary(char c) {
    if (c == '0') {
      return "0000";
    } else if (c == '1') {
      return "0001";
    } else if (c == '2') {
      return "0010";
    } else if (c == '3') {
      return "0011";
    } else if (c == '4') {
      return "0100";
    } else if (c == '5') {
      return "0101";
    } else if (c == '6') {
      return "0110";
    } else if (c == '7') {
      return "0111";
    } else if (c == '8') {
      return "1000";
    } else if (c == '9') {
      return "1001";
    } else if (c == 'A') {
      return "1010";
    } else if (c == 'B') {
      return "1011";
    } else if (c == 'C') {
      return "1100";
    } else if (c == 'D') {
      return "1101";
    } else if (c == 'E') {
      return "1110";
    } else if (c == 'F') {
      return "1111";
    }
    return "";
  }

  static long getDecimal(int[] arr, int start, int end) {
    long dec = 0;
    long add = 1;
    for (int i = end; i >= start; i--) {
      if (arr[i] == 1) {
        dec += add;
      }
      add *= 2;
    }
    return dec;
  }

  static Result parse4(int[] arr, int start) {
    int packetLength = 5;
    List<Integer> literal = new ArrayList<>();
    while (start + packetLength <= arr.length) {
      for (int i = 1; i < 5; i++) {
        literal.add(arr[start + i]);
      }
      if (arr[start] == 0) {
        start += packetLength;
        break;
      }
      start += packetLength;
    }
    // calculate and add the literal value to the list
    int[] literals = new int[literal.size()];
    for (int i = 0; i < literal.size(); i++) {
      literals[i] = literal.get(i);
    }
    return new Result(start, getDecimal(literals, 0, literals.length - 1));
  }

  static Result parseNumPackets(int[] arr, int start, long numPackets, long packetTypeId) {
    List<Long> subPacketValues = new ArrayList<>();
    for (int i = 0; i < numPackets; i++) {
      Result result = parseTopLevelPacket(arr, start);
      start = result.end;
      subPacketValues.add(result.value);
    }
    return new Result(start, getValue(subPacketValues, packetTypeId));
  }
 
  static Result parseNumBitPackets(int[] arr, int start, long numBits, long packetTypeId) {
    List<Long> subPacketValues = new ArrayList<>();
    long stopAtBit = start + numBits;
    while (start < stopAtBit) {
      Result result = parseTopLevelPacket(arr, start);
      start = result.end;
      subPacketValues.add(result.value);
    }
    return new Result(start, getValue(subPacketValues, packetTypeId));
  }

  static long getValue(List<Long> subPacketValues, long packetTypeId) {
    if (packetTypeId == 0) {
      return subPacketValues.stream().mapToLong(Long::longValue).sum();
    } else if (packetTypeId == 1) {
      long product = 1;
      for (long l : subPacketValues) {
        product *= l;
      }
      return product;
    } else if (packetTypeId == 2) {
      return Collections.min(subPacketValues);
    } else if (packetTypeId == 3) {
      return Collections.max(subPacketValues);
    } else if (packetTypeId == 5) {
      assert subPacketValues.size() == 2;
      return subPacketValues.get(0) > subPacketValues.get(1) ? 1 : 0;
    } else if (packetTypeId == 6) {
      assert subPacketValues.size() == 2;
      return subPacketValues.get(0) < subPacketValues.get(1) ? 1 : 0;
    } else if (packetTypeId == 7) {
      assert subPacketValues.size() == 2;
      return subPacketValues.get(0) == subPacketValues.get(1) ? 1 : 0;
    }
    return 0;
  }

  static Result parseTopLevelPacket(int[] arr, int start) {
    long packetTypeId = getDecimal(arr, start + 3, start + 5);
    if (packetTypeId == 4) {
      return parse4(arr, start + 6);
    } else {
      return parseNon4(arr, start + 6, packetTypeId);
    }
  }

  static Result parseNon4(int[] arr, int start, long packetTypeId) {
    int lengthTypeId = arr[start];
    if (lengthTypeId == 0) {
      long totalLengthBits = getDecimal(arr, start + 1, start + 1 + 14);
      return parseNumBitPackets(arr, start + 1 + 15, totalLengthBits, packetTypeId);
    } else {
      long numberSubPackets = getDecimal(arr, start + 1, start + 11);
      return parseNumPackets(arr, start + 1 + 11, numberSubPackets, packetTypeId);
    }
  }
  
  public static void main(String[] args) throws Exception {
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      int[] arr = new int[fileContents.get(0).length() * 4];
      String str = fileContents.get(0).trim();
      for (int i = 0; i < str.length(); i++) {
        String binary = getBinary(str.charAt(i));
        assert(binary.length() == 4);
        for (int j = 0; j < 4; j++) {
          arr[i*4 + j] = binary.charAt(j) - '0';
        }
      }
      System.out.println(parseTopLevelPacket(arr, 0));
    } catch (Exception e) {
      System.out.println(e);
      throw e;
    }
  }
  
}
