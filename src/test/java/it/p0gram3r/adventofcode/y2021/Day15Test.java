package it.p0gram3r.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day15Test {

    @Test
    void parseTargetArea() {
        var map = Day15.parseCavernMap(List.of("8"));

        var fullMap = Day15.scaleToFullCavernMap(map);

        assertThat(fullMap.length).isEqualTo(5);
        assertThat(fullMap[0].length).isEqualTo(5);
        assertThat(fullMap[4][4]).isEqualTo(7);
    }
}
