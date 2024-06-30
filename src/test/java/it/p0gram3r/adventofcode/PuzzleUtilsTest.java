package it.p0gram3r.adventofcode;

import static org.assertj.core.api.Assertions.assertThat;

import it.p0gram3r.adventofcode.y2021.Day01;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PuzzleUtilsTest {

    @Test
    void guessInputFileName() {
        Puzzle year2021_day01 = new Day01();

        String input = PuzzleUtils.guessInputFileName(year2021_day01);
        assertThat(input).isEqualTo("y2021/day01.txt");
    }

    @Test
    void getPuzzleInput() {
        String fileName = "y2021/day07-test.txt";

        List<String> input = PuzzleUtils.getPuzzleInput(fileName);
        assertThat(input).hasSize(1);
        assertThat(input).containsOnly("16,1,2,0,4,2,7,1,2,14");
    }
}
