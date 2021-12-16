import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class CellComparator implements Comparator<Cell> {
  public int compare(Cell a, Cell b) {
    if (a.dist < b.dist) {
      return -1;
    } else if (a.dist > b.dist) {
      return 1;
    } else {
      return 0;
    }
  }
}

class Cell {
  int x;
  int y;
  int dist;
  Cell(int x, int y, int dist) {
    this.x = x;
    this.y = y;
    this.dist = dist;
  }
}

class Day15 {
  
  static int shortestPath(int[][] grid) {
    int[][] dist = new int[grid.length][grid[0].length];
    for (int i = 0; i < dist.length; i++) {
      for (int j = 0; j < dist[0].length; j++) {
        dist[i][j] = Integer.MAX_VALUE;
      }
    }
    dist[0][0] = 0;
    PriorityQueue<Cell> pq = new PriorityQueue<>(grid.length * grid[0].length, new CellComparator());
    pq.add(new Cell(0, 0, 0));
    int[][] directions = {{0,1}, {1,0}, {-1,0}, {0,-1}};
    while (!pq.isEmpty()) {
      Cell c = pq.poll();
      for (int[] dir : directions) {
        int x = c.x + dir[0];
        int y = c.y + dir[1];
        if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length) {
          if (dist[x][y] > dist[c.x][c.y] + grid[x][y]) {
            dist[x][y] = dist[c.x][c.y] + grid[x][y];
            pq.add(new Cell(x, y, dist[x][y]));
          }
        }
      }
    }
    return dist[dist.length-1][dist[0].length-1];
  }
	public static void main(String[] args) throws Exception
	{
    try {
      Path path = Paths.get("input.txt");
      List<String> fileContents = Files.readAllLines(path);
      int[][] arr = new int[fileContents.size()][];
      for (int i = 0; i < fileContents.size(); i++) {
        arr[i] = new int[fileContents.get(0).length()];
        for (int j = 0; j < fileContents.get(i).length(); j++) {
          arr[i][j] = fileContents.get(i).charAt(j) - '0';
        }
      }
      System.out.println(shortestPath(arr));
    } catch (Exception e) {
      System.out.println(e);
    }
	}
}
