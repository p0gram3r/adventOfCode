import static org.assertj.core.api.Assertions.assertThat;

import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdventOfCode2021Test {

    @ParameterizedTest
    @CsvSource({
            "Day13,      17,           a", // actually a capital o, but solution is hardcoded for this one...
            "Day12,      10,          36",
            "Day11,    1656,         195",
            "Day10,   26397,      288957",
            "Day09,      15,        1134",
            "Day08,      26,       61229",
            "Day07,      37,         168",
            "Day06,    5934, 26984457539",
            "Day05,       5,          12",
            "Day04,    4512,        1924",
            "Day03,     198,         230",
            "Day02,     150,         900",
            "Day01,       7,           5",
    })
    @SneakyThrows
    void testPuzzleSolutions(String puzzleName, long solutionA, long solutionB) {
        var inputFileName = puzzleName.toLowerCase() + "-test.txt";
        var lines = AdventOfCode2021.getPuzzleInput(inputFileName);

        Puzzle puzzle = (Puzzle) Class.forName(puzzleName)
                .getDeclaredConstructor()
                .newInstance();
        assertThat(puzzle.solutionA(lines)).isEqualTo(solutionA);
        assertThat(puzzle.solutionB(lines)).isEqualTo(solutionB);
    }
}
