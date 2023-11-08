import com.google.auto.service.AutoService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AutoService(Puzzle.class)
public class Day07 implements Puzzle<Integer, Integer> {
    @Override
    public Integer solutionA(List<String> input) {
        List<Integer> positions = parsePositions(input.get(0));
        ConsumptionTable table = new ConsumptionTable(positions);
//        System.out.println(table);

        return table.calculateMinFuelForIdealAlignment();
    }


    @Override
    public Integer solutionB(List<String> input) {
        List<Integer> positions = parsePositions(input.get(0));
        ConsumptionTable table = new ConsumptionTable(positions, true);

        return table.calculateMinFuelForIdealAlignment();
    }

    private List<Integer> parsePositions(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static class ConsumptionTable {
        private final int maxPosition;
        private final Map<Integer, List<Integer>> fuelConsumptions;

        ConsumptionTable(List<Integer> positions) {
            this(positions, false);
        }

        ConsumptionTable(List<Integer> positions, boolean additionalCostPerPositionChange) {
            int numberOfCrabs = positions.size();
            this.maxPosition = Collections.max(positions);
            this.fuelConsumptions = new LinkedHashMap<>(numberOfCrabs);

            for (int crabNo = 0; crabNo < numberOfCrabs; crabNo++) {
                int currentPosition = positions.get(crabNo);
                List<Integer> consumptionOfCrab = new ArrayList<>(numberOfCrabs);
                fuelConsumptions.put(crabNo, consumptionOfCrab);
                for (int pos = 0; pos < maxPosition; pos++) {
                    int consumption = additionalCostPerPositionChange
                            ? incrementalFuelConsumption(currentPosition, pos)
                            : constantFuelConsumption(currentPosition, pos);
                    consumptionOfCrab.add(consumption);
                }
            }
        }

        private int constantFuelConsumption(int currentPosition, int pos) {
            return Math.abs(currentPosition - pos);
        }

        private int incrementalFuelConsumption(int currentPosition, int pos) {
            int simpleCost = constantFuelConsumption(currentPosition, pos);
            return (simpleCost * (simpleCost + 1)) / 2;
        }

        int calculateFuelRequiredToMoveAllCrabsToPosition(int position) {
            return fuelConsumptions.values().stream()
                    .map(l -> l.get(position))
                    .reduce(0, Integer::sum);
        }

        int calculateMinFuelForIdealAlignment() {
            int minFuelForIdealAlignment = Integer.MAX_VALUE;
            for (int pos = 0; pos < maxPosition; pos++) {
                minFuelForIdealAlignment = Math.min(minFuelForIdealAlignment, calculateFuelRequiredToMoveAllCrabsToPosition(pos));
            }
            return minFuelForIdealAlignment;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            fuelConsumptions.values().forEach(list -> {
                list.forEach(i -> sb.append(String.format("%4d ", i)));
                sb.append("\n");
            });
            return sb.toString();
        }
    }
}
