package org.keepitsimple.draftlottery;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TieredPowerballTest {

    // TODO: consider parameterizing these three tests
    @Test
    public void determineDraftOrderOneTeamTest() {
        // arrange
        String[] teams = {"1st"};

        // act
        TeamChances[] result = TieredPowerball.determineDraftOrder(teams);

        // assert
        assertThat(result.length, is(teams.length));
        TeamChances expected = new TeamChances("1st", 1);
        expected.setPercent(100);
        assertThat(result[0], is(expected));
    }

    @Test
    public void determineDraftOrderTwoTeamsTest() {
        // arrange
        String[] teams = {"1st", "2nd"};

        // act
        TeamChances[] result = TieredPowerball.determineDraftOrder(teams);

        // assert
        assertThat(result.length, is(teams.length));
        TeamChances[] expected = {new TeamChances("1st", 1), new TeamChances("2nd", 2)};
        expected[0].setPercent(1f / 3f * 100);
        expected[1].setPercent(2f / 3f * 100);
        assertThat(List.of(result), containsInAnyOrder(expected[0], expected[1]));
    }

    @Test
    public void determineDraftOrderThreeTeamsTest() {
        // arrange
        String[] teams = {"1st", "2nd", "3rd"};

        // act
        TeamChances[] result = TieredPowerball.determineDraftOrder(teams);

        // assert
        assertThat(result.length, is(teams.length));
        TeamChances[] expected = {new TeamChances("1st", 1), new TeamChances("2nd", 2), new TeamChances("3rd", 4)};
        expected[0].setPercent(1f / 7f * 100);
        expected[1].setPercent(2f / 7f * 100);
        expected[2].setPercent(4f / 7f * 100);
        assertThat(List.of(result), containsInAnyOrder(expected[0], expected[1], expected[2]));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void testPercent(int candidateCount) {
        String[] teams = new String[10];

        for (int i = 0 ; i < candidateCount; i++) {
            teams[i] = "" + i;
        }

        TeamChances[] teamsChances = TieredPowerball.determineDraftOrder(teams);

        float sum = 0;
        for (TeamChances teamChances : teamsChances) {
            sum += teamChances.getPercent();
        }
        assertThat(100d, closeTo(sum, .00001d));
    }

    @Test
    public void calculateTeamChancesTest() {
        // arrange
        String[] teamsRankedFromLowestChance = {"1st", "2nd", "4th", "3rd", "8th", "7th", "6th", "5th"};

        // act
        List<TeamChances> teamsChances = TieredPowerball.setTeamChances(teamsRankedFromLowestChance);

        // assert
        assertThat(teamsChances.get(0).getTeam(), is("1st"));
        assertThat(teamsChances.get(0).getChancesCount(), is(1));

        assertThat(teamsChances.get(1).getTeam(), is("2nd"));
        assertThat(teamsChances.get(1).getChancesCount(), is(2));

        assertThat(teamsChances.get(2).getTeam(), is("4th"));
        assertThat(teamsChances.get(2).getChancesCount(), is(4));

        assertThat(teamsChances.get(3).getTeam(), is("3rd"));
        assertThat(teamsChances.get(3).getChancesCount(), is(8));

        assertThat(teamsChances.get(4).getTeam(), is("8th"));
        assertThat(teamsChances.get(4).getChancesCount(), is(16));

        assertThat(teamsChances.get(5).getTeam(), is("7th"));
        assertThat(teamsChances.get(5).getChancesCount(), is(32));

        assertThat(teamsChances.get(6).getTeam(), is("6th"));
        assertThat(teamsChances.get(6).getChancesCount(), is(64));

        assertThat(teamsChances.get(7).getTeam(), is("5th"));
        assertThat(teamsChances.get(7).getChancesCount(), is(128));
    }
}
