package org.keepitsimple.draftlottery;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class TeamChances {

    public TeamChances(String team, int chancesCount) {
        this.team = team;
        this.chancesCount = chancesCount;
    }

    @Getter @Setter @NonNull
    private String team;
    
    @Getter @Setter
    private int chancesCount;
    
    @Getter @Setter
    private float percent;

    @Override
    public final int hashCode() {
        return team.hashCode() + chancesCount + Float.hashCode(percent);
    }

    @Override
    public boolean equals(Object o) {
        if (null == o || !(o instanceof TeamChances)) {
            return false;
        } else {
            return this.hashCode() == o.hashCode();
        }
    }
}
