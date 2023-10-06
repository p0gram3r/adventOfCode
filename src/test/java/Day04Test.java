import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day04Test {

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
