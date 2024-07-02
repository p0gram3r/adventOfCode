package it.p0gram3r.adventofcode.y2023;

import it.p0gram3r.adventofcode.PuzzleUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class AdventOfCode2023 {

    public static void main(String[] args) {
        val puzzles = PuzzleUtils.loadPuzzlesInPackage(AdventOfCode2023.class.getPackage());
        PuzzleUtils.solvePuzzles(puzzles, log);
    }
}


