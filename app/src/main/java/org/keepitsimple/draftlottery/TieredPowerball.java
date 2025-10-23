package org.keepitsimple.draftlottery;

import java.io.PrintStream;
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
        int originalTotal = currentTotal;

        TeamChances[] draftOrder = new TeamChances[teamsChances.size()];

        for (int draftPosition = 0; !teamsChances.isEmpty(); draftPosition++) {
            long result = Math.round(Math.random() * currentTotal);

            boolean picked = false;
            int current = 0;
            for (int i = 0; i < teamsChances.size(); i++) {
                int chancesCount = teamsChances.get(i).getChancesCount();
                teamsChances.get(i).setPercent((float)chancesCount / (float)originalTotal * 100);

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
        List<TeamChances> teamsChances = new LinkedList<>();
        for (int i = 0; i < teamsRankedFromLowestChance.length; i++) {
            // `1 << i` is cleaner than `(int)Math.round(pow(2d, Double.valueOf(i)))`
            teamsChances.add(new TeamChances(teamsRankedFromLowestChance[i], 1 << i));
        }
        return teamsChances;
    }

    public static void simulateLottery(PrintStream printStream, String[] teamsRankedFromLowestChance) {
        TeamChances[] teamsInOrder = determineDraftOrder(teamsRankedFromLowestChance);
        try {
            for (int i = 1; i <= teamsInOrder.length; i++) {
                printStream.println("Pick " + i + " goes to...");
                drumroll(printStream);
                TeamChances picked = teamsInOrder[i - 1];
                printStream.println(picked.getTeam() + "! (" + picked.getPercent() + "%)\n");
                pause();
            }
        } catch (InterruptedException e) {
            printStream.println("Terminal lottery simulation failure.");
        }

    }

    private static void drumroll(PrintStream printStream) throws InterruptedException {
        printStream.println("...🥁......");
        pause();
        printStream.println("......🥁...");
        pause();
    }

    private static void pause() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }
}
