import RiskGame.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class DieTest {
    Die die1 = new Die(1,6);
    @Test
    public void roll() {
    for (int i = 0, rollResult = die1.roll(); i < 12;i++, rollResult = die1.roll()){
        assertTrue(rollResult <= 6 && rollResult >= 1);
    }
    }
}
