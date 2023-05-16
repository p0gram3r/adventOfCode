import java.util.List;
import java.util.stream.Collectors;

class Day03B implements Puzzle {

    @Override
    public String getSolution(List<String> input) {
        String oxyString = determineRating(input, true);
        String co2String = determineRating(input, false);

        int oxy = Integer.parseInt(oxyString, 2);
        int co2 = Integer.parseInt(co2String, 2);
        return Integer.toString(oxy * co2);
    }

    private String determineRating(List<String> input, boolean considerMostCommon) {
        int arrayLength = input.get(0).length();

        for (int pos = 0; pos < arrayLength; pos++) {
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

            char bitToConsider = determineBitToConsider(zeros, ones, pos, considerMostCommon);

            input = filter(input, pos, bitToConsider);
            if (input.size() == 1) {
                break;
            }
        }

        if (input.size() != 1) {
            throw new IllegalArgumentException("something's wrong!");
        }

        return input.get(0);
    }

    private List<String> filter(List<String> list, int position, char bitToConsider) {
        return list.stream()
                .filter(s -> s.charAt(position) == bitToConsider)
                .collect(Collectors.toList());
    }

    private char determineBitToConsider(int[] zeros, int[] ones, int position, boolean considerMostCommon) {
        if (considerMostCommon) {
            return ones[position] >= zeros[position] ? '1' : '0';
        } else {
            return ones[position] < zeros[position] ? '1' : '0';
        }
    }
}
