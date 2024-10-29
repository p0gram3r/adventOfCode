package it.p0gram3r.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.val;
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

    @Test
    void nodeCompare() {
        var node1 = createNodeWithPathRisk(1);
        var node2 = createNodeWithPathRisk(3);
        var node3 = createNodeWithPathRisk(5);
        var node4 = createNodeWithPathRisk(5);

        assertThat(node1.compareTo(node2)).isLessThan(0);
        assertThat(node1.compareTo(node3)).isLessThan(0);
        assertThat(node1.compareTo(node4)).isLessThan(0);

        assertThat(node2.compareTo(node1)).isGreaterThan(0);
        assertThat(node2.compareTo(node3)).isLessThan(0);
        assertThat(node2.compareTo(node4)).isLessThan(0);

        assertThat(node3.compareTo(node1)).isGreaterThan(0);
        assertThat(node3.compareTo(node2)).isGreaterThan(0);
        assertThat(node3.compareTo(node4)).isEqualTo(0);

        assertThat(node4.compareTo(node1)).isGreaterThan(0);
        assertThat(node4.compareTo(node2)).isGreaterThan(0);
        assertThat(node4.compareTo(node3)).isEqualTo(0);
    }

    private Day15.Node createNodeWithPathRisk(int pathRisk) {
        val node = new Day15.Node(1,1,1);
        node.setPathRisk(pathRisk);
        return node;
    }
}
