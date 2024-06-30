package it.p0gram3r.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day01Test {

    @Test
    void removeNonDigits() {
        String input = "a1b2c3";
        String expected = "123";

        assertThat(Day01.removeNonDigits(input)).isEqualTo(expected);
    }

    @Test
    void determineCalibrationValue() {
        String input = "123456";
        String expected = "16";

        assertThat(Day01.determineCalibrationValue(input)).isEqualTo(expected);
    }

}
