package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day17 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        TargetArea area = parseTargetArea(input.get(0));

        Set<Velocity> set = findAllVelocitiesCausingTheProbeToLandInTargetArea(area);
        int maxYValue = set.stream().map(Velocity::y).mapToInt(i -> i).max().orElseThrow();

        int result = maxYValue * (maxYValue + 1) / 2;
        return Integer.toString(result);
    }

    @Override
    public String solutionB(List<String> input) {
        TargetArea area = parseTargetArea(input.get(0));

        Set<Velocity> set = findAllVelocitiesCausingTheProbeToLandInTargetArea(area);

        return Integer.toString(set.size());
    }

    static TargetArea parseTargetArea(String input) {
        Pattern pattern = Pattern.compile("x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)");
        Matcher matcher = pattern.matcher(input);

        return matcher.results()
                .map(m -> {
                    int xMin = Integer.parseInt(matcher.group(1));
                    int xMax = Integer.parseInt(matcher.group(2));
                    int yMin = Integer.parseInt(matcher.group(3));
                    int yMax = Integer.parseInt(matcher.group(4));
                    return new TargetArea(xMin, xMax, yMin, yMax);
                })
                .findFirst().orElseThrow(() -> new IllegalArgumentException(input));
    }


    private static Set<Velocity> findAllVelocitiesCausingTheProbeToLandInTargetArea(TargetArea area) {
        Set<Velocity> velocitiesToLandInTargetArea = new HashSet<>();
        int maxUnsuccessfulTries = 500;
        for (int x = 1; x <= area.xMax(); x++) {
            int tries = 0;

            for (int y = area.yMin; tries < maxUnsuccessfulTries; y++, tries++) {
                Velocity velocity = new Velocity(x, y);
                Probe probe = new Probe(velocity);

                while (!probe.isInTargetArea(area) && probe.canPotentiallyReachTargetArea(area)) {
                    probe.move();
                }
                if (probe.isInTargetArea(area)) {
                    velocitiesToLandInTargetArea.add(velocity);
                }
            }
        }

        return velocitiesToLandInTargetArea;
    }

    record Position(int x, int y) {
        Position move(Velocity velocity) {
            return new Position(x + velocity.x(), y + velocity.y());
        }
    }

    record Velocity(int x, int y) {

        Velocity adjust() {
            // Due to drag, the probe's x velocity changes by 1 toward the value 0;
            // that is, it decreases by 1 if it is greater than 0,
            // increases by 1 if it is less than 0, or does not change if it is already 0.
            int newX = x;
            if (x > 0) {
                newX -= 1;
            } else if (x < 0) {
                newX += 1;
            }

            // Due to gravity, the probe's y velocity decreases by 1
            int newY = y - 1;

            return new Velocity(newX, newY);
        }

        public String toString() {
            return "velo(" + x + ", " + y + ")";
        }
    }

    record TargetArea(int xMin, int xMax, int yMin, int yMax) {
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class Probe {
        final Velocity initialVelocity;
        Position currentPosition;
        Velocity currentVelocity;

        Probe(Velocity initialVelocity) {
            this.currentPosition = new Position(0, 0);
            this.initialVelocity = initialVelocity;
            this.currentVelocity = initialVelocity;
        }

        Probe move() {
            currentPosition = currentPosition.move(currentVelocity);
            currentVelocity = currentVelocity.adjust();
            return this;
        }

        boolean isInTargetArea(TargetArea area) {
            return currentPosition.x >= area.xMin && currentPosition.x <= area.xMax
                    && currentPosition.y >= area.yMin && currentPosition.y <= area.yMax;
        }

        boolean canPotentiallyReachTargetArea(TargetArea area) {
            if ((currentVelocity.x <= 0 && currentPosition.x < area.xMin)
                    || (currentVelocity.x >= 0 && currentPosition.x > area.xMax)
                    || (currentVelocity.y < 0 && currentPosition.y < area.yMin)) {
                return false;
            }

            return true;
        }
    }
}

