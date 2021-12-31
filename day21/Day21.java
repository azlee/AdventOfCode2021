class Day21 {

  static class Player {
    int pos;
    int score;

    Player(int p) {
      this.pos = p;
      this.score = 0;
    }
  }

  static class Die {
    private int num;
    private int numRolls;

    Die() {
      this.num = 0;
      this.numRolls = 0;
    }

    int roll() {
      num++;
      numRolls++;
      return num;
    }

    int getNum() {
      return this.num;
    }

    int getNumRolls() {
      return this.numRolls;
    }
  }

  static void oneTurn(Player player, Die die) {
    int r1 = die.roll();
    int r2 = die.roll();
    int r3 = die.roll();
    int move = r1 + r2 + r3;
    player.pos += move;
    while (player.pos > 10) {
      player.pos -= 10;
    }
    player.score += player.pos;
  }
  public static void main(String[] args) throws Exception {
    Player p1 = new Player(5);
    Player p2 = new Player(10);
    Die die = new Die();
    Player currPlayer = p1;
    while (p1.score < 1000 && p2.score < 1000) {
      oneTurn(currPlayer, die);
      currPlayer = currPlayer == p1 ? p2 : p1;
    }
    System.out.println("P1: " + p1.score + ", P2: " + p2.score);
    System.out.println(Math.min(p1.score, p2.score) * die.getNumRolls());
  }
}
