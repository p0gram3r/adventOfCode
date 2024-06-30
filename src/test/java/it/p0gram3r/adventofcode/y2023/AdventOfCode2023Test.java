package it.p0gram3r.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import it.p0gram3r.adventofcode.Puzzle;
import it.p0gram3r.adventofcode.PuzzleUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdventOfCode2023Test {

    @ParameterizedTest
    @CsvSource({
            "Day01, 142, 281",
            "Day02, 8, 2286",
    })
    @SneakyThrows
    void testPuzzleSolutions(String puzzleName, String solutionA, String solutionB) {
        var inputFileNameA = "y2023/" + puzzleName.toLowerCase() + "a-test.txt";
        var linesA = PuzzleUtils.getPuzzleInput(inputFileNameA);
        var inputFileNameB = "y2023/" + puzzleName.toLowerCase() + "b-test.txt";
        var linesB = PuzzleUtils.getPuzzleInput(inputFileNameB);

        puzzleName = getClass().getPackageName() + "." + puzzleName;

        Puzzle puzzle = (Puzzle) Class.forName(puzzleName)
                .getDeclaredConstructor()
                .newInstance();
        assertThat(puzzle.solutionA(linesA)).isEqualTo(solutionA);
        assertThat(puzzle.solutionB(linesB)).isEqualTo(solutionB);
    }

}
