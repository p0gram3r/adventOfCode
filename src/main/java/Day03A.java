import java.util.List;

class Day03A implements Puzzle {

    @Override
    public String getSolution(List<String> input) {
        int arrayLength = input.get(0).length();
        int[] ones = new int[arrayLength];
        int[] zeros = new int[arrayLength];

        input.forEach(line -> {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                switch (c) {
                    case '0' -> zeros[i]++;
                    case '1' -> ones[i]++;
                    default -> throw new IllegalArgumentException(line);
                }
            }
        });

        StringBuilder gammaString = new StringBuilder();
        StringBuilder epsilonString = new StringBuilder();
        for (int i = 0; i < arrayLength; i++) {
            String mostCommonBit = zeros[i] > ones[i] ? "0" : "1";
            String leastCommonBit = zeros[i] < ones[i] ? "0" : "1";
            gammaString.append(mostCommonBit);
            epsilonString.append(leastCommonBit);
        }

        int gammaRate = Integer.parseInt(gammaString.toString(), 2);
        int epsilonRate = Integer.parseInt(epsilonString.toString(), 2);

        return Integer.toString(gammaRate * epsilonRate);
    }
}
