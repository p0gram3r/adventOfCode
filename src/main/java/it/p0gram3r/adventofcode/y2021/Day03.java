package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        int arrayLength = input.get(0).length();
        int[] ones = new int[arrayLength];
        int[] zeros = new int[arrayLength];

        countBitsInEachString(input, ones, zeros);

        StringBuilder gammaString = new StringBuilder();
        StringBuilder epsilonString = new StringBuilder();
        for (int i = 0; i < arrayLength; i++) {
            String mostCommonBit = zeros[i] > ones[i] ? "0" : "1";
            String leastCommonBit = zeros[i] < ones[i] ? "0" : "1";
            gammaString.append(mostCommonBit);
            epsilonString.append(leastCommonBit);
        }

        long gammaRate = Long.parseLong(gammaString.toString(), 2);
        long epsilonRate = Long.parseLong(epsilonString.toString(), 2);

        return Long.toString(gammaRate * epsilonRate);
    }


    @Override
    public String solutionB(List<String> input) {
        String oxyString = determineRating(input, true);
        String co2String = determineRating(input, false);

        long oxy = Long.parseLong(oxyString, 2);
        long co2 = Long.parseLong(co2String, 2);
        return Long.toString(oxy * co2);
    }

    private static void countBitsInEachString(List<String> input, int[] ones, int[] zeros) {
        input.forEach(line -> {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                switch (c) {
                    case '0' -> zeros[i]++;
                    case '1' -> ones[i]++;
                    default -> throw new IllegalArgumentException(line);
                }
            }
        });
    }

    private String determineRating(List<String> input, boolean considerMostCommon) {
        int arrayLength = input.get(0).length();

        for (int pos = 0; pos < arrayLength; pos++) {
            int[] ones = new int[arrayLength];
            int[] zeros = new int[arrayLength];

            countBitsInEachString(input, ones, zeros);

            char bitToConsider = determineBitToConsider(zeros, ones, pos, considerMostCommon);

            input = filter(input, pos, bitToConsider);
            if (input.size() == 1) {
                break;
            }
        }

        if (input.size() != 1) {
            throw new IllegalArgumentException("something's wrong!");
        }

        return input.get(0);
    }

    private List<String> filter(List<String> list, int position, char bitToConsider) {
        return list.stream()
                .filter(s -> s.charAt(position) == bitToConsider)
                .collect(Collectors.toList());
    }

    private char determineBitToConsider(int[] zeros, int[] ones, int position, boolean considerMostCommon) {
        if (considerMostCommon) {
            return ones[position] >= zeros[position] ? '1' : '0';
        } else {
            return ones[position] < zeros[position] ? '1' : '0';
        }
    }
}
