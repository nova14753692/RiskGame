

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackTest {

    Player thisPlayer = new Player("Ton", 3);
    Territory thisTerritory = new Territory("Alaska", 1);
    Territory otherTerritory = new Territory("Alberta", 2);
    Attack attack = new Attack(thisPlayer, thisTerritory, otherTerritory, 1,1, 1,1);

    @BeforeEach
    void setUp() {
        System.out.println("Starting Test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test Passed!!!\n");
    }

    @Test
    void startBattle() {
        System.out.println("startBattle test in progress...");
        boolean result = attack.startBattle(1000);
        assertFalse(result);
    }

    @Test
    void afterBattle() {
        System.out.println("afterBattle test in progress...");
        attack.afterBattle(0, 2);
        boolean result = attack.thisPlayer.isLost();
        assertTrue(result);
    }

    @Test
    void getArmyPenalty() {
    }

    @Test
    void setArmyPenalty(){
    }

    @Test
    void getArmyPenaltyToDefender() {
    }

    @Test
    void setArmyPenaltyToDefender() {
    }

}