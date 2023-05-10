import java.util.List;
import lombok.val;

class Day02A implements Puzzle {

    @Override
    public String getSolution(List<String> input) {
        int x = 0;
        int y = 0;
        for (String line : input) {
            val command = line.split(" ");
            val value = Integer.parseInt(command[1]);

            switch (command[0]) {
                case "forward" -> x += value;
                case "up" -> y -= value;
                case "down" -> y += value;
                default -> throw new IllegalArgumentException(line);
            }
        }

        return Integer.toString(x * y);
    }
}
