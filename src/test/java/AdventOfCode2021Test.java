import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdventOfCode2021Test {

    @ParameterizedTest
    @CsvSource({
            "Day04,    4512,       1924",
            "Day03,     198,        230",
            "Day02,     150,        900",
            "Day01,       7,          5",
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

    @Test
    void puzzle4BoardTest_winningRow() {
        Day04.Board board = new Day04.Board(List.of(" 1  2  3", " 4  5  6", " 7  8  9"));
        assertThat(board.boardHasWon()).isFalse();

        board.markNumber(1);
        assertThat(board.boardHasWon()).isFalse();
        board.markNumber(2);
        assertThat(board.boardHasWon()).isFalse();
        board.markNumber(3);
        assertThat(board.boardHasWon()).isTrue();

        assertThat(board.sumAllUnmarkedNumbers()).isEqualTo(39);
    }

    @Test
    void puzzle4BoardTest_winningCol() {
        Day04.Board board = new Day04.Board(List.of(" 1  2  3", " 4  5  6", " 7  8  9"));
        assertThat(board.boardHasWon()).isFalse();

        board.markNumber(3);
        assertThat(board.boardHasWon()).isFalse();
        board.markNumber(6);
        assertThat(board.boardHasWon()).isFalse();
        board.markNumber(9);
        assertThat(board.boardHasWon()).isTrue();

        assertThat(board.sumAllUnmarkedNumbers()).isEqualTo(27);
    }
}
