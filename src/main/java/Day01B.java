import java.util.List;
import java.util.function.BiFunction;

class Day01B implements Puzzle {

    @Override
    public String getSolution(List<String> input) {
        BiFunction<List<String>, Integer, Integer> sumLines = (l, i) -> Integer.parseInt(l.get(i)) + Integer.parseInt(l.get(i + 1)) + Integer.parseInt(l.get(i + 2));

        int numberOfIncrements = 0;
        for (int index = 1; index + 2 < input.size(); index++) {
            int sumA = sumLines.apply(input, index-1);
            int sumB = sumLines.apply(input, index);
            if (sumA < sumB) {
                numberOfIncrements++;
            }
        }

        return Integer.toString(numberOfIncrements);
    }
}
