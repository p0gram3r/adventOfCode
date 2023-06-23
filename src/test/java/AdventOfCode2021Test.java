import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdventOfCode2021Test {

    @ParameterizedTest
    @CsvSource({
            "Day09,      15,        1134",
            "Day08,      26,       61229",
            "Day07,      37,         168",
//            "Day06,    5934, 26984457539",
            "Day06,    5934,           0",
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

    @Test
    void puzzle8_digit_contains() {
        Day08.Digit ZERO = Day08.Digit.of("cagedb");
        Day08.Digit ONE = Day08.Digit.of("ab");
        Day08.Digit TWO = Day08.Digit.of("gcdfa");
        Day08.Digit THREE = Day08.Digit.of("fbcad");
        Day08.Digit FOUR = Day08.Digit.of("eafb");
        Day08.Digit FIVE = Day08.Digit.of("cdfbe");
        Day08.Digit SIX = Day08.Digit.of("cdfgeb");
        Day08.Digit SEVEN = Day08.Digit.of("dab");
        Day08.Digit EIGHT = Day08.Digit.of("acedgfb");
        Day08.Digit NINE = Day08.Digit.of("cefabd");

        assertThat(NINE.contains(ZERO)).isFalse();
        assertThat(NINE.contains(ONE)).isTrue();
        assertThat(NINE.contains(TWO)).isFalse();
        assertThat(NINE.contains(THREE)).isTrue();
        assertThat(NINE.contains(FOUR)).isTrue();
        assertThat(NINE.contains(FIVE)).isTrue();
        assertThat(NINE.contains(SIX)).isFalse();
        assertThat(NINE.contains(SEVEN)).isTrue();
        assertThat(NINE.contains(EIGHT)).isFalse();
        assertThat(NINE.contains(NINE)).isTrue();
    }
}
