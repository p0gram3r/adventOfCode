package it.p0gram3r.adventofcode.y2021;

import com.google.auto.service.AutoService;
import it.p0gram3r.adventofcode.Puzzle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import lombok.val;

@AutoService(Puzzle.class)
public class Day14 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        return runIterations(input, 10);
    }

    @Override
    public String solutionB(List<String> input) {
        return runIterations(input, 40);
    }

    private String runIterations(List<String> input, int itCount) {
        String polymer = parsePolymerTemplate(input);
        val insertionRules = parseRules(input);

        String resultingPolymer = polymer;
        for (int i = 0; i < itCount; i++) {
            resultingPolymer = process(resultingPolymer, insertionRules);
        }

        val elementCounts = countElements(resultingPolymer);
        long mostCommonElementCount = elementCounts.values().stream()
                .mapToLong(AtomicLong::longValue)
                .max()
                .orElseThrow();
        long leastCommonElementCount = elementCounts.values().stream()
                .mapToLong(AtomicLong::longValue)
                .min()
                .orElseThrow();

        return Long.toString(mostCommonElementCount - leastCommonElementCount);
    }


    static String process(String polymer, Map<String, String> insertionRules) {
        int index = 0;
        StringBuilder sb = new StringBuilder(polymer);
        while (index < sb.length() - 1) {
            String currentPair = sb.substring(index, index + 2);
            String insertedElement = insertionRules.get(currentPair);

            if (insertedElement == null) {
                index += 1;
                continue;
            }

            sb.insert(index + 1, insertedElement);
            index += insertedElement.length() + 1;
        }

        return sb.toString();
    }

    static Map<String, AtomicLong> countElements(String polymer) {
        val result = new TreeMap<String, AtomicLong>();
        for (char c : polymer.toCharArray()) {
            String key = String.valueOf(c);
            result.computeIfAbsent(key, k -> new AtomicLong(0)).incrementAndGet();
        }
        return result;
    }

    String parsePolymerTemplate(List<String> input) {
        return input.get(0);
    }

    Map<String, String> parseRules(List<String> input) {
        val result = new LinkedHashMap<String, String>(input.size() - 2);
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            result.put(line.substring(0, 2), line.substring(6));
        }
        return result;
    }
}
