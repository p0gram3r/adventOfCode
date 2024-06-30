package it.p0gram3r.adventofcode.y2023;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        List<Game> games = parseGames(input);
        Revelation r = new Revelation(12, 13, 14);

        int result = games.stream()
                .filter(game -> game.isPossible(r))
                .map(game -> game.id)
                .mapToInt(i -> i)
                .sum();

        return Integer.toString(result);
    }


    @Override
    public String solutionB(List<String> input) {
        List<Game> games = parseGames(input);

        int result = games.stream()
                .map(Game::calculateFewestNumberOfCubesToMakeGamePossible)
                .map(Revelation::powerOfSetOfCubes)
                .mapToInt(i -> i)
                .sum();

        return Integer.toString(result);
    }

    static List<Game> parseGames(List<String> input) {
        return input.stream()
                .map(Game::parse)
                .toList();
    }

    record Game(int id, List<Revelation> rounds) {

        boolean isPossible(Revelation maxValues) {
            return rounds.stream()
                    .allMatch(round -> round.isPossible(maxValues));
        }

        Revelation calculateFewestNumberOfCubesToMakeGamePossible() {
            AtomicReference<Revelation> reference = new AtomicReference<>(new Revelation(0, 0, 0));
            rounds.forEach(round -> {
                int red = Integer.max(reference.get().red, round.red);
                int blue = Integer.max(reference.get().blue, round.blue);
                int green = Integer.max(reference.get().green, round.green);
                reference.set(new Revelation(red, green, blue));
            });
            return reference.get();
        }

        static Game parse(String input) {
            int id = Integer.parseInt(input.substring(5, input.indexOf(':')));
            String[] roundStrings = input.substring(input.indexOf(':') + 1).split(";");
            List<Revelation> rounds = Arrays.stream(roundStrings)
                    .map(String::trim)
                    .map(Game::parseRevelation)
                    .toList();
            return new Game(id, rounds);
        }

        static Pattern patternRed = Pattern.compile(".*?(\\d+) red.*", Pattern.CASE_INSENSITIVE);
        static Pattern patternBlue = Pattern.compile(".*?(\\d+) blue.*", Pattern.CASE_INSENSITIVE);
        static Pattern patternGreen = Pattern.compile(".*?(\\d+) green.*", Pattern.CASE_INSENSITIVE);

        private static Revelation parseRevelation(String input) {
            return new Revelation(
                    parseNumber(input, patternRed),
                    parseNumber(input, patternGreen),
                    parseNumber(input, patternBlue)
            );
        }

        private static int parseNumber(String input, Pattern pattern) {
            Matcher matcher = pattern.matcher(input);
            String number = (matcher.matches()) ? matcher.group(1) : "0";
            return Integer.parseInt(number);
        }
    }

    record Revelation(int red, int green, int blue) {

        boolean isPossible(Revelation maxVales) {
            return this.red <= maxVales.red
                    && this.green <= maxVales.green
                    && this.blue <= maxVales.blue;
        }

        int powerOfSetOfCubes() {
            return red * green * blue;
        }
    }

}
