import RiskGame.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

public class DefendTest {
    Defend defend;

    @Test
    public void  Canary() {
        assertTrue(true);
    }

    @Test
    public void StartBattleTest() {
        Player player = new Player("Ton", 3);
        Territory thisTerritory = new Territory("Alaska", 1);
        Territory otherTerritory = new Territory("Alberta", 2);
        defend = new Defend(player, thisTerritory, otherTerritory, 10, 3);
        boolean result = defend.startBattle(1000);
        assertEquals(true, result);
    }

    @Test
    public void AfterBattleTest(){
        Player player = new Player("Ton", 3);
        Territory thisTerritory = new Territory("Alaska", 1);
        Territory otherTerritory = new Territory("Alberta", 2);
        defend = new Defend(player, thisTerritory, otherTerritory, 10, 3);
        defend.afterBattle(1);
        boolean result = defend.getThisPlayer().isLost();
        assertEquals( true, result);
    }

    @Test
    public void AfterBattleTestSuccessDefence(){
        Player player = new Player("Ton", 3);
        Territory thisTerritory = new Territory("Alaska", 1);
        Territory otherTerritory = new Territory("Alberta", 2);
        defend = new Defend(player, thisTerritory, otherTerritory, 10, 3);
        defend.afterBattle(-3);
        boolean result = defend.getThisPlayer().isLost();
        assertEquals( false, result);
    }
}