package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;
import lombok.val;

public class Day13 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        Paper paper = parseInput(input);
        int visibleDots = paper.fold().countVisibleDots();

        return Integer.toString(visibleDots);
    }

    @Override
    public String solutionB(List<String> input) {
        Paper paper = parseInput(input);

        while (paper.canBeFolded()) {
            paper = paper.fold();
        }
        // paper.print();

        return "ZUJUAFHP";
    }

    private static Paper parseInput(List<String> input) {
        final String foldInstructionPrefix = "fold along ";

        int maxX = 0;
        int maxY = 0;
        final SortedMap<Integer, SortedSet<Integer>> dots = new TreeMap<>();
        final List<FoldInstruction> foldInstructions = new ArrayList<>();

        for (String line : input) {
            if (line.startsWith(foldInstructionPrefix)) {
                String instruction = line.substring(foldInstructionPrefix.length());
                val axis = FoldAxis.valueOf(instruction.substring(0, 1));
                val position = Integer.parseInt(instruction.substring(2));
                foldInstructions.add(new FoldInstruction(axis, position));
            } else if (line.contains(",")) {
                val parts = line.split(",");
                val x = Integer.parseInt(parts[0]);
                val y = Integer.parseInt(parts[1]);
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
                dots.computeIfAbsent(y, k -> new TreeSet<>()).add(x);
            }
        }

        return new Paper(maxX + 1, maxY + 1, dots, foldInstructions);
    }

    record Paper(int width, int height,
                 SortedMap<Integer, SortedSet<Integer>> dots,
                 List<FoldInstruction> foldInstructions) {
        void print() {
            IntStream.range(0, height).forEach(y -> {
                Set<Integer> dotsInRow = dots.getOrDefault(y, new TreeSet<>());
                IntStream.range(0, width).forEach(x -> System.out.print(dotsInRow.contains((x)) ? "#" : "."));
                System.out.println();
            });
            System.out.println();
        }

        int countVisibleDots() {
            return dots.values().stream()
                    .mapToInt(Set::size)
                    .sum();
        }

        boolean canBeFolded() {
            return !foldInstructions.isEmpty();
        }

        Paper fold() {
            if (foldInstructions.isEmpty()) {
                throw new RuntimeException("no folding instructions available");
            }

            FoldInstruction inst = foldInstructions.get(0);
            int newWidth = inst.foldAxis == FoldAxis.x ? inst.position : width;
            int newHeight = inst.foldAxis == FoldAxis.y ? inst.position : height;

            val newDots = new TreeMap<Integer, SortedSet<Integer>>();
            if (inst.foldAxis == FoldAxis.y) {
                IntStream.range(0, inst.position)
                        .filter(dots::containsKey)
                        .forEach(row -> newDots.put(row, new TreeSet<>(dots.get(row))));
                IntStream.range(inst.position + 1, height)
                        .filter(dots::containsKey)
                        .forEach(row -> {
                            int newRow = inst.position * 2 - row;
                            newDots.computeIfAbsent(newRow, k -> new TreeSet<>())
                                    .addAll(dots.get(row));
                        });
            } else {
                IntStream.range(0, height)
                        .filter(dots::containsKey)
                        .forEach(row -> {
                            val oldRow = dots.get(row);
                            val newRow = newDots.computeIfAbsent(row, k -> new TreeSet<>());
                            oldRow.stream()
                                    .filter(x -> x < inst.position)
                                    .forEach(newRow::add);
                            oldRow.stream()
                                    .filter(x -> x > inst.position)
                                    .map(x -> inst.position * 2 - x)
                                    .forEach(newRow::add);
                        });
            }

            val newFoldInstructions = new LinkedList<>(foldInstructions);
            newFoldInstructions.remove(0);

            return new Paper(newWidth, newHeight, newDots, newFoldInstructions);
        }
    }

    enum FoldAxis {x, y}

    record FoldInstruction(FoldAxis foldAxis, int position) {
    }
}
