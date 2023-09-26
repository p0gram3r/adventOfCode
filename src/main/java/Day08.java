import com.google.auto.service.AutoService;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Value;
import lombok.val;

@AutoService(Puzzle.class)
public class Day08 implements Puzzle {
    @Override
    public long solutionA(List<String> input) {
        return input.stream()
                .map(this::parseOutputValues)
                .flatMap(Collection::stream)
                .filter(s -> s.length() != 5)
                .filter(s -> s.length() != 6)
                .count();
    }

    private List<String> parseOutputValues(String line) {
        String outputPart = line.split("\\|")[1];
        return Arrays.stream(outputPart.split(" ")).filter(s -> !s.isEmpty()).toList();
    }

    @Override
    public long solutionB(List<String> input) {
        return input.stream()
                .map(line -> {
                    List<String> inputValues = parseInputValues(line);
                    final Map<Digit, String> digitMap = deduceDigits(inputValues);

                    List<String> outputValues = parseOutputValues(line);
                    return outputValues.stream()
                            .map(Digit::of)
                            .map(digitMap::get)
                            .collect(Collectors.joining(""));
                })
                .map(Long::parseLong)
                .reduce(0L, Long::sum);
    }

    private Map<Digit, String> deduceDigits(List<String> inputValues) {
        Digit ONE = uniqueDigit(2, inputValues);
        Digit SEVEN = uniqueDigit(3, inputValues);
        Digit FOUR = uniqueDigit(4, inputValues);
        Digit EIGHT = uniqueDigit(7, inputValues);

        Digit THREE = containing(5, inputValues, ONE);
        Digit NINE = containing(6, inputValues, FOUR);

        Digit UL = FOUR.removeSegments(THREE);
        Digit FIVE = containing(5, inputValues, UL);

        Digit LL = EIGHT.removeSegments(NINE);
        Digit TWO = containing(5, inputValues, LL);

        Digit ZERO = containingMultiple(6, inputValues, ONE, LL);

        Digit M = EIGHT.removeSegments(ZERO);
        Digit SIX = containingMultiple(6, inputValues, M, LL);

        Map<Digit, String> digits = new HashMap<>();
        digits.put(ZERO, "0");
        digits.put(ONE, "1");
        digits.put(TWO, "2");
        digits.put(THREE, "3");
        digits.put(FOUR, "4");
        digits.put(FIVE, "5");
        digits.put(SIX, "6");
        digits.put(SEVEN, "7");
        digits.put(EIGHT, "8");
        digits.put(NINE, "9");
        return digits;
    }

    private Digit uniqueDigit(int segmentCount, List<String> inputValues) {
        String segments = inputValues.stream()
                .filter(s -> s.length() == segmentCount)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("input"));
        return Digit.of(segments);
    }

    private Digit containing(int segmentCount, List<String> inputValues, Digit otherDigit) {
        return inputValues.stream()
                .filter(s -> s.length() == segmentCount)
                .map(Digit::of)
                .filter(digit -> digit.contains(otherDigit))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("not unique"));
    }

    private Digit containingMultiple(int segmentCount, List<String> inputValues, Digit firstOtherDigit, Digit secondOtherDigit) {
        return inputValues.stream()
                .filter(s -> s.length() == segmentCount)
                .map(Digit::of)
                .filter(digit -> digit.contains(firstOtherDigit))
                .filter(digit -> digit.contains(secondOtherDigit))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("not unique"));
    }

    private List<String> parseInputValues(String line) {
        String outputPart = line.split("\\|")[0];
        return Arrays.stream(outputPart.split(" ")).filter(s -> !s.isEmpty()).toList();
    }

    @Value
    static class Digit {
        Set<Character> segments;

        private Digit(Set<Character> segments) {
            this.segments = segments;
        }

        static Digit of(String s) {
            Set<Character> charList = getCharacterSet(s);
            return new Digit(charList);
        }


        private static Set<Character> getCharacterSet(String s) {
            val charArray = s.toCharArray();
            Set<Character> charList = new HashSet<>(charArray.length);
            for (char c : charArray) {
                charList.add(c);
            }
            return charList;
        }

        boolean contains(Digit otherDigit) {
            return this.segments.containsAll(otherDigit.segments);
        }

        Digit removeSegments(Digit otherDigit) {
            val remainingSegments = new HashSet<>(this.segments);
            remainingSegments.removeAll(otherDigit.getSegments());
            return new Digit(remainingSegments);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Digit digit = (Digit) o;
            return Objects.equals(segments, digit.segments);
        }

        @Override
        public int hashCode() {
            return Objects.hash(segments);
        }
    }

}
