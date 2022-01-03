import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day22 {

  static class Cube {
    int x;
    int y;
    int z;

    Cube(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    public boolean isInBounds() {
      return x > -50 && x < 50 && y > -50 && y < 50 && z > -50 && z < 50;
    }

    @Override
    public String toString() {
      return x + ", " + y + ", " + z + "\n";
    }

    @Override
    public boolean equals(Object c1) {
      if (c1 == null) return false;
      if (c1 == this) return true;
      if (!(c1 instanceof Cube)) return false;
      Cube cube = (Cube)c1;
      return cube.x == this.x && cube.y == this.y && cube.z == this.z;
    }

    @Override
    public int hashCode() {
      return x ^ y * z;
    }
  }

  enum CubeState {
    ON ("on"),
    OFF("off");

    private String state;

    CubeState(String s) {
      this.state = s;
    }
  }

  static class Reboot {
    Set<Cube> cubesOn;

    Reboot() {
      this.cubesOn = new HashSet<>();
    }

    void add(Cube cube) {
      cubesOn.add(cube);
    }

    void remove(Cube cube) {
      cubesOn.remove(cube);
    }

    int getNumCubesOn() {
      return cubesOn.size();
    }
  }

  private static int getNumCubesOn(List<String> lines) {
    Reboot reboot = new Reboot();
    for (int i = 0; i < lines.size(); i++) {
      String[] step = lines.get(i).split(" ");
      CubeState state = step[0].equals("on") ? CubeState.ON : CubeState.OFF;
      int x1 = Integer.parseInt(step[1].split(",")[0].split("=")[1].split("\\.\\.")[0]);
      int x2 = Integer.parseInt(step[1].split(",")[0].split("=")[1].split("\\.\\.")[1]);
      int y1 = Integer.parseInt(step[1].split(",")[1].split("=")[1].split("\\.\\.")[0]);
      int y2 = Integer.parseInt(step[1].split(",")[1].split("=")[1].split("\\.\\.")[1]);
      int z1 = Integer.parseInt(step[1].split(",")[2].split("=")[1].split("\\.\\.")[0]);
      int z2 = Integer.parseInt(step[1].split(",")[2].split("=")[1].split("\\.\\.")[1]);
      if (x1 < -50 || x2 > 50 || y1 < -50 || y2 > 50 || z1 < -50 || z2 > 50) continue;
      for (int x = x1; x <= x2; x++) {
        for (int y = y1; y <= y2; y++) {
          for (int z = z1; z <= z2; z++) {
            Cube c = new Cube(x, y, z);
            if (state == CubeState.ON) {
              reboot.add(c);
            } else {
              reboot.remove(c);
            }
          }
        }
      }
    }
    return reboot.getNumCubesOn();
  }

  public static void main(String[] args) throws Exception {
    Path path = Paths.get("input.txt");
    List<String> lines = Files.readAllLines(path);
    System.out.println(getNumCubesOn(lines));
  }
}
