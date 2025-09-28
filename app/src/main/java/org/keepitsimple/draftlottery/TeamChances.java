package org.keepitsimple.draftlottery;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TeamChances {
    @Getter @Setter
    private String team;
    
    @Getter @Setter
    private int chancesCount;
}
