import java.util.List;
import java.util.function.BiFunction;

class Day01 implements Puzzle {

    @Override
    public long solutionA(List<String> input) {
        long oldVal, newVal = Integer.MAX_VALUE;
        long numberOfIncrements = 0;
        for (String depth : input) {
            oldVal = newVal;
            newVal = Integer.parseInt(depth);
            if (newVal > oldVal) {
                numberOfIncrements++;
            }
        }

        return numberOfIncrements;
    }

    @Override
    public long solutionB(List<String> input) {
        BiFunction<List<String>, Integer, Integer> sumLines = (l, i) -> Integer.parseInt(l.get(i)) + Integer.parseInt(l.get(i + 1)) + Integer.parseInt(l.get(i + 2));

        long numberOfIncrements = 0;
        for (int index = 1; index + 2 < input.size(); index++) {
            int sumA = sumLines.apply(input, index-1);
            int sumB = sumLines.apply(input, index);
            if (sumA < sumB) {
                numberOfIncrements++;
            }
        }

        return numberOfIncrements;
    }
}