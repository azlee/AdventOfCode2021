import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Velocity {
  int xv;
  int yv;
  Velocity(int x, int y) {
    this.xv = x;
    this.yv = y;
  }

  @Override
  public String toString() {
    return "[" + xv + "," + yv + "]";
  }
}

class Point {
  int x;
  int y;
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return x + ", " + y;
  }
}

class Day17Part2 {
  boolean isInRange(Point point, Point targetLeftTop, Point targetBottomRight) {
    return 
      point.x >= targetLeftTop.x &&
      point.x <= targetBottomRight.x &&
      point.y <= targetLeftTop.y &&
      point.y >= targetBottomRight.y;
  }

  boolean isPastRange(Point point, Point targetLeftTop, Point targetBottomRight) {
    return
      point.x >= targetBottomRight.x ||
      point.y <= targetBottomRight.y;
  }

  boolean hitsTarget(Velocity v, Point targetLeftTop, Point targetBottomRight) {
    Point pos = new Point(0, 0);
    while (!isPastRange(pos, targetLeftTop, targetBottomRight)) {
      pos.x += v.xv;
      pos.y += v.yv;
      if (isInRange(pos, targetLeftTop, targetBottomRight)) {
        return true;
      }
      if (v.xv > 0) {
        v.xv--;
      }
      v.yv--;
    }
    return false;
  }

  boolean isVelocityInRange(int v, int x1, int x2) {
    int pos = 0;
    while (v >= 0) {
      pos += v;
      if (pos >= x1 && pos <= x2) {
        return true;
      }
      if (pos >= x2) break;
      v--;
    }
    return pos >= x1 && pos <= x2;
  }

  List<Velocity> getVelocitiesThatHitTarget(Point leftTop, Point bottomRight) {
    List<Velocity> velocities = new ArrayList<>();
    for (int xv = 1; xv <= bottomRight.x; xv++) {
      if (!isVelocityInRange(xv, leftTop.x, bottomRight.x)) continue;
      for (int yv = bottomRight.y; yv <= (-1 * bottomRight.y)-1; yv++) {
        // check if we can hit the target
        Velocity v = new Velocity(xv, yv);
        if (hitsTarget(v, leftTop, bottomRight)) {
          velocities.add(v);
        }
      }
    }
    return velocities;
  }
 public static void main(String[] args) {
    try {
      Path path = Paths.get("input.txt");
      String line = Files.readAllLines(path).get(0);
      String[] splitStr = line.split("=");
      String[] xValues = splitStr[1].split(",")[0].split("\\.\\.");
      String[] yValues = splitStr[2].split("\\.\\.");
      int x1 = Integer.parseInt(xValues[0]);
      int x2 = Integer.parseInt(xValues[1]);
      int y1 = Integer.parseInt(yValues[1]);
      int y2 = Integer.parseInt(yValues[0]);
      Day17Part2 solution = new Day17Part2();
      Point bottomLeft = new Point(x1, y1);
      Point topRight = new Point(x2, y2);
      List<Velocity> velocities = solution.getVelocitiesThatHitTarget(bottomLeft, topRight);
      System.out.println(velocities.size());
    } catch (Exception e) {
      System.out.println(e);
    } 
  }
}
