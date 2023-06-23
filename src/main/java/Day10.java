import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 implements Puzzle {
    @Override
    public long solutionA(List<String> input) {
        return input.stream()
                .map(NaviSubSystemLine::new)
                .map(NaviSubSystemLine::firstCorruptedChar)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Day10::scoreForCorruptedChar)
                .reduce(0, Integer::sum);

    }

    @Override
    public long solutionB(List<String> input) {
        List<Long> scores = input.stream()
                .map(NaviSubSystemLine::new)
                .filter(NaviSubSystemLine::isNotCorrupt)
                .map(NaviSubSystemLine::autoComplete)
                .map(Day10::scoreForAutocompletedChar)
                .sorted(Long::compare)
                .collect(Collectors.toCollection(ArrayList::new));

        return scores.get(scores.size() / 2);
    }

    static int scoreForCorruptedChar(char illegalChar) {
        return switch (illegalChar) {
            case ')' -> 3;
            case ']' -> 57;
            case '}' -> 1197;
            case '>' -> 25137;
            default -> 0;
        };
    }

    static long scoreForAutocompletedChar(List<Character> autoCompletedChars) {
        long totalScore = 0;
        for (char c : autoCompletedChars) {
            totalScore = totalScore * 5 + scoreForAutocompletedChar(c);
        }
        return totalScore;
    }

    static int scoreForAutocompletedChar(char completedChar) {
        return switch (completedChar) {
            case ')' -> 1;
            case ']' -> 2;
            case '}' -> 3;
            case '>' -> 4;
            default -> 0;
        };
    }

    static class NaviSubSystemLine {
        private final String str;

        NaviSubSystemLine(String str) {
            this.str = str;
        }

        Optional<Character> firstCorruptedChar() {
            Stack<Character> chunks = new Stack<>();
            for (int i = 0; i < str.length(); i++) {
                char token = str.charAt(i);

                if (isStartToken(token)) {
                    chunks.push(token);
                    continue;
                }

                int startToken = chunks.peek();
                if (tokensAreMatching(startToken, token)) {
                    chunks.pop();
                } else {
                    return Optional.of(token);
                }

            }
            return Optional.empty();
        }

        private boolean isStartToken(int token) {
            return token == '(' || token == '[' || token == '{' || token == '<';
        }

        private boolean tokensAreMatching(int token1, int token2) {
            return (token1 == '(' && token2 == ')') || (token1 == '[' && token2 == ']')
                    || (token1 == '{' && token2 == '}') || (token1 == '<' && token2 == '>');
        }

        public boolean isNotCorrupt() {
            return firstCorruptedChar().isEmpty();
        }

        public List<Character> autoComplete() {
            Stack<Character> chunks = new Stack<>();
            for (int i = 0; i < str.length(); i++) {
                char token = str.charAt(i);
                if (isStartToken(token)) {
                    chunks.push(token);
                } else {
                    chunks.pop();
                }
            }
            LinkedList<Character> autoCompletedTokens = new LinkedList<>();
            while(!chunks.isEmpty()) {
                char startToken = chunks.pop();
                autoCompletedTokens.add(closeChunk(startToken));
            }
            return autoCompletedTokens;
        }

        static char closeChunk(char startToken) {
            return switch (startToken) {
                case '(' ->')';
                case '[' -> ']';
                case '{' -> '}';
                case '<' -> '>';
                default -> throw new RuntimeException();
            };
        }
    }
}
