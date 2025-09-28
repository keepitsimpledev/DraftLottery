package org.keepitsimple.draftlottery;

import static java.lang.Math.pow;

public class TieredPowerball {

    public static String[] determineDraftOrder(String[] teamsRankedFromLowestChance) {
        //https://www.bigocheatsheet.com/

        TeamChances[] teamsChances = calculateTeamChances(teamsRankedFromLowestChance);

        return null;
    }

    public static TeamChances[] calculateTeamChances(String[] teamsRankedFromLowestChance) {
        TeamChances[] teamsChances = new TeamChances[teamsRankedFromLowestChance.length];
        for (int i = 0; i < teamsRankedFromLowestChance.length; i++) {
            teamsChances[i] = new TeamChances(teamsRankedFromLowestChance[i], (int)Math.round(pow(2d, Double.valueOf(i))));
        }
        return teamsChances;
    }
}
