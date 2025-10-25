package org.keepitsimple.draftlottery;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

public class TeamChancesTest {

    @Test
    public void equalityTest() {
        // arrange
        TeamChances a = new TeamChances("1st", 1);
        TeamChances b = new TeamChances("1st", 1);

        // assert
        assertThat(a, is(b));
        assertThat(a, equalTo(b));
    }

    @Test
    public void listEqualityTest() {
        // arrange
        TeamChances a = new TeamChances("1st", 1);
        TeamChances b = new TeamChances("2nd", 2);
        List<TeamChances> chances = List.of(a, b);

        // assert
        assertThat(chances, contains(new TeamChances("1st", 1), new TeamChances("2nd", 2)));
    }
}
