package it.p0gram3r.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import it.p0gram3r.adventofcode.PuzzleUtils;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.Test;

public class Day14Test {

    @Test
    void polymer_initialize() {
        val polymerTemplate = "NNCB";
        val polymer = Day14.Polymer.initialize(polymerTemplate);

        assertThat(polymer.pairCount()).size().isEqualTo(3);
        assertThat(polymer.pairCount()).containsEntry("NN", 1L);
        assertThat(polymer.pairCount()).containsEntry("NC", 1L);
        assertThat(polymer.pairCount()).containsEntry("CB", 1L);

        assertThat(polymer.elementCounts()).size().isEqualTo(3);
        assertThat(polymer.elementCounts()).containsEntry("N", 2L);
        assertThat(polymer.elementCounts()).containsEntry("C", 1L);
        assertThat(polymer.elementCounts()).containsEntry("B", 1L);
    }

    @Test
    void polymer_runInsertionProcess() {
        List<String> input = PuzzleUtils.getPuzzleInput("y2021/day14-test.txt");
        val polymerTemplate = Day14.parsePolymerTemplate(input);
        val insertionRules = Day14.parsePairInsertionRules(input);
        val polymer = Day14.Polymer.initialize(polymerTemplate);

        val step1 = polymer.runInsertionProcess(insertionRules);
        assertThat(step1.pairCount()).size().isEqualTo(6);
        assertThat(step1.pairCount()).containsEntry("NC", 1L);
        assertThat(step1.pairCount()).containsEntry("CN", 1L);
        assertThat(step1.pairCount()).containsEntry("NB", 1L);
        assertThat(step1.pairCount()).containsEntry("BC", 1L);
        assertThat(step1.pairCount()).containsEntry("CH", 1L);
        assertThat(step1.pairCount()).containsEntry("HB", 1L);
        assertThat(step1.elementCounts()).size().isEqualTo(4);
        assertThat(step1.elementCounts()).containsEntry("N", 2L);
        assertThat(step1.elementCounts()).containsEntry("C", 2L);
        assertThat(step1.elementCounts()).containsEntry("B", 2L);
        assertThat(step1.elementCounts()).containsEntry("H", 1L);

        val step2 = step1.runInsertionProcess(insertionRules);
        assertThat(step2.pairCount()).size().isEqualTo(8);
        assertThat(step2.pairCount()).containsEntry("NB", 2L);
        assertThat(step2.pairCount()).containsEntry("BC", 2L);
        assertThat(step2.pairCount()).containsEntry("CC", 1L);
        assertThat(step2.pairCount()).containsEntry("CN", 1L);
        assertThat(step2.pairCount()).containsEntry("BB", 2L);
        assertThat(step2.pairCount()).containsEntry("CB", 2L);
        assertThat(step2.pairCount()).containsEntry("BH", 1L);
        assertThat(step2.pairCount()).containsEntry("HC", 1L);
        assertThat(step2.elementCounts()).size().isEqualTo(4);
        assertThat(step2.elementCounts()).containsEntry("N", 2L);
        assertThat(step2.elementCounts()).containsEntry("C", 4L);
        assertThat(step2.elementCounts()).containsEntry("B", 6L);
        assertThat(step2.elementCounts()).containsEntry("H", 1L);
    }

}
