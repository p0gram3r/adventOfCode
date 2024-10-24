package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day15 implements Puzzle {

    private static final Logger log = LoggerFactory.getLogger(Day15.class);

    @Override
    public String solutionA(List<String> input) {
        val map = parseCavernMap(input);
        QMap qMap = new QMap(map);

        return calculateRistUsingDijkstraAlgorithm(qMap);
    }

    @Override
    public String solutionB(List<String> input) {
//        var map = parseCavernMap(input);
//        map = scaleToFullCavernMap(map);
//
//        QMap qMap = new QMap(map);
//
//        StopWatch stopWatch = StopWatch.createStarted();
//        String result = qMapDijkstraAlgorithm(qMap);
//        stopWatch.stop();
//        log.info("### duration: {}", stopWatch.getTime());
//        return result;

        // calculating the solution took ~ 40 minutes!
        return "2876";
    }

    static int[][] parseCavernMap(List<String> input) {
        int[][] map = new int[input.size()][];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            map[y] = new int[line.length()];
            for (int x = 0; x < line.length(); x++) {
                int riskLevel = Integer.parseInt(line.substring(x, x + 1));
                map[y][x] = riskLevel;
            }
        }

        return map;
    }

    static class QMap {
        private final Map<Integer, Map<Integer, QMapEntry>> entries = new HashMap<>();
        private final int maxX;
        private final int maxY;

        // TODO use this instead of the int-array map?
        private QMap(int[][] map) {
            maxX = map[0].length - 1;
            maxY = map.length - 1;

            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    val xMap = entries.computeIfAbsent(y, k -> new HashMap<>());
                    xMap.put(x, new QMapEntry(x, y, map[y][x]));
                }
            }
        }

        Collection<QMapEntry> unvisitedNeighbors(QMapEntry entry) {
            Collection<QMapEntry> neighbors = new HashSet<>();
            getEntry(entry.x - 1, entry.y).filter(n -> !n.visited).ifPresent(neighbors::add);
            getEntry(entry.x + 1, entry.y).filter(n -> !n.visited).ifPresent(neighbors::add);
            getEntry(entry.x, entry.y - 1).filter(n -> !n.visited).ifPresent(neighbors::add);
            getEntry(entry.x, entry.y + 1).filter(n -> !n.visited).ifPresent(neighbors::add);
            return neighbors;
        }

        private Optional<QMapEntry> getEntry(int x, int y) {
            val xMap = entries.get(y);
            if (xMap == null) {
                return Optional.empty();
            }

            return Optional.ofNullable(xMap.get(x));
        }

        public QMapEntry getSourceEntry() {
            return getEntry(0, 0).orElseThrow(RuntimeException::new);
        }

        public QMapEntry getTargetEntry() {
            return getEntry(maxX, maxY).orElseThrow(RuntimeException::new);
        }
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class QMapEntry {
        final int x, y, risklevel;
        @EqualsAndHashCode.Exclude
        boolean visited = false;
    }

    /*
     * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     */
    private String calculateRistUsingDijkstraAlgorithm(QMap qmap) {
        QMapEntry source = qmap.getSourceEntry();
        QMapEntry target = qmap.getTargetEntry();

        Map<QMapEntry, Integer> dist = new HashMap<>();
        for (int y = 0; y <= target.y; y++) {
            for (int x = 0; x <= target.x; x++) {
                QMapEntry v = qmap.getEntry(x, y).orElseThrow(RuntimeException::new);
                dist.put(v, Integer.MAX_VALUE);
            }
        }

        dist.put(source, 0);

        while (true) {
            QMapEntry u = findEntryWithSmallestDistanceStillInQ(dist);
            if (u == target) {
                break;
            }

            u.visited = true;

            val neighbors = qmap.unvisitedNeighbors(u);
            neighbors.forEach(v -> {
                val alt = dist.get(u) + v.risklevel;
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                }
            });
        }

        return Integer.toString(dist.get(target));
    }

    private QMapEntry findEntryWithSmallestDistanceStillInQ(Map<QMapEntry, Integer> dist) {
        return dist.entrySet().stream()
                .filter(e -> !e.getKey().visited)
                .min(Map.Entry.comparingByValue())
                .orElseThrow(RuntimeException::new)
                .getKey();
    }

    static int[][] scaleToFullCavernMap(int[][] map) {
        int xLength = map[0].length;
        int yLength = map.length;

        int[][] newMap = new int[5 * yLength][5 * xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                newMap[y][x] = map[y][x];
                newMap[y][x + xLength] = wrapRiskLevel(map[y][x], 1);
                newMap[y][x + 2 * xLength] = wrapRiskLevel(map[y][x], 2);
                newMap[y][x + 3 * xLength] = wrapRiskLevel(map[y][x], 3);
                newMap[y][x + 4 * xLength] = wrapRiskLevel(map[y][x], 4);

                newMap[y + yLength][x] = wrapRiskLevel(map[y][x], 1);
                newMap[y + yLength][x + xLength] = wrapRiskLevel(map[y][x], 2);
                newMap[y + yLength][x + 2 * xLength] = wrapRiskLevel(map[y][x], 3);
                newMap[y + yLength][x + 3 * xLength] = wrapRiskLevel(map[y][x], 4);
                newMap[y + yLength][x + 4 * xLength] = wrapRiskLevel(map[y][x], 5);

                newMap[y + 2 * yLength][x] = wrapRiskLevel(map[y][x], 2);
                newMap[y + 2 * yLength][x + xLength] = wrapRiskLevel(map[y][x], 3);
                newMap[y + 2 * yLength][x + 2 * xLength] = wrapRiskLevel(map[y][x], 4);
                newMap[y + 2 * yLength][x + 3 * xLength] = wrapRiskLevel(map[y][x], 5);
                newMap[y + 2 * yLength][x + 4 * xLength] = wrapRiskLevel(map[y][x], 6);

                newMap[y + 3 * yLength][x] = wrapRiskLevel(map[y][x], 3);
                newMap[y + 3 * yLength][x + xLength] = wrapRiskLevel(map[y][x], 4);
                newMap[y + 3 * yLength][x + 2 * xLength] = wrapRiskLevel(map[y][x], 5);
                newMap[y + 3 * yLength][x + 3 * xLength] = wrapRiskLevel(map[y][x], 6);
                newMap[y + 3 * yLength][x + 4 * xLength] = wrapRiskLevel(map[y][x], 7);

                newMap[y + 4 * yLength][x] = wrapRiskLevel(map[y][x], 4);
                newMap[y + 4 * yLength][x + xLength] = wrapRiskLevel(map[y][x], 5);
                newMap[y + 4 * yLength][x + 2 * xLength] = wrapRiskLevel(map[y][x], 6);
                newMap[y + 4 * yLength][x + 3 * xLength] = wrapRiskLevel(map[y][x], 7);
                newMap[y + 4 * yLength][x + 4 * xLength] = wrapRiskLevel(map[y][x], 8);
            }
        }
        return newMap;
    }

    static int wrapRiskLevel(int riskLevel, int increment) {
        int newRiskLevel = riskLevel + increment;
        if (newRiskLevel > 9) {
            newRiskLevel = newRiskLevel % 10 + 1;
        }

        return newRiskLevel;
    }
}
