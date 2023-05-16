import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AdventOfCode2021Test {

    @Test
    void day03b() {
        var lines = AdventOfCode2021.getPuzzleInput("day03-test.txt");
        assertThat(new Day03B().getSolution(lines)).isEqualTo("230");
    }

    @Test
    void day03a() {
        var lines = AdventOfCode2021.getPuzzleInput("day03-test.txt");
        assertThat(new Day03A().getSolution(lines)).isEqualTo("198");
    }

    @Test
    void day02b() {
        var lines = AdventOfCode2021.getPuzzleInput("day02-test.txt");
        assertThat(new Day02B().getSolution(lines)).isEqualTo("900");
    }

    @Test
    void day02a() {
        var lines = AdventOfCode2021.getPuzzleInput("day02-test.txt");
        assertThat(new Day02A().getSolution(lines)).isEqualTo("150");
    }

    @Test
    void day01b() {
        var lines = AdventOfCode2021.getPuzzleInput("day01-test.txt");
        assertThat(new Day01B().getSolution(lines)).isEqualTo("5");
    }

    @Test
    void day01a() {
        var lines = AdventOfCode2021.getPuzzleInput("day01-test.txt");
        assertThat(new Day01A().getSolution(lines)).isEqualTo("7");
    }
}
