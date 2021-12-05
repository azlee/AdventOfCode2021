import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Day2 {
    static int horizontal = 0;
    static int depth = 0;
    static int aim = 0;

    public enum Command {
       FORWARD ("forward"),
       UP ("up"),
       DOWN ("down");

       private final String value;

       Command(String value) {
           this.value = value;
       }

       public String getValue() {
           return this.value;
       }
    }

    static void advance(String command) {
        String[] splitStr = command.split(" ");
        int distance = Integer.parseInt(splitStr[1]);
        String cmd = splitStr[0];
        if (cmd.equals(Command.FORWARD.getValue())) {
            horizontal += distance;
            depth += (aim * distance);
        } else if (cmd.equals(Command.UP.getValue())) {
            // depth -= distance;
            aim -= distance;
        } else if (cmd.equals(Command.DOWN.getValue())) {
            // depth += distance;
            aim += distance;
        }
    }

    public static void main(String[] args) {
        try {
            Path path = Paths.get("input.txt");
            List<String> fileContents = Files.readAllLines(path);
            String line = fileContents.get(0);
            System.out.println(line);
            for (int i = 0; i < fileContents.size(); i++) {
                advance(fileContents.get(i));
            }
            System.out.println(horizontal);
            System.out.println(depth);
            System.out.println(horizontal * depth);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}