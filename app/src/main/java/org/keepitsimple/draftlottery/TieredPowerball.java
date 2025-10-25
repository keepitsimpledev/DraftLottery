package org.keepitsimple.draftlottery;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TieredPowerball {

    public static TeamChances[] determineDraftOrder(String[] teamsRankedFromLowestChance) {
        List<TeamChances> teamsChances = setTeamChances(teamsRankedFromLowestChance);

        // can upgrade this to stream in Java 21
        int currentTotal = 0;
        for (TeamChances teamChances : teamsChances) {
            currentTotal += teamChances.getChancesCount();
        }

        TeamChances[] draftOrder = new TeamChances[teamsChances.size()];

        for (int draftPosition = 0; !teamsChances.isEmpty(); draftPosition++) {
            long result = Math.round(Math.random() * currentTotal);

            boolean picked = false;
            int current = 0;
            for (int i = 0; i < teamsChances.size(); i++) {
                int chancesCount = teamsChances.get(i).getChancesCount();

                current += chancesCount;

                if (result <= current) {
                    draftOrder[draftPosition] = teamsChances.get(i);
                    currentTotal -= teamsChances.get(i).getChancesCount();
                    teamsChances.remove(i);
                    picked = true;
                    break;
                }
            }
            if (!picked) {
                // improve handling here - maybe make new exception
                throw new RuntimeException("failed to determine draft placement");
            }
        }

        return draftOrder;
    }

    protected static List<TeamChances> setTeamChances(String[] teamsRankedFromLowestChance) {
        int totalChances = 0;
        List<TeamChances> teamsChances = new LinkedList<>();
        for (int i = 0; i < teamsRankedFromLowestChance.length; i++) {
            // `1 << i` is cleaner than `(int)Math.round(pow(2d, Double.valueOf(i)))`
            int chances = 1 << i;
            teamsChances.add(new TeamChances(teamsRankedFromLowestChance[i], chances));
            totalChances += chances;
        }
        for (TeamChances teamChances : teamsChances) {
            teamChances.setPercent((float)teamChances.getChancesCount() / (float)totalChances * 100);
        }
        return teamsChances;
    }

    public static void simulateLottery(PrintStream out, String[] teamsRankedFromLowestChance) {
        TeamChances[] teamsInOrder = determineDraftOrder(teamsRankedFromLowestChance);
        try {
            pause();

            out.println("Welcome to the lottery!👏👏👏👏\n");
            pause();

            out.println("Odds of getting first pick:");
            TeamChances[] orderedByOdds = Arrays.copyOf(teamsInOrder, teamsInOrder.length);
            Collections.sort(Arrays.asList(orderedByOdds), new TeamChancesComparator());

            for (int i = orderedByOdds.length - 1; i >= 0; i--) {
                out.printf("%s: %f%%\n", orderedByOdds[i].getTeam(), orderedByOdds[i].getPercent());
            }
            pause();

            for (int i = 1; i <= teamsInOrder.length; i++) {
                out.printf("\nPick %d goes to...\n", i);
                drumroll(out);
                TeamChances picked = teamsInOrder[i - 1];
                out.printf("%s!\n\n", picked.getTeam());
                pause();
            }
        } catch (InterruptedException e) {
            out.println("Terminal lottery simulation failure.");
        }
    }

    private static void drumroll(PrintStream out) throws InterruptedException {
        pause();
        out.println("...🥁......");
        pause();
        out.println("......🥁...");
        pause();
    }

    private static void pause() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1250);
    }
}
