import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

class Day16 {

  static int packetVersionSum = 0;

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

  static int getDecimal(int[] arr, int start, int end) {
    int dec = 0;
    int add = 1;
    for (int i = end; i >= start; i--) {
      if (arr[i] == 1) {
        dec += add;
      }
      add *= 2;
    }
    return dec;
  }

  static int parse4(int[] arr, int start) {
    int packetLength = 5;
    while (start + packetLength < arr.length) {
      if (arr[start] == 0) {
        start += packetLength;
        break;
      }
      start += packetLength;
    }
    return start;
  }

  static int parseNumPackets(int[] arr, int start, int numPackets) {
    int newStart = 0;
    for (int i = 0; i < numPackets; i++) {
      newStart = parsePacket(arr, start);
      start = newStart;
    }
    return start;
  }

  static int parseNumBitPackets(int[] arr, int start, int numBits) {
    int numBitsTotal = 0;
    int newStart = 0;
    while (numBitsTotal < numBits) {
      newStart = parsePacket(arr, start);
      numBitsTotal += (newStart - start);
      start = newStart;
    }
    return start;
  }

  static int parsePacket(int[] arr, int start) {
    int packetVersion = getDecimal(arr, start, start + 2);
    int packetTypeId = getDecimal(arr, start + 3, start + 5);
    packetVersionSum += packetVersion;
    int newEnd = 0;
    if (packetTypeId == 4) {
      newEnd = parse4(arr, start + 6);
    } else {
      int lengthTypeId = arr[start + 6];
      if (lengthTypeId == 0) {
        int totalLengthBits = getDecimal(arr, start + 7, start + 7 + 14);
        newEnd = parseNumBitPackets(arr, start + 7 + 15, totalLengthBits);
      } else {
        int numberSubPackets = getDecimal(arr, start + 7, start + 7 + 10);
        newEnd = parseNumPackets(arr, start + 7 + 11, numberSubPackets);
      }
    }
    return newEnd;
  }
  
  public static void main(String[] args) throws Exception {
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      int[] arr = new int[fileContents.get(0).length() * 4];
      String str = fileContents.get(0);
      for (int i = 0; i < str.length(); i++) {
        String binary = getBinary(str.charAt(i));
        assert(binary.length() == 4);
        for (int j = 0; j < 4; j++) {
          arr[i*4 + j] = binary.charAt(j) - '0';
        }
      }
      parsePacket(arr, 0);
      System.out.println(Arrays.toString(arr));
      System.out.println(packetVersionSum);
    } catch (Exception e) {
      System.out.println(e);
      throw e;
    }
  }
  
}
