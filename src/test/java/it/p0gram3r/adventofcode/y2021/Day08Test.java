package it.p0gram3r.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day08Test {

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
