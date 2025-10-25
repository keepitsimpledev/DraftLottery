package org.keepitsimple.draftlottery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

public class TeamChancesComparatorTest {
    @Test
    public void testComparator() {
        TeamChances a = new TeamChances("a", 1);
        TeamChances a2 = new TeamChances("a2", 1);
        TeamChances a4 = new TeamChances("a4", 4);
        TeamChances b = new TeamChances("b", 2);
        TeamChancesComparator c = new TeamChancesComparator();

        assertThat(-1, is(c.compare(a, b)));
        assertThat(0, is(c.compare(a, a2)));
        assertThat(1, is(c.compare(a4, a2)));
    }
}