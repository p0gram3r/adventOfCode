package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

public class Day15 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        // FIXME this is too inefficient. Need to think again!

//        val map = parseCavernMap(input);
//
//        for (int y = 0; y < map.length; y++) {
//            for (int x = 0; x < map[y].length; x++) {
//                System.out.print(map[y][x]);
//            }
//            System.out.println();
//        }
//
//        Position start = Position.of(0, 0);
//        Position target = Position.of(map.length - 1, map.length - 1);
////        Position target = Position.of(2, 2);
//        Path p = findPathWithLowestRiskLevel(map, start, target);
//
//        return Integer.toString(p.riskLevel);
        return "0";
    }

    @Override
    public String solutionB(List<String> input) {
        return "0";
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

    Path findPathWithLowestRiskLevel(int[][] map, Position start, Position target) {
        Collection<Path> pathCollection = new HashSet<>();
        pathCollection.add(Path.startFrom(start));

        do {
            Path pathToFollow = findPathWithCurrentlyLowestRiskLevel(pathCollection);

            pathCollection.remove(pathToFollow);

            int x = pathToFollow.getEndPosition().x;
            int y = pathToFollow.getEndPosition().y;

            addPathIfPossible(pathToFollow, x - 1, y, map, pathCollection);
            addPathIfPossible(pathToFollow, x + 1, y, map, pathCollection);
            addPathIfPossible(pathToFollow, x, y - 1, map, pathCollection);
            addPathIfPossible(pathToFollow, x, y + 1, map, pathCollection);

            Optional<Path> opt = findPathToTargetPosition(pathCollection, target);
            if (opt.isPresent()) {
                return opt.get();
            }
        } while (true);
    }

    private boolean addPathIfPossible(Path pathToFollow, int x, int y, int[][] map, Collection<Path> pathCollection) {
        if (x < 0 || x >= map.length || y < 0 || y >= map.length) {
            return false;
        }

        Optional<Path> newPath = pathToFollow.goTo(Position.of(x, y), map[y][x]);
        if (newPath.isPresent()) {
            pathCollection.add(newPath.get());
            return true;
        }
        return false;
    }

    private static Optional<Path> findPathToTargetPosition(Collection<Path> pathCollection, Position target) {
        Optional<Path> opt = pathCollection.stream()
                .filter(path -> path.contains(target))
                .findFirst();
        return opt;
    }

    private static Path findPathWithCurrentlyLowestRiskLevel(Collection<Path> pathCollection) {
        Optional<Path> opt = pathCollection.stream()
                .min((o1, o2) -> {
                    if (o1.riskLevel == o2.riskLevel) {
                        return 0;
                    }

                    return o1.riskLevel - o2.riskLevel;
                });
        return opt.orElseThrow(() -> new RuntimeException("no path found"));
    }


    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class Path {
        final LinkedList<Position> positions;
        final int riskLevel;

        static Path startFrom(Position start) {
            return new Path(start);
        }

        private Path(Position start) {
            this.positions = new LinkedList<>();
            this.positions.add(start);
            this.riskLevel = 0;
        }

        private Path(LinkedList<Position> positions, int riskLevel) {
            this.positions = new LinkedList<>(positions);
            this.riskLevel = riskLevel;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("id: ").append(this.hashCode()).append(" --- ");
            sb.append("risk: ").append(riskLevel).append(" --- ");

            for (Position position : positions) {
                sb.append("(").append(position.x).append(",").append(position.y).append(") ");
            }

            return sb.toString();
        }

        Position getEndPosition() {
            return positions.getLast();
        }

        Optional<Path> goTo(Position position, int riskLevel) {
            if (positions.contains(position)) {
                return Optional.empty();
            }

            // TODO plausibility check for x and y!

            var newPositions = new LinkedList<>(this.positions);
            newPositions.add(position);
            int newRiskLevel = this.riskLevel + riskLevel;

            return Optional.of(new Path(newPositions, newRiskLevel));
        }

        public boolean contains(Position target) {
            return positions.contains(target);
        }
    }

    record Position(int x, int y) {
        static Position of(int x, int y) {
            return new Position(x, y);
        }
    }
}
