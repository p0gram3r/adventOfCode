package it.p0gram3r.adventofcode.y2021;

import com.google.auto.service.AutoService;
import it.p0gram3r.adventofcode.Puzzle;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@AutoService(Puzzle.class)
public class Day06 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        long totalPopulation = countTotalPopulation(input, 80);
        return Long.toString(totalPopulation);
    }

    @Override
    public String solutionB(List<String> input) {
        long totalPopulation = countTotalPopulation(input, 256);
        return Long.toString(totalPopulation);
    }

    private static long countTotalPopulation(List<String> input, int totalDays) {
        var population = initializePopulation(input);

        population = populate(population, totalDays);

        return population.values().stream()
                .reduce(0L, Long::sum);
    }

    private static Map<Integer, Long> initializePopulation(List<String> input) {
        Map<Integer, Long> populationPerAge = new LinkedHashMap<>();
        IntStream.range(0, 9).forEach(age -> populationPerAge.put(age, 0L));

        Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .forEach(age -> populationPerAge.put(age, populationPerAge.get(age) + 1));

        return populationPerAge;
    }

    private static Map<Integer, Long> populate(Map<Integer, Long> population, int totalDays) {
        for (int day = 0; day < totalDays; day++) {
            var oldPopulation = population;
            var newPopulation = new LinkedHashMap<Integer, Long>();
            IntStream.rangeClosed(0, 7).forEach(age -> newPopulation.put(age, oldPopulation.get(age + 1)));
            newPopulation.put(6, oldPopulation.get(0) + oldPopulation.get(7));
            newPopulation.put(8, oldPopulation.get(0));

            population = newPopulation;
        }
        return population;
    }

}
