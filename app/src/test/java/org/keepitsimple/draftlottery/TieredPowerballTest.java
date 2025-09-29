package org.keepitsimple.draftlottery;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

public class TieredPowerballTest {

    // TODO: consider parameterizing these three tests
    @Test
    public void determineDraftOrderOneTeamTest() {
        String[] teams = {"1st"};
        String[] result = TieredPowerball.determineDraftOrder(teams);

        assertThat(result.length, is(teams.length));
        assertThat(result[0], is("1st"));
    }

    @Test
    public void determineDraftOrderTwoTeamsTest() {
        String[] teams = {"1st", "2nd"};
        String[] result = TieredPowerball.determineDraftOrder(teams);

        assertThat(result.length, is(teams.length));
        assertThat(List.of(result), containsInAnyOrder(teams));
    }

    @Test
    public void determineDraftOrderThreeTeamsTest() {
        String[] teams = {"1st", "2nd", "3rd"};
        String[] result = TieredPowerball.determineDraftOrder(teams);

        assertThat(result.length, is(teams.length));
        assertThat(List.of(result), containsInAnyOrder(teams));
    }

    @Test
    public void calculateTeamChancesTest() {
        String[] teamsRankedFromLowestChance = {"1st", "2nd", "4th", "3rd", "8th", "7th", "6th", "5th"};

        List<TeamChances> teamsChances = TieredPowerball.calculateTeamChances(teamsRankedFromLowestChance);

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
