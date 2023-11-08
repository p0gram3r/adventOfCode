import com.google.auto.service.AutoService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Value;
import lombok.val;

@AutoService(Puzzle.class)
public class Day12 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        CaveSystem caveSystem = CaveSystem.fromInput(input);
        int size = new SingleVisitStrategy().findAllPaths(caveSystem).size();
        return Integer.toString(size);
    }

    @Override
    public String solutionB(List<String> input) {
        CaveSystem caveSystem = CaveSystem.fromInput(input);
        int size = new DoubleVisitStrategy().findAllPaths(caveSystem).size();
        return Integer.toString(size);
    }

    interface PathFindingStrategy {
        Collection<List<Cave>> findAllPaths(CaveSystem caveSystem);
    }

    private static class SingleVisitStrategy implements PathFindingStrategy {
        @Override
        public Collection<List<Cave>> findAllPaths(CaveSystem caveSystem) {
            Collection<List<Cave>> allPaths = new LinkedHashSet<>();
            findAllPaths(caveSystem, caveSystem.start, List.of(), allPaths);

            return allPaths;
        }

        protected void findAllPaths(CaveSystem caveSystem, Cave currentCave, List<Cave> path, Collection<List<Cave>> allPaths) {
            if (currentCave.isSmallCave() && path.contains(currentCave)) {
                return;
            }

            val newPath = new ArrayList<>(path);
            newPath.add(currentCave);

            if (currentCave == caveSystem.end) {
                allPaths.add(newPath);
                return;
            }

            caveSystem.getConnections(currentCave)
                    .forEach(c -> findAllPaths(caveSystem, c, newPath, allPaths));
        }
    }

    private static class DoubleVisitStrategy implements PathFindingStrategy {
        @Override
        public Collection<List<Cave>> findAllPaths(CaveSystem caveSystem) {
            Collection<List<Cave>> allPaths = new LinkedHashSet<>();

            Collection<Cave> allSmallCaves = caveSystem.connections.keySet().stream()
                    .filter(Cave::isSmallCave)
                    .filter(c -> c != caveSystem.start)
                    .toList();

            findAllPaths(caveSystem, caveSystem.start, List.of(), allPaths, allSmallCaves);

            allSmallCaves.forEach(c -> {
                List<Cave> smallCavesNotVisitedYet = new ArrayList<>(allSmallCaves);
                smallCavesNotVisitedYet.add(c);
                findAllPaths(caveSystem, caveSystem.start, List.of(), allPaths, smallCavesNotVisitedYet);
            });

            return allPaths;
        }

        void findAllPaths(CaveSystem caveSystem, Cave currentCave, List<Cave> path, Collection<List<Cave>> allPaths, Collection<Cave> smallCavesNotVisitedYet) {
            val newPath = new ArrayList<>(path);
            newPath.add(currentCave);

            if (currentCave == caveSystem.end) {
                allPaths.add(newPath);
                return;
            }

            Collection<Cave> newListOfSmallCavesNotVisitedYet;
            if (currentCave.isSmallCave()) {
                newListOfSmallCavesNotVisitedYet = new ArrayList<>(smallCavesNotVisitedYet);
                newListOfSmallCavesNotVisitedYet.remove(currentCave);
            } else {
                newListOfSmallCavesNotVisitedYet = smallCavesNotVisitedYet;
            }

            caveSystem.getConnections(currentCave).stream()
                    .filter(c -> !c.isSmallCave() || newListOfSmallCavesNotVisitedYet.contains(c))
                    .forEach(c -> findAllPaths(caveSystem, c, newPath, allPaths, newListOfSmallCavesNotVisitedYet));
        }
    }

    @Value
    private static class CaveSystem {
        Cave start = new Cave("start");
        Cave end = new Cave("end");
        Map<Cave, Set<Cave>> connections = new LinkedHashMap<>();

        void addConnection(Cave a, Cave b) {
            connections.computeIfAbsent(a, c -> new LinkedHashSet<>()).add(b);
            connections.computeIfAbsent(b, c -> new LinkedHashSet<>()).add(a);
        }

        Collection<Cave> getConnections(Cave cave) {
            return connections.getOrDefault(cave, Set.of());
        }

        static CaveSystem fromInput(List<String> input) {
            CaveSystem caveSystem = new CaveSystem();

            for (String line : input) {
                String[] caveNames = line.split("-");

                Cave a = caveSystem.createCave(caveNames[0]);
                Cave b = caveSystem.createCave(caveNames[1]);
                caveSystem.addConnection(a, b);
            }

            return caveSystem;
        }

        private Cave createCave(String name) {
            if (name.equalsIgnoreCase("start")) {
                return start;
            }
            if (name.equalsIgnoreCase("end")) {
                return end;
            }
            return new Cave(name);
        }
    }


    private record Cave(String name) {
        public boolean isSmallCave() {
            return name.toLowerCase().equals(name);
        }
    }
}
