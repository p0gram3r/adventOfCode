import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AdventOfCode2022Test {

    @Test
    void day02b() {
        var lines = AdventOfCode2022.parseFile("day02-test.txt");
        assertThat(new Day02B().getSolution(lines)).isEqualTo("900");
    }

    @Test
    void day02a() {
        var lines = AdventOfCode2022.parseFile("day02-test.txt");
        assertThat(new Day02A().getSolution(lines)).isEqualTo("150");
    }

    @Test
    void day01b() {
        var lines = AdventOfCode2022.parseFile("day01-test.txt");
        assertThat(new Day01B().getSolution(lines)).isEqualTo("5");
    }

    @Test
    void day01a() {
        var lines = AdventOfCode2022.parseFile("day01-test.txt");
        assertThat(new Day01A().getSolution(lines)).isEqualTo("7");
    }
}
