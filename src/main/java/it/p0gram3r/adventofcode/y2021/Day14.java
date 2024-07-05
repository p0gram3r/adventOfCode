package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
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
        val polymerTemplate = parsePolymerTemplate(input);
        val insertionRules = parsePairInsertionRules(input);

        var polymer = Polymer.initialize(polymerTemplate);
        for (int i = 0; i < itCount; i++) {
            polymer = polymer.runInsertionProcess(insertionRules);
        }

        long mostCommonElementCount = polymer.elementCounts.values().stream()
                .mapToLong(l -> l)
                .max()
                .orElseThrow();
        long leastCommonElementCount = polymer.elementCounts.values().stream()
                .mapToLong(l -> l)
                .min()
                .orElseThrow();

        return Long.toString(mostCommonElementCount - leastCommonElementCount);
    }

    static String parsePolymerTemplate(List<String> input) {
        return input.get(0);
    }

    static Map<String, String> parsePairInsertionRules(List<String> input) {
        val result = new LinkedHashMap<String, String>(input.size() - 2);
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            result.put(line.substring(0, 2), line.substring(6));
        }
        return result;
    }

    record Polymer(Map<String, Long> pairCount, Map<String, Long> elementCounts) {

        static Polymer initialize(String polymerTemplate) {
            Map<String, Long> pairCount = new HashMap<>();
            Map<String, Long> elementCount = new HashMap<>();

            for (int i = 0; i < polymerTemplate.length() - 1; i++) {
                val elem1 = polymerTemplate.substring(i, i + 1);
                val elem2 = polymerTemplate.substring(i + 1, i + 2);
                val pair = elem1 + elem2;

                pairCount.put(pair, pairCount.getOrDefault(pair, 0L) + 1);
                elementCount.put(elem1, elementCount.getOrDefault(elem1, 0L) + 1);
            }

            val lastElem = polymerTemplate.substring(polymerTemplate.length() - 1);
            elementCount.put(lastElem, elementCount.getOrDefault(lastElem, 0L) + 1);

            return new Polymer(pairCount, elementCount);
        }

        Polymer runInsertionProcess(Map<String, String> pairInsertionRules) {
            Map<String, Long> pairCount = new HashMap<>();
            Map<String, Long> elementCount = new HashMap<>(this.elementCounts);

            for (Map.Entry<String, Long> entry : this.pairCount.entrySet()) {
                if (pairInsertionRules.containsKey(entry.getKey())) {
                    String newElem = pairInsertionRules.get(entry.getKey());
                    String newPair1 = entry.getKey().charAt(0) + newElem;
                    String newPair2 = newElem + entry.getKey().charAt(1);

                    addValueToEntry(pairCount, newPair1, entry.getValue());
                    addValueToEntry(pairCount, newPair2, entry.getValue());
                    addValueToEntry(elementCount, newElem, entry.getValue());
                } else {
                    addValueToEntry(pairCount, entry.getKey(), entry.getValue());
                }
            }

            return new Polymer(pairCount, elementCount);
        }

        static void addValueToEntry(Map<String, Long> map, String key, Long valueToAdd) {
            map.put(key, map.containsKey(key) ? map.get(key) + valueToAdd : valueToAdd);
        }
    }

}
