import java.util.List;
import lombok.val;

class Day02 implements Puzzle {

    @Override
    public long solutionA(List<String> input) {
        long x = 0;
        long y = 0;
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

        return x * y;
    }

    @Override
    public long solutionB(List<String> input) {
        long aim = 0;
        long x = 0;
        long y = 0;
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

        return x * y;
    }
}
