import RiskGame.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void createTerritories() {
    }

    @Test
    public void getNumberOfArmyEachPlayer() {
        int players = 3;
        assertEquals(35, Main.getNumberOfArmyEachPlayer(players));
        assertEquals(25, Main.getNumberOfArmyEachPlayer(5));
        assertEquals(20, Main.getNumberOfArmyEachPlayer(6));
    }

    @Test
    public void userInputRequest() {
    }

    @Test
    public void setTerritory() {
    }

    @Test
    public void setTerritory1() {
    }

    @Test
    public void battleStage() {
    }

    @Test
    public void play() {
    }

    @Test
    public void checkWinCondition() {
        Player player1 = new Player("Kevin", 3);
        Player player2 = new Player("Ton", 3);
        List<Player> totalPlayer1 = new ArrayList<>();
        List<Player> totalPlayer2 = new ArrayList<>();
        totalPlayer2.add(player1);
        totalPlayer2.add(player2);
        assertEquals(true, Main.checkWinCondition(totalPlayer1));
        assertEquals(false, Main.checkWinCondition(totalPlayer2));
    }

    @Test
    public void printTerritory() {
    }

    @Test
    public void printTerritory1() {
    }

    @Test
    public void printTerritory2() {
    }

    @Test
    public void printTerritory3() {
    }

    @Test
    public void printTerritory4() {
    }

    @Test
    public void findTerritory() throws IOException {
        Territory territory1 = new Territory("Alaska", 1);
        Territory territory2 = new Territory("Alberta", 2);
        assertEquals(1, territory1.getTerritoryIndex());
        assertEquals(2, territory2.getTerritoryIndex());
    }
}
