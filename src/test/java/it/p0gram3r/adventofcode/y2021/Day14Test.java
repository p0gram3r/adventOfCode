package it.p0gram3r.adventofcode.y2021;

import java.util.Map;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day14Test {

    @Test
    void countElements() {
        val polymer = "AABCDBCAA";
        val countResult = Day14.countElements(polymer);

        Assertions.assertThat(countResult).hasSize(4);
        Assertions.assertThat(countResult).containsOnlyKeys("A", "B", "C", "D");
        Assertions.assertThat(countResult.get("A").get()).isEqualTo(4);
        Assertions.assertThat(countResult.get("B").get()).isEqualTo(2);
        Assertions.assertThat(countResult.get("C").get()).isEqualTo(2);
        Assertions.assertThat(countResult.get("D").get()).isEqualTo(1);
    }

    @Test
    void process() {
        val polymer = "ABC";
        val insertionRules = Map.of("AB", "C", "BC", "D");

        val result = Day14.process(polymer, insertionRules);
        Assertions.assertThat(result).isEqualTo("ACBDC");
    }
}
