package it.p0gram3r.adventofcode.y2023;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Day01 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        int result = input.stream()
                .map(Day01::removeNonDigits)
                .map(Day01::determineCalibrationValue)
                .mapToInt(Integer::parseInt)
                .sum();

        return Integer.toString(result);
    }

    static String removeNonDigits(String input) {
        return input.chars()
                .mapToObj(i -> (char) i)
                .filter(Character::isDigit)
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

    static String determineCalibrationValue(String input) {
        return String.valueOf(input.charAt(0)) + input.charAt(input.length() - 1);
    }

    @Override
    public String solutionB(List<String> input) {
        int result =  input.stream()
                .map(parseCalibrationValues)
                .mapToInt(i->i)
                .sum();

        return Integer.toString(result);
    }

    static Function<String, Integer> parseCalibrationValues = (input) -> {
        int firstDigit = findFirstDigit(input);
        int lastDigit = findLastDigit(input);
        return firstDigit * 10 + lastDigit;
    };

    static int findFirstDigit(String input) {
        for (int index = 0; index < input.length(); index++) {
            if (Character.isDigit(input.charAt(index))) {
                return Integer.parseInt(input.substring(index, index + 1));
            }
            for (Numeral numeral : Numeral.values()) {
                if (input.startsWith(numeral.name(), index)) {
                    return numeral.getDigit();
                }
            }
        }
        throw new IllegalArgumentException("input does not contain any digits");
    }

    static int findLastDigit(String input) {
        for (int index = input.length() - 1; index >= 0; index--) {
            if (Character.isDigit(input.charAt(index))) {
                return Integer.parseInt(input.substring(index, index + 1));
            }
            for (Numeral numeral : Numeral.values()) {
                if (input.startsWith(numeral.name(), index)) {
                    return numeral.getDigit();
                }
            }
        }
        throw new IllegalArgumentException("input does not contain any digits");
    }

    @AllArgsConstructor
    @Getter
    enum Numeral {
        one(1),
        two(2),
        three(3),
        four(4),
        five(5),
        six(6),
        seven(7),
        eight(8),
        nine(9);

        private final int digit;
    }
}
