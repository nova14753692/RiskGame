package RiskGame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPlayerName() {
        Player player1 = new Player("Kevin");
        String expectedOutput = player1.getPlayerName();
        assertEquals("Kevin",expectedOutput);
    }

    @Test
    public void getNumOfAvailableArmy() {
        Player player1 = new Player("Kevin");
        player1.setNumOfAvailableArmy(6);
        assertEquals(6,player1.getNumOfAvailableArmy());
    }

    @Test
    public void setNumOfAvailableArmy() {
    }

    @Test
    public void getOwnedTerritories() {

    }

    @Test
    public void addOwnedTerritory() {
        Player player1 = new Player("Kevin");
        Territory thisTerritory = new Territory("Alaska", 1);
        player1.addOwnedTerritory(thisTerritory);
        player1.setNumOfAvailableArmy(1);
        assertEquals(1,player1.getTotalNumbOfArmy());
    }

    @Test
    public void getTotalNumbOfArmy() {
    }

    @Test
    public void setBonusArmies() {
    }

    @Test
    public void printRolledDice() throws Exception{
    }

    @Test
    public void isLost() {

    }

    @Test
    public void setLost() {
    }

    @Test
    public void getNumOfDice() {
    }

    @Test
    public void setNumOfDice() {
    }

    @Test
    public void getDice() {
    }

    @Test
    public void getOwnedCards() {
    }
}