package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.PuzzleUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class AdventOfCode2021 {

    public static void main(String[] args) {
        val puzzles = PuzzleUtils.loadPuzzlesInPackage(AdventOfCode2021.class.getPackage());
        PuzzleUtils.solvePuzzles(puzzles, log);
    }

}

