import RiskGame.Army;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArmyTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void getNumbOfArmy() {
        final int value = 1;
        Army a = new Army(value);
        long result = a.getNumbOfArmy();
        assertEquals(value, result);
    }

    @Test
    public void setNumbOfArmy() {
    }

    @Test
    public void getArmyName() {
    }

    @Test
    public void setArmyName() {
    }

    @Test
    public void getLowerBound() {
    }

    @Test
    public void setLowerBound() {
    }

    @Test
    public void getUpperBound() {
    }

    @Test
    public void setUpperBound() {
    }

    @Test
    public void getNextType() {
    }
}