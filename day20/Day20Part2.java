import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Day20Part2 {

  static char LIGHT_PIXEL = '#';
  static char DARK_PIXEL = '.';
  static int NUM_ITERATIONS = 50;

  private static int getBinary(char[] comboPixel) {
    int binary = 0;
    int add = 1;
    for (int i = comboPixel.length - 1; i >= 0; i--) {
      if (comboPixel[i] == LIGHT_PIXEL) {
        binary += add;
      }
      add *= 2;
    }
    return binary;
  }

  private static char translatePixel(String enhancementAlgorithm, char[][] outputImage, int i, int j, int iteration) {
    char[] comboPixel = new char[9];
    int k = 0;
    for (int i2 = i - 1; i2 < i + 2; i2++) {
      for (int j2 = j - 1; j2 < j + 2; j2++) {
        if ( i2 < 0 || j2 < 0 || i2 >= outputImage.length || j2 >= outputImage[0].length) {
          if (iteration % 2 == 1 && enhancementAlgorithm.charAt(0) == '#') {
            comboPixel[k] = LIGHT_PIXEL;
          } else {
            comboPixel[k] = DARK_PIXEL;
          }
        } else {
          comboPixel[k] = outputImage[i2][j2];
        }
        k++;
      }
    }
    int binary = getBinary(comboPixel);
    return enhancementAlgorithm.charAt(binary);
  }

  private static char[][] enhanceImage(String enhancementAlgorithm, char[][] inputImage, int iteration) {
    char[][] outputImage = new char[inputImage.length][inputImage[0].length];
    for (int i = 0; i < inputImage.length; i++) {
      for (int j = 0; j < inputImage[0].length; j++) {
        outputImage[i][j] = translatePixel(enhancementAlgorithm, inputImage, i, j, iteration);
      }
    }
    return outputImage;
  }

  private static int getLightPixels(String enhancementAlgorithm, char[][] inputImage) {
    for (int i = 0; i < NUM_ITERATIONS; i++) {
      inputImage = enhanceImage(enhancementAlgorithm, inputImage, i);
    }
    // count the lit pixels
    int numLightPixels = 0;
    for (int i = 0; i < inputImage.length; i++) {
      for (int j = 0; j < inputImage[0].length; j++) {
        if (inputImage[i][j] == LIGHT_PIXEL) {
          numLightPixels++;
        }
      }
    }
    return numLightPixels;
  }
 public static void main(String[] args) throws Exception {
    Path path = Paths.get("input.txt");
    List<String> lines = Files.readAllLines(path);
    String enhancementAlgorithm = lines.get(0);
    char[][] inputImage = new char[lines.size() - 2 + NUM_ITERATIONS*2][lines.get(2).length() + NUM_ITERATIONS*2];
    for (int i = 2; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(2).length(); j++) {
        inputImage[i - 2 + NUM_ITERATIONS][j + NUM_ITERATIONS] = lines.get(i).charAt(j);
      }
    }
    int numLightPixels = getLightPixels(enhancementAlgorithm, inputImage);
    System.out.println(numLightPixels);
  }
}
