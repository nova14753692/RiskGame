import org.junit.Test;

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
       assertEquals(35,Main.getNumberOfArmyEachPlayer(players));
       assertEquals(25,Main.getNumberOfArmyEachPlayer(5));
       assertEquals(20,Main.getNumberOfArmyEachPlayer(6));
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
    public void findTerritory() {
    }

    @Test
    public void findTerritory1() {
    }

    @Test
    public void findTerritory2() {
    }

    @Test
    public void displayMap() {
    }
}