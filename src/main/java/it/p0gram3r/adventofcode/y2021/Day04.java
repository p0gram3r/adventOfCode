package it.p0gram3r.adventofcode.y2021;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

public class Day04 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        String numbersDrawn = input.get(0);
        val boards = parseBingoBoards(input);

        for (String str : numbersDrawn.split(",")) {
            int number = Integer.parseInt(str);
            for (Board board : boards) {
                board.markNumber(number);
                if (board.boardHasWon()) {
                    long l = number * board.sumAllUnmarkedNumbers();
                    return Long.toString(l);
                }
            }
        }

        throw new IllegalArgumentException("game not solved!");
    }

    @Override
    public String solutionB(List<String> input) {
        String numbersDrawn = input.get(0);
        val boards = parseBingoBoards(input);

        for (String str : numbersDrawn.split(",")) {
            int number = Integer.parseInt(str);
            for (Board board : boards) {
                board.markNumber(number);
                if (!board.boardHasWon()) {
                    continue;
                }
                if (allBoardsHaveWon(boards)) {
                    long l = number * board.sumAllUnmarkedNumbers();
                    return Long.toString(l);
                }
            }
        }

        throw new IllegalArgumentException("game not solved!");
    }

    private ArrayList<Board> parseBingoBoards(List<String> input) {
        val boards = new ArrayList<Board>();
        for (int i = 2; i < input.size(); i += 6) {
            val boardRows = List.of(
                    input.get(i),
                    input.get(i + 1),
                    input.get(i + 2),
                    input.get(i + 3),
                    input.get(i + 4)
            );
            boards.add(new Board(boardRows));
        }
        return boards;
    }

    private boolean allBoardsHaveWon(List<Board> boards) {
        return boards.stream()
                .allMatch(Board::boardHasWon);
    }

    static class Board {
        private final int boardSize;
        private final Entry[][] entries;

        Board(List<String> rows) {
            boardSize = rows.size();
            entries = new Entry[boardSize][boardSize];

            for (int row = 0; row < boardSize; row++) {
                String line = rows.get(row);
                for (int col = 0; col < boardSize; col++) {
                    String val = line.substring(col * 3, col * 3 + 2);
                    entries[row][col] = Entry.of(val);
                }
            }
        }

        void markNumber(int number) {
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    Entry entry = entries[row][col];
                    if (entry.value == number) {
                        entry.checked = true;
                        return;
                    }
                }
            }
        }

        boolean boardHasWon() {
            for (int row = 0; row < boardSize; row++) {
                boolean entireRowChecked = true;
                for (int col = 0; col < boardSize; col++) {
                    Entry entry = entries[row][col];
                    entireRowChecked &= entry.isChecked();
                }
                if (entireRowChecked) {
                    return true;
                }
            }

            for (int col = 0; col < boardSize; col++) {
                boolean entireColChecked = true;
                for (int row = 0; row < boardSize; row++) {
                    Entry entry = entries[row][col];
                    entireColChecked &= entry.isChecked();
                }
                if (entireColChecked) {
                    return true;
                }
            }

            return false;
        }

        long sumAllUnmarkedNumbers() {
            long sum = 0;
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    Entry entry = entries[row][col];
                    if (!entry.isChecked()) {
                        sum += entry.value;
                    }
                }
            }
            return sum;
        }
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @RequiredArgsConstructor
    @AllArgsConstructor
    static class Entry {
        final int value;
        boolean checked = false;

        static Entry of(String val) {
            return new Entry(Integer.parseInt(val.trim()));
        }
    }
}
