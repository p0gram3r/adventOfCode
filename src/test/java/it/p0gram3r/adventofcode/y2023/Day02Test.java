package it.p0gram3r.adventofcode.y2023;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day02Test {

    @Test
    void parseGame() {
        String input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";

        Day02.Game game = Day02.Game.parse(input);

        assertThat(game.id()).isEqualTo(1);
        assertThat(game.rounds()).hasSize(3);

        Day02.Revelation round = game.rounds().get(0);
        assertThat(round.red()).isEqualTo(4);
        assertThat(round.green()).isEqualTo(0);
        assertThat(round.blue()).isEqualTo(3);

        round = game.rounds().get(1);
        assertThat(round.red()).isEqualTo(1);
        assertThat(round.green()).isEqualTo(2);
        assertThat(round.blue()).isEqualTo(6);

        round = game.rounds().get(2);
        assertThat(round.red()).isEqualTo(0);
        assertThat(round.green()).isEqualTo(2);
        assertThat(round.blue()).isEqualTo(0);
    }

    @Test
    void gameIsPossible() {
        String input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";
        Day02.Game game = Day02.Game.parse(input);

        Day02.Revelation allSix = new Day02.Revelation(6,6,6);
        Day02.Revelation allFour = new Day02.Revelation(4,4,4);

        assertThat(game.isPossible(allSix)).isTrue();
        assertThat(game.isPossible(allFour)).isFalse();
    }
}
