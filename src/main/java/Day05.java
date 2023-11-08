import com.google.auto.service.AutoService;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@AutoService(Puzzle.class)
public class Day05 implements Puzzle<Long, Long> {
    @Override
    public Long solutionA(List<String> input) {
        Area area = createArea(input, true);
        return area.countCrossings();
    }

    @Override
    public Long solutionB(List<String> input) {
        Area area = createArea(input, false);
        return area.countCrossings();
    }

    private static Area createArea(List<String> input, boolean ignoreDiagonalLines) {
        Area area = new Area();
        input.forEach(line -> {
            String[] parts = line.split(" -> ");
            Coordinate start = Coordinate.from(parts[0]);
            Coordinate end = Coordinate.from(parts[1]);
            area.drawLine(start, end, ignoreDiagonalLines);
        });
        return area;
    }

    static class Area {
        private final Map<Integer, Map<Integer, Integer>> coordinates = new TreeMap<>();
        private int maxX = 0;
        private int maxY = 0;

        private void drawLine(Coordinate from, Coordinate to, boolean ignoreDiagonalLines) {
            if (ignoreDiagonalLines && from.x != to.x && from.y != to.y) {
                return;
            }

            int xInc = Integer.compare(to.x, from.x);
            int yInc = Integer.compare(to.y, from.y);
            int dist = Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
            for (int i = 0, x = from.x, y = from.y; i <= dist; i++, x += xInc, y += yInc) {
                mark(x, y);
            }
        }

        private long countCrossings() {
            return coordinates.values().stream()
                    .flatMap(map -> map.values().stream())
                    .filter(i -> i >= 2)
                    .count();
        }

        private void mark(int x, int y) {
            var temp = coordinates.computeIfAbsent(x, k -> new TreeMap<>());
            temp.merge(y, 1, Integer::sum);

            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

    }

    record Coordinate(int x, int y) {
        static Coordinate from(String input) {
            String[] parts = input.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            return new Coordinate(x, y);
        }
    }
}
