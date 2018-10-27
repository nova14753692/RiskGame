import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefendTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Starting Test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test Ended\n");
    }

    private Player player = new Player("Ton", 3);
    private Territory thisTerritory = new Territory("Alaska", 1);
    private Territory otherTerritory = new Territory("Alberta", 2);
    Defend defend = new Defend(player, thisTerritory, otherTerritory, 10, 3);

    @Test
    public void startBattle() {
        System.out.println("startBattle test in progress...");
        boolean result = defend.startBattle(1000);
        assertTrue(result);
    }

    @Test
    public void afterBattle() {
        System.out.println("afterBattle test in progress...");
        defend.afterBattle(1);
        boolean result = defend.thisPlayer.isLost();
        assertTrue(result);
    }

}