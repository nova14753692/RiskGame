package RiskGame;

import org.junit.Test;
import static org.junit.Assert.*;

public class DieTest {

    private Die die = new Die(1,6);

    @Test
    public void roll() {
        for (int i = 0, rollResult = die.roll(); i < 12; i++, rollResult = die.roll()){
            assertTrue(rollResult <= 6 && rollResult >= 1);
        }
    }

    @Test
    public void reset() {
        die.reset();
        assertEquals(die.getCurrentValue(), 0);
    }

    @Test
    public void getCurrentValue() {
        assertEquals(die.getCurrentValue(), 0);
    }

    @Test
    public void getMinValue() {
        assertEquals(die.getMinValue(), 1);
    }

    @Test
    public void getMaxValue() {
        assertEquals(die.getMaxValue(), 6);
    }
}