import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day22Part2 {

  static class Cuboid {
    long xMin;
    long yMin;
    long zMin;
    long xMax;
    long yMax;
    long zMax;

    Cuboid(long x1, long x2, long y1, long y2, long z1, long z2) {
      this.xMin = x1;
      this.yMin = y1;
      this.zMin = z1;
      this.xMax = x2;
      this.yMax = y2;
      this.zMax = z2;
    }

    @Override
    public String toString() {
      return xMin + ".." + xMax + ", " + yMin + ".." + yMax + ", " + zMin + ".." + zMax + "\n";
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

  static class Interval {
    long min;
    long max;

    Interval(long min, long max) {
      this.min = min;
      this.max = max;
    }
  }

  static class Reboot {
    List<Cuboid> cuboids; // cuboids that are on

    Reboot() {
      this.cuboids = new ArrayList<>();
    }

    long getNumCubesOn() {
      long volume = 0;
      for (Cuboid cuboid : cuboids) {
        volume += (cuboid.xMax - cuboid.xMin + 1) * (cuboid.yMax - cuboid.yMin + 1) * (cuboid.zMax - cuboid.zMin + 1);
      }
      return volume;
    }

    boolean doesIntersect(Cuboid c1, Cuboid c2) {
      return c1.xMin <= c2.xMax &&
        c1.yMin <= c2.yMax &&
        c1.zMin <= c2.zMax;
    }

    void turn(Cuboid cuboid, CubeState state) {
      List<Cuboid> newCuboids = new ArrayList<>();
      // loop through all the cuboids and check for intersection
      for (Cuboid oldCuboid : cuboids) {
        if (!(doesIntersect(cuboid, oldCuboid) && doesIntersect(oldCuboid, cuboid))) {
          newCuboids.add(oldCuboid);
          continue;
        }

        // else there is intersection so figure out the new cuboids
        if (oldCuboid.xMin <= cuboid.xMax && cuboid.xMax <= oldCuboid.xMax) {
          Cuboid newCube = new Cuboid(cuboid.xMax + 1, oldCuboid.xMax, oldCuboid.yMin, oldCuboid.yMax, oldCuboid.zMin, oldCuboid.zMax);
          newCuboids.add(newCube);
          oldCuboid = new Cuboid(oldCuboid.xMin, cuboid.xMax, oldCuboid.yMin, oldCuboid.yMax, oldCuboid.zMin, oldCuboid.zMax);
        }

        if (oldCuboid.xMin <= cuboid.xMin && cuboid.xMin <= oldCuboid.xMax) {
          newCuboids.add(new Cuboid(oldCuboid.xMin, cuboid.xMin - 1, oldCuboid.yMin, oldCuboid.yMax, oldCuboid.zMin, oldCuboid.zMax));
          oldCuboid = new Cuboid(cuboid.xMin, oldCuboid.xMax, oldCuboid.yMin, oldCuboid.yMax, oldCuboid.zMin, oldCuboid.zMax);
        }

        if (oldCuboid.yMin <= cuboid.yMax && cuboid.yMax <= oldCuboid.yMax) {
          newCuboids.add(new Cuboid(oldCuboid.xMin, oldCuboid.xMax, cuboid.yMax + 1, oldCuboid.yMax, oldCuboid.zMin, oldCuboid.zMax));
          oldCuboid = new Cuboid(oldCuboid.xMin, oldCuboid.xMax, oldCuboid.yMin, cuboid.yMax, oldCuboid.zMin, oldCuboid.zMax);
        }

        if (oldCuboid.yMin <= cuboid.yMin && cuboid.yMin <= oldCuboid.yMax) {
          newCuboids.add(new Cuboid(oldCuboid.xMin, oldCuboid.xMax, oldCuboid.yMin, cuboid.yMin - 1, oldCuboid.zMin, oldCuboid.zMax));
          oldCuboid = new Cuboid(oldCuboid.xMin, oldCuboid.xMax, cuboid.yMin, oldCuboid.yMax, oldCuboid.zMin, oldCuboid.zMax);
        }

        if (oldCuboid.zMin <= cuboid.zMax && cuboid.zMax <= oldCuboid.zMax) {
          newCuboids.add(new Cuboid(oldCuboid.xMin, oldCuboid.xMax, oldCuboid.yMin, oldCuboid.yMax, cuboid.zMax + 1, oldCuboid.zMax));
          oldCuboid = new Cuboid(oldCuboid.xMin, oldCuboid.xMax, oldCuboid.yMin, oldCuboid.yMax, oldCuboid.zMin, cuboid.zMax);
        }

        if (oldCuboid.zMin <= cuboid.zMin && cuboid.zMin <= oldCuboid.zMax) {
          newCuboids.add(new Cuboid(oldCuboid.xMin, oldCuboid.xMax, oldCuboid.yMin, oldCuboid.yMax, oldCuboid.zMin, cuboid.zMin - 1));
          oldCuboid = new Cuboid(oldCuboid.xMin, oldCuboid.xMax, oldCuboid.yMin, oldCuboid.yMax, cuboid.zMin, oldCuboid.zMax);
        }        
      }

      if (state == CubeState.ON) {
        newCuboids.add(cuboid);
      }
      this.cuboids = newCuboids;
    }
  }

  private static long getNumCubesOn(List<String> lines) {
    Reboot reboot = new Reboot();
    for (int i = 0; i < lines.size(); i++) {
      String[] step = lines.get(i).split(" ");
      CubeState state = step[0].equals("on") ? CubeState.ON : CubeState.OFF;
      long x1 = Long.parseLong(step[1].split(",")[0].split("=")[1].split("\\.\\.")[0]);
      long x2 = Long.parseLong(step[1].split(",")[0].split("=")[1].split("\\.\\.")[1]);
      long y1 = Long.parseLong(step[1].split(",")[1].split("=")[1].split("\\.\\.")[0]);
      long y2 = Long.parseLong(step[1].split(",")[1].split("=")[1].split("\\.\\.")[1]);
      long z1 = Long.parseLong(step[1].split(",")[2].split("=")[1].split("\\.\\.")[0]);
      long z2 = Long.parseLong(step[1].split(",")[2].split("=")[1].split("\\.\\.")[1]);
      Cuboid cuboid = new Cuboid(x1, x2, y1, y2, z1, z2);
      reboot.turn(cuboid, state);
    }
    return reboot.getNumCubesOn();
  }
  public static void main(String[] args) throws Exception {
    Path path = Paths.get("input.txt");
    List<String> lines = Files.readAllLines(path);
    System.out.println(getNumCubesOn(lines));
  }
}
