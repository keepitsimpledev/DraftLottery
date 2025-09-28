package org.keepitsimple.draftlottery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TieredPowerballTest {

    @ParameterizedTest
    @CsvSource({"0,1st,1", "1,2nd,2", "2,4th,4"})
    public void TieredPowerballTest(int index, String team, int chances) {
        String[] teamsRankedFromLowestChance = {"1st", "2nd", "4th", "3rd", "8th", "7th", "6th", "5th"};

        TeamChances[] teamsChances = TieredPowerball.calculateTeamChances(teamsRankedFromLowestChance);

        assertThat(teamsChances[index].getTeam(), is(team));
        assertThat(teamsChances[index].getChancesCount(), is(chances));
    }
}
