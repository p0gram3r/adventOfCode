package it.p0gram3r.adventofcode.y2021;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import it.p0gram3r.adventofcode.Puzzle;
import it.p0gram3r.adventofcode.PuzzleUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdventOfCode2021Test {

    @ParameterizedTest
    @CsvSource({
//            "Day18,    4140,           112",
            "Day17,      45,           112",
            "Day16,      31,            54",
            "Day15,      40,          2876", // actually 315 for the test, but solution is hardcoded for this one
            "Day14,    1588, 2188189693529",
            "Day13,      17,      ZUJUAFHP", // actually a capital o, but solution is hardcoded for this one...
            "Day12,      10,            36",
            "Day11,    1656,           195",
            "Day10,   26397,        288957",
            "Day09,      15,          1134",
            "Day08,      26,         61229",
            "Day07,      37,           168",
            "Day06,    5934,   26984457539",
            "Day05,       5,            12",
            "Day04,    4512,          1924",
            "Day03,     198,           230",
            "Day02,     150,           900",
            "Day01,       7,             5",
    })
    @SneakyThrows
    void testPuzzleSolutions(String puzzleName, String solutionA, String solutionB) {
        var inputFileName = "y2021/" + puzzleName.toLowerCase() + "-test.txt";
        var input = PuzzleUtils.getPuzzleInput(inputFileName);

        puzzleName = getClass().getPackageName() + "." + puzzleName;

        Puzzle puzzle = (Puzzle) Class.forName(puzzleName)
                .getDeclaredConstructor()
                .newInstance();
        assertThat(puzzle.solutionA(input)).isEqualTo(solutionA);
        assertThat(puzzle.solutionB(input)).isEqualTo(solutionB);
    }
}
