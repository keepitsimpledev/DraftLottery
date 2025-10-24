package org.keepitsimple.draftlottery;

import java.util.Comparator;

public class TeamChancesComparator implements Comparator<TeamChances> {
    @Override
    public int compare(TeamChances a, TeamChances b) {
       return Integer.compare(a.getChancesCount(), b.getChancesCount());
    }
}
