import java.util.List;
import lombok.val;

class Day02B implements Puzzle {

    @Override
    public String getSolution(List<String> input) {
        int aim = 0;
        int x = 0;
        int y = 0;
        for (String line : input) {
            val command = line.split(" ");
            val value = Integer.parseInt(command[1]);

            switch (command[0]) {
                case "forward" -> {x += value;y += aim * value;}
                case "up" -> aim -= value;
                case "down" -> aim += value;
                default -> throw new IllegalArgumentException(line);
            }
        }

        return Integer.toString(x * y);
    }
}
