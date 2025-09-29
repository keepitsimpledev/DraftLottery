package org.keepitsimple.draftlottery;

import static java.lang.Math.pow;
import java.util.LinkedList;
import java.util.List;

public class TieredPowerball {

    public static String[] determineDraftOrder(String[] teamsRankedFromLowestChance) {
        List<TeamChances> teamsChances = calculateTeamChances(teamsRankedFromLowestChance);

        // can upgrade this to stream in Java 21
        int total = 0;
        for (TeamChances teamChances : teamsChances) {
            total += teamChances.getChancesCount();
        }

        String[] draftOrder = new String[teamsChances.size()];

        for (int draftPosition = 0; !teamsChances.isEmpty(); draftPosition++) {
            long result = Math.round(Math.random() * total);

            boolean picked = false;
            int current = 0;
            for (int i = 0; i < teamsChances.size(); i++) {
                current += teamsChances.get(i).getChancesCount();

                if (result <= current) {
                    draftOrder[draftPosition] = teamsChances.get(i).getTeam();
                    teamsChances.remove(i);
                    picked = true;
                    break;
                }
            }
            if (!picked) {
                // improve handling here - maybe make new exception
                throw new RuntimeException("not picked");
            }
        }

        return draftOrder;
    }

    public static List<TeamChances> calculateTeamChances(String[] teamsRankedFromLowestChance) {
        List<TeamChances> teamsChances = new LinkedList<>();
        for (int i = 0; i < teamsRankedFromLowestChance.length; i++) {
            // TODO: make this math less ugly
            teamsChances.add(new TeamChances(teamsRankedFromLowestChance[i], (int)Math.round(pow(2d, Double.valueOf(i)))));
        }
        return teamsChances;
    }
}
