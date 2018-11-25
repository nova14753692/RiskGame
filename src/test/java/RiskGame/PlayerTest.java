package RiskGame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = new Player("Ton", 1);
    Player player1 = new Player("Ton");
    Player player2 = new Player("Ton", 1, 1, 6);

    @Test
    public void getPlayerName() {
        assertEquals(player.getPlayerName(), "Ton");
    }

    @Test
    public void getNumOfAvailableArmy() {
        assertEquals(player.getNumOfAvailableArmy(), 0);
    }

    @Test
    public void setNumOfAvailableArmy() {
        player.setNumOfAvailableArmy(3);
        assertEquals(player.getNumOfAvailableArmy(), 3);
    }

    @Test
    public void getOwnedTerritories() {
        assertEquals(player.getOwnedTerritories().size(), 0);
    }

    @Test
    public void addOwnedTerritory() {
        Territory territory = new Territory("Alaska", 0);
        player.addOwnedTerritory(territory);
    }

    @Test
    public void getTotalNumbOfArmy() {
        assertEquals(player.getTotalNumbOfArmy(), 0);
    }

    @Test
    public void setBonusArmies() {
        player.setBonusArmies(1);
        assertEquals(player.getNumOfAvailableArmy(), 3);
    }

    @Test
    public void isLost() {
        assertFalse(player.isLost());
    }

    @Test
    public void setLost() {
        player.setLost(true);
        assertTrue(player.isLost());
    }

    @Test
    public void getNumOfDice() {
        assertEquals(player.getNumOfDice(), 1);
    }

    @Test
    public void setNumOfDice() {
        player.setNumOfDice(2);
        assertEquals(player.getNumOfDice(), 2);
    }

    @Test
    public void getDice() {
        assertEquals(player.getDice().size(), 1);
    }

    @Test
    public void getTimer() {
        assertEquals(player.getTimer().getTime(), 0);
        assertEquals(player.getTimer().getTimeOut(), 5);
    }
}