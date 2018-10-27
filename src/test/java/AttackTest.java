import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttackTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private Player thisPlayer = new Player("Ton", 3);
    private Territory thisTerritory = new Territory("Alaska", 1);
    private Territory otherTerritory = new Territory("Alberta", 2);
    Attack attack = new Attack(thisPlayer, thisTerritory, otherTerritory, 1,1, 1,1);

    @Test
    public void startBattle() {
        System.out.println("startBattle test in progress...");
        boolean result = attack.startBattle(1000);
        assertFalse(result);
    }

    @Test
    public void afterBattle() {
        System.out.println("afterBattle test in progress...");
        attack.afterBattle(0, 2);
        boolean result = attack.thisPlayer.isLost();
        assertTrue(result);
    }

    @Test
    public void afterBattle1() {
    }

    @Test
    public void getArmyPenalty() {
    }

    @Test
    public void setArmyPenalty() {
    }

    @Test
    public void getArmyPenaltyToDefender() {
    }

    @Test
    public void setArmyPenaltyToDefender() {
    }
}