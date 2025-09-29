package org.keepitsimple.draftlottery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

public class TieredPowerballTest {

    @Test
    public void TieredPowerballTest() {
        String[] teamsRankedFromLowestChance = {"1st", "2nd", "4th", "3rd", "8th", "7th", "6th", "5th"};

        TeamChances[] teamsChances = TieredPowerball.calculateTeamChances(teamsRankedFromLowestChance);

        assertThat(teamsChances[0].getTeam(), is("1st"));
        assertThat(teamsChances[0].getChancesCount(), is(1));

        assertThat(teamsChances[1].getTeam(), is("2nd"));
        assertThat(teamsChances[1].getChancesCount(), is(2));

        assertThat(teamsChances[2].getTeam(), is("4th"));
        assertThat(teamsChances[2].getChancesCount(), is(4));

        assertThat(teamsChances[3].getTeam(), is("3rd"));
        assertThat(teamsChances[3].getChancesCount(), is(8));

        assertThat(teamsChances[4].getTeam(), is("8th"));
        assertThat(teamsChances[4].getChancesCount(), is(16));

        assertThat(teamsChances[5].getTeam(), is("7th"));
        assertThat(teamsChances[5].getChancesCount(), is(32));

        assertThat(teamsChances[6].getTeam(), is("6th"));
        assertThat(teamsChances[6].getChancesCount(), is(64));

        assertThat(teamsChances[7].getTeam(), is("5th"));
        assertThat(teamsChances[7].getChancesCount(), is(128));
    }
}
