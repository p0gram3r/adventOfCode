package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.ServiceLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class AdventOfCode2021 {

    public static void main(String[] args) {

        val providers = ServiceLoader.load(Puzzle.class);
        for (Puzzle puzzle : providers) {
            val inputFileName = Puzzle.guessInputFileName(puzzle);
            val lines = Puzzle.getPuzzleInput(inputFileName);

            log.info("Puzzle: " + puzzle.getClass().getSimpleName());
            log.info("  solution to part A: {}", puzzle.solutionA(lines));
            log.info("  solution to part B: {}", puzzle.solutionB(lines));
            log.info("");
        }
    }

}

