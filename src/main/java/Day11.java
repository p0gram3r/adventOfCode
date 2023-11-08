import com.google.auto.service.AutoService;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Value;

@AutoService(Puzzle.class)
public class Day11 implements Puzzle<Integer, Integer> {

    @Override
    public Integer solutionA(List<String> input) {
        FlashMap map = FlashMap.of(input);

        return IntStream.range(0, 100)
                .map(i -> map.countFlashesInIteration())
                .sum();
    }

    @Override
    public Integer solutionB(List<String> input) {
        FlashMap map = FlashMap.of(input);

        int allFlashCount = map.height * map.width;
        for (int itCount = 1; itCount < Integer.MAX_VALUE; itCount++) {
            int flashCount = map.countFlashesInIteration();
            if(flashCount == allFlashCount) {
                return itCount;
            }
        }
        throw new IllegalArgumentException();
    }


    @Value
    static class FlashMap {
        static final int FLASH_LEVEL = 10;
        int[][] area;
        int height;
        int width;

        static FlashMap of(List<String> input) {
            int height = input.size();
            int width = input.get(0).length();
            int[][] area = new int[height][];

            for (int y = 0; y < height; y++) {
                String line = input.get(y);
                area[y] = new int[width];
                for (int x = 0; x < width; x++) {
                    area[y][x] = Integer.parseInt(line.substring(x, x + 1));
                }
            }

            return new FlashMap(area, height, width);
        }

        int countFlashesInIteration() {
            increaseEnergyLevels(area);
            int result = resetEnergyLevels(area);
            return result;
        }

        private void increaseEnergyLevels(int[][] area) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    increaseEnergyLevel(area, x, y);
                }
            }
        }

        private void increaseEnergyLevel(int[][] area, int x, int y) {
            if (x < 0 || x >= width || y < 0 || y >= height) {
                return;
            }

            area[y][x] += 1;
            if (area[y][x] == FLASH_LEVEL) {
                increaseEnergyLevel(area, x - 1, y - 1);
                increaseEnergyLevel(area, x - 1, y);
                increaseEnergyLevel(area, x - 1, y + 1);
                increaseEnergyLevel(area, x, y - 1);
                increaseEnergyLevel(area, x, y + 1);
                increaseEnergyLevel(area, x + 1, y - 1);
                increaseEnergyLevel(area, x + 1, y);
                increaseEnergyLevel(area, x + 1, y + 1);
            }
        }

        private int resetEnergyLevels(int[][] area) {
            int flashCount = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (area[y][x] >= FLASH_LEVEL) {
                        flashCount++;
                        area[y][x] = 0;
                    }
                }
            }
            return flashCount;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int[] row : area) {
                for (int x = 0; x < width; x++) {
                    sb.append(String.format("%2d", row[x]));
                }
                sb.append("\n");
            }
            sb.append("\n");
            return sb.toString();
        }

    }
}
