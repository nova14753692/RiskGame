import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DieTest {

    Die die1 = new Die(1,6);

    @BeforeClass
    public static void setUp() {
        System.out.println("Starting Test");
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Test Ended\n");
    }

    @Test
    public void roll() {
        System.out.println("roll test in progress");
        for (int i = 0, rollResult = die1.roll(); i < 12;i++, rollResult = die1.roll()){
            assertTrue(rollResult <= 6 && rollResult >= 1);
        }
    }
}