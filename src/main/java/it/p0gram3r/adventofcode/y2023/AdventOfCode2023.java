package it.p0gram3r.adventofcode.y2023;

import it.p0gram3r.adventofcode.Puzzle;
import it.p0gram3r.adventofcode.PuzzleUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class AdventOfCode2023 {

    public static void main(String[] args) {
        val puzzles = PuzzleUtils.loadPuzzlesInPackage(AdventOfCode2023.class.getPackage());
        for (Puzzle puzzle : puzzles) {
            log.info(puzzle.getClass().getName());
            val inputFileName = PuzzleUtils.guessInputFileName(puzzle);
            val lines = PuzzleUtils.getPuzzleInput(inputFileName);

            log.info("Puzzle: " + puzzle.getClass().getName());
            log.info("  solution to part A: {}", puzzle.solutionA(lines));
            log.info("  solution to part B: {}", puzzle.solutionB(lines));
            log.info("");
        }
    }
}


