import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

@Slf4j
public class Day06 implements Puzzle {
    @Override
    public long solutionA(List<String> input) {
        return extracted(input, 80);
    }


    private static long extracted(List<String> input, int totalDays) {
        Map<Integer, Integer> populationPerTimer = new HashMap<>();

        return Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .map(timer -> populationPerTimer.computeIfAbsent(timer, t -> populate(t, totalDays)))
                .map(Integer::longValue)
                .reduce(0L, Long::sum);
    }

    private static int populate(int timer, int totalDays) {
        List<Integer> population = List.of(timer);
        StopWatch sw = StopWatch.create();

        for (int day = 0; day < totalDays; day++) {

            sw.reset();
            sw.start();

            List<Integer> newPopulation = new LinkedList<>();
            population.forEach(t -> {
                if (t == 0) {
                    newPopulation.add(6);
                    newPopulation.add(8);
                } else {
                    newPopulation.add(t - 1);
                }
            });
            population = newPopulation;
            sw.stop();

            log.info("start age: {}, day: {}, population size: {}, duration: {} ms", timer, day, population.size(), sw.getTime());
        }
        return population.size();
    }

    @Override
    @SneakyThrows
    public long solutionB(List<String> input) {
        // TODO the current approach is neither fast nor efficient enough!
        return 0L;
    }


}
