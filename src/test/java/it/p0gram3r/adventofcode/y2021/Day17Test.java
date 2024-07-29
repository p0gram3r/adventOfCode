package it.p0gram3r.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day17Test {

    @Test
    void parseTargetArea() {
        Day17.TargetArea area = Day17.parseTargetArea("target area: x=153..199, y=-114..-75");

        assertThat(area.xMin()).isEqualTo(153);
        assertThat(area.xMax()).isEqualTo(199);
        assertThat(area.yMin()).isEqualTo(-114);
        assertThat(area.yMax()).isEqualTo(-75);
    }

    static Day17.Position position(int x, int y) {
        return new Day17.Position(x, y);
    }

    static Day17.Velocity velocity(int x, int y) {
        return new Day17.Velocity(x, y);
    }

    @Test
    void proveMovement() {
        Day17.Velocity startingVelocity = velocity(7, 2);
        Day17.Probe probe = new Day17.Probe(startingVelocity);

        probe.move();
        assertThat(probe.getCurrentPosition()).isEqualTo(position(7, 2));
        assertThat(probe.getCurrentVelocity()).isEqualTo(velocity(6, 1));

        probe.move();
        assertThat(probe.getCurrentPosition()).isEqualTo(position(13, 3));
        assertThat(probe.getCurrentVelocity()).isEqualTo(velocity(5, 0));

        probe.move();
        assertThat(probe.getCurrentPosition()).isEqualTo(position(18, 3));
        assertThat(probe.getCurrentVelocity()).isEqualTo(velocity(4, -1));

        probe.move();
        assertThat(probe.getCurrentPosition()).isEqualTo(position(22, 2));
        assertThat(probe.getCurrentVelocity()).isEqualTo(velocity(3, -2));

        probe.move();
        assertThat(probe.getCurrentPosition()).isEqualTo(position(25, 0));
        assertThat(probe.getCurrentVelocity()).isEqualTo(velocity(2, -3));

        probe.move();
        assertThat(probe.getCurrentPosition()).isEqualTo(position(27, -3));
        assertThat(probe.getCurrentVelocity()).isEqualTo(velocity(1, -4));

        probe.move();
        assertThat(probe.getCurrentPosition()).isEqualTo(position(28, -7));
        assertThat(probe.getCurrentVelocity()).isEqualTo(velocity(0, -5));
    }

    @Test
    void canPotentiallyReachTargetArea() {
        Day17.Probe probe;
        Day17.Velocity startingVelocity;
        Day17.TargetArea area = Day17.parseTargetArea("target area: x=20..30, y=-10..-5");

        startingVelocity = velocity(7, 2);
        probe = new Day17.Probe(startingVelocity);
        moveProbe(probe, area, 7);
        assertThat(probe.isInTargetArea(area)).isTrue();

        startingVelocity = velocity(6, 3);
        probe = new Day17.Probe(startingVelocity);
        moveProbe(probe, area, 9);
        assertThat(probe.isInTargetArea(area)).isTrue();

        startingVelocity = velocity(9, 0);
        probe = new Day17.Probe(startingVelocity);
        moveProbe(probe, area, 4);
        assertThat(probe.isInTargetArea(area)).isTrue();
    }

    @Test
    void cannotReachTargetArea() {
        Day17.TargetArea area = Day17.parseTargetArea("target area: x=20..30, y=-10..-5");

        Day17.Velocity startingVelocity = velocity(17, -4);
        Day17.Probe probe = new Day17.Probe(startingVelocity);
        assertThat(probe.move().canPotentiallyReachTargetArea(area)).isTrue();
        assertThat(probe.move().canPotentiallyReachTargetArea(area)).isFalse();
    }

    static Day17.Probe moveProbe(Day17.Probe probe, Day17.TargetArea area, int moves) {
        for (int i = 0; i < moves; i++) {
            assertThat(probe.move().canPotentiallyReachTargetArea(area)).isTrue();
        }
        return probe;
    }
}
