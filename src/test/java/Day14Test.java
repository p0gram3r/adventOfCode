import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import lombok.val;
import org.junit.jupiter.api.Test;

public class Day14Test {

    @Test
    void countElements() {
        val polymer = "AABCDBCAA";
        val countResult = Day14.countElements(polymer);

        assertThat(countResult).hasSize(4);
        assertThat(countResult).containsOnlyKeys("A", "B", "C", "D");
        assertThat(countResult.get("A").get()).isEqualTo(4);
        assertThat(countResult.get("B").get()).isEqualTo(2);
        assertThat(countResult.get("C").get()).isEqualTo(2);
        assertThat(countResult.get("D").get()).isEqualTo(1);
    }

    @Test
    void process() {
        val polymer = "ABC";
        val insertionRules = Map.of("AB", "C", "BC", "D");

        val result = Day14.process(polymer, insertionRules);
        assertThat(result).isEqualTo("ACBDC");
    }
}
