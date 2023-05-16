import static org.assertj.core.api.Assertions.assertThat;

import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdventOfCode2021Test {

    @ParameterizedTest
    @CsvSource({
            "Day03,     198,        230",
            "Day02,     150,        900",
            "Day01        7,        5",
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
