import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefendTest {

    Player player = new Player("Ton", 3);
    Territory thisTerritory = new Territory("Alaska", 1);
    Territory otherTerritory = new Territory("Alberta", 2);
    Defend defend = new Defend(player, thisTerritory, otherTerritory, 10, 3);

    @BeforeEach
    void setUp() {
        System.out.println("Starting Test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test Ended\n");
    }

    @Test
    void startBattle() {
        System.out.println("startBattle test in progress...");
        boolean result = defend.startBattle(1000);
        assertEquals(true, result);

    }

    @Test
    void afterBattle() {
        System.out.println("afterBattle test in progress...");
        defend.afterBattle(1);
        boolean result = defend.thisPlayer.isLost();
        assertEquals( true, result);
    }
}