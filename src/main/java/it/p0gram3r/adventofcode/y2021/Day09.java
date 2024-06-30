package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

public class Day09 implements Puzzle {
    @Override
    public String solutionA(List<String> input) {
        HeightMap map = HeightMap.of(input);

        return map.coordinateStream()
                .filter(map::isLowPoint)
                .map(c -> 1 + map.heightAt(c))
                .reduce(0, Integer::sum)
                .toString();
    }

    @Override
    public String solutionB(List<String> input) {
        HeightMap map = HeightMap.of(input);

        Collection<Basin> allBasins = new LinkedList<>();
        map.coordinateStream().forEach(coordinate -> {
            if (isCoordinatePartOfAnyBasin(coordinate, allBasins)) {
                return;
            }
            map.identifyBasin(coordinate).ifPresent(allBasins::add);
        });

        Comparator<Basin> reverseSizeComparator = (b1, b2) -> Integer.compare(b2.size(), b1.coordinates.size());
        return allBasins.stream()
                .sorted(reverseSizeComparator)
                .limit(3)
                .map(Basin::size)
                .reduce(1, (l, i) -> l * i)
                .toString();
    }

    private boolean isCoordinatePartOfAnyBasin(Coordinate c, Collection<Basin> allBasins) {
        return allBasins.stream().anyMatch(b -> b.containsCoordinate(c));
    }

    @Value
    @RequiredArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    static class Coordinate {
        int x, y;

        static Coordinate of(int x, int y) {
            return new Coordinate(x, y);
        }
    }

    record Basin(Collection<Coordinate> coordinates) {
        boolean containsCoordinate(Coordinate coordinate) {
            return coordinates.stream().anyMatch(c -> c.equals(coordinate));
        }

        int size() {
            return coordinates.size();
        }
    }

    @Value
    static class HeightMap {
        int[][] area;
        int height;
        int width;

        static HeightMap of(List<String> input) {
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

            return new HeightMap(area, height, width);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int[] row : area) {
                for (int x = 0; x < width; x++) {
                    sb.append(row[x]);
                }
                sb.append("\n");
            }
            sb.append("\n");
            return sb.toString();
        }

        int heightAt(Coordinate coordinate) {
            return area[coordinate.y][coordinate.x];
        }

        int heightAt(int x, int y) {
            return area[y][x];
        }

        boolean isLowPoint(Coordinate coordinate) {
            return isLowPoint(coordinate.x, coordinate.y);
        }

        boolean isLowPoint(int x, int y) {
            boolean isLowPoint = true;
            int currentHeight = heightAt(x, y);
            if (y > 0) {
                isLowPoint = currentHeight < heightAt(x, y - 1);
            }
            if (y + 1 < height) {
                isLowPoint &= currentHeight < heightAt(x, y + 1);
            }
            if (x > 0) {
                isLowPoint &= currentHeight < heightAt(x - 1, y);
            }
            if (x + 1 < width) {
                isLowPoint &= currentHeight < heightAt(x + 1, y);
            }

            return isLowPoint;
        }

        Stream<Coordinate> coordinateStream() {
            Stream.Builder<Coordinate> streamBuilder = Stream.builder();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    streamBuilder.add(new Coordinate(x, y));
                }
            }
            return streamBuilder.build();
        }

        Optional<Basin> identifyBasin(Coordinate coordinate) {
            List<Coordinate> basin = new LinkedList<>();
            addCoordToBasin(coordinate.x, coordinate.y, basin);
            return basin.isEmpty()
                    ? Optional.empty()
                    : Optional.of(new Basin(basin));
        }

        private void addCoordToBasin(int x, int y, Collection<Coordinate> basinCoords) {
            Coordinate coordinate = Coordinate.of(x, y);
            if (x < 0 || y < 0 || x >= width || y >= height || heightAt(coordinate) == 9 || basinCoords.contains(coordinate)) {
                return;
            }

            basinCoords.add(coordinate);
            addCoordToBasin(x, y - 1, basinCoords);
            addCoordToBasin(x, y + 1, basinCoords);
            addCoordToBasin(x - 1, y, basinCoords);
            addCoordToBasin(x + 1, y, basinCoords);
        }

    }
}
