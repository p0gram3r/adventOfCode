import java.util.List;

class Day01A implements Puzzle {

    @Override
    public String getSolution(List<String> input) {
        int oldVal, newVal = Integer.MAX_VALUE;
        int numberOfIncrements = 0;
        for (String depth : input) {
            oldVal = newVal;
            newVal = Integer.parseInt(depth);
            if (newVal > oldVal) {
                numberOfIncrements++;
            }
        }

        return Integer.toString(numberOfIncrements);
    }
}
