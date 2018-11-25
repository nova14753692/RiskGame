package RiskGame;

import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    Pair<Integer, Integer> pair = new Pair<Integer, Integer>(1, 2);

    @Test
    public void getFirst() {
        assertEquals(pair.getFirst(), Integer.valueOf(1));
    }

    @Test
    public void setFirst() {
        pair.setFirst(2);
        assertEquals(pair.getFirst(), Integer.valueOf(2));
    }

    @Test
    public void getSecond() {
        assertEquals(pair.getSecond(), Integer.valueOf(2));
    }

    @Test
    public void setSecond() {
        pair.setSecond(3);
        assertEquals(pair.getSecond(), Integer.valueOf(3));
    }
}