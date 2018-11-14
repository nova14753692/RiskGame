package RiskGame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DieTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Starting Test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test Ended\n");
    }

    Die die1 = new Die(1,6);

    @Test
    public void roll() {
        System.out.println("roll test in progress");
        for (int i = 0, rollResult = die1.roll(); i < 12;i++, rollResult = die1.roll()){
            assertTrue(rollResult <= 6 && rollResult >= 1);
        }
    }

    @Test
    public void reset() {
        Die die2 = new Die(1,6);
        die2.reset();
        assertEquals(0, die2.getCurrentValue());
    }

    @Test
    public void getCurrentValue() {
    }

    @Test
    public void getMinValue() {
    }

    @Test
    public void getMaxValue() {
    }
}