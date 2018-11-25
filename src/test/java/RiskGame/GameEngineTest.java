package RiskGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class GameEngineTest {

    //Name of the file contains list of all territories
    final String territoriesDataFileName = "TerritoryList";

    //The path to the Data folder which contains territories list and their adjacent territories
    final String territoriesDataPath = System.getProperty("user.dir") + File.separator + "Data" + File.separator;

    final String mapPath = System.getProperty("user.dir") + File.separator + "map.jpg";

    private GameEngine getGameEngine() {
        GameEngine gameEngine = new GameEngine();
        return gameEngine;
    }

    @Test
    public void createPlayers() {
        List<Player> players = new ArrayList<>();

        List<String> playerNames = new ArrayList<>();
        playerNames.add("Ton");
        playerNames.add("AI");

        players = getGameEngine().createPlayers(6, 2, playerNames);

        assertEquals(players.size(), 2);
        assertEquals(players.get(0).getPlayerName(), "Ton");
        assertEquals(players.get(1).getPlayerName(), "AI");
    }

    @Test
    public void readTerritoriesData() {
        List<String> territoryNames = getGameEngine()
                .readTerritoriesData(territoriesDataPath, territoriesDataFileName, ".list");

        assertEquals(territoryNames.size(), 42);
        assertEquals(territoryNames.get(0), "Alaska");
    }

    @Test
    public void checkDuplicationsInTerritoryNames() {
        List<String> territoryNames = getGameEngine()
                .readTerritoriesData(territoriesDataPath, territoriesDataFileName, ".list");

        assertFalse(getGameEngine().checkDuplicationsInTerritoryNames(territoryNames));
    }

    @Test
    public void filterOutTerritoryNamesDuplications() {
        List<String> territoryNames = getGameEngine()
                .readTerritoriesData(territoriesDataPath, territoriesDataFileName, ".list");

        assertEquals(getGameEngine().filterOutTerritoryNamesDuplications(territoryNames).size(), 42);
    }

    @Test
    public void createTerritoriesFromNames() {
        List<String> territoryNames = getGameEngine()
                .readTerritoriesData(territoriesDataPath, territoriesDataFileName, ".list");

        assertEquals(getGameEngine().createTerritoriesFromNames(territoryNames).size(), 42);
    }

    @Test
    public void findAdjacentTerritoryNames() {
        String territoryName = "Alaska";
        List<String> territoryNames = getGameEngine()
                .readTerritoriesData(territoriesDataPath, territoryName, ".adj");
        assertEquals(territoryNames.size(), 3);
    }

    @Test
    public void createTerritoryAdjacents() {
        Territory territory = new Territory("Alaska", 0);

        List<Territory> territories = new ArrayList<>();
        territories.add(territory);
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 2));

        List<String> adjTerritoryNames = getGameEngine()
                .readTerritoriesData(territoriesDataPath, territory.getTerritoryName(), ".adj");
        getGameEngine().createTerritoryAdjacents(territory, adjTerritoryNames, territories);

        assertEquals(territory.getAdjTerritories().size(), 3);
        assertEquals(territory.getAdjTerritories().get(0).getTerritoryName(), "Northwest");
        assertEquals(territory.getAdjTerritories().get(1).getTerritoryName(), "Alberta");
        assertEquals(territory.getAdjTerritories().get(2).getTerritoryName(), "Kamchatka");
    }

    @Test
    public void getNumberOfArmyEachPlayer() {
        assertEquals(getGameEngine().getNumberOfArmyEachPlayer(2), 35);
        assertEquals(getGameEngine().getNumberOfArmyEachPlayer(3), 35);
        assertEquals(getGameEngine().getNumberOfArmyEachPlayer(4), 30);
        assertEquals(getGameEngine().getNumberOfArmyEachPlayer(5), 25);
        assertEquals(getGameEngine().getNumberOfArmyEachPlayer(6), 20);
    }

    @Test
    public void checkWinCondition() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Ton", 3));
        players.add(new Player("AI", 3));
        players.add(new Player("Ken", 3));

        assertFalse(getGameEngine().checkWinCondition(players));
    }

    @Test
    public void setTerritory() {
        List<Territory> territories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));

        Player player = new Player("Ton", 3);
        String tName = "Alaska";
        int tIndex = 0;

        //Case 1
        Territory foundTerritory = getGameEngine().setTerritory(player,tName, tIndex, territories);

        if (foundTerritory != null) {
            assertEquals(foundTerritory.getTerritoryName(), "Alaska");
            assertEquals(foundTerritory.getTerritoryIndex(), 0);
            assertEquals(foundTerritory.getOccupiedBy().getPlayerName(), "Ton");
        }

        //Case 2
        tName = null;
        tIndex = 0;
        foundTerritory = getGameEngine().setTerritory(player,tName, tIndex, territories);

        if(foundTerritory != null) {
            assertEquals(foundTerritory.getTerritoryName(), "Alaska");
            assertEquals(foundTerritory.getTerritoryIndex(), 0);
            assertEquals(foundTerritory.getOccupiedBy().getPlayerName(), "Ton");
        }
    }

    @Test
    public void addTroopsToOwnedTerritory() {
        List<Territory> territories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));

        Player player = new Player("Ton", 3);
        String tName = "Alaska";
        int tIndex = 0;

        //Case 1
        Territory foundTerritory = getGameEngine().addTroopsToOwnedTerritory(player,tName, tIndex);

        if (foundTerritory != null) {
            assertEquals(foundTerritory.getTerritoryName(), "Alaska");
            assertEquals(foundTerritory.getTerritoryIndex(), 0);
            assertEquals(foundTerritory.getOccupiedBy().getPlayerName(), "Ton");
            assertEquals(foundTerritory.getNumbOfArmy(), 1);
        }

        //Case 2
        tName = null;
        tIndex = 0;
        foundTerritory = getGameEngine().addTroopsToOwnedTerritory(player,tName, tIndex);

        if(foundTerritory != null) {
            assertEquals(foundTerritory.getTerritoryName(), "Alaska");
            assertEquals(foundTerritory.getTerritoryIndex(), 0);
            assertEquals(foundTerritory.getOccupiedBy().getPlayerName(), "Ton");
            assertEquals(foundTerritory.getNumbOfArmy(), 1);
        }
    }

    @Test
    public void findTerritory() {
        List<Territory> territories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));
        String tName = "Alaska";

        Territory foundTerritory = getGameEngine().findTerritory(tName, territories);

        assertEquals(foundTerritory.getTerritoryName(), "Alaska");
        assertEquals(foundTerritory.getTerritoryIndex(), 0);
    }

    @Test
    public void findTerritory1() {
        List<Territory> territories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));
        int tIndex = 0;

        Territory foundTerritory = getGameEngine().findTerritory(tIndex, territories);

        assertEquals(foundTerritory.getTerritoryName(), "Alaska");
        assertEquals(foundTerritory.getTerritoryIndex(), 0);
    }

    @Test
    public void findTerritory2() {
        List<Territory> territories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));
        int tIndex = 0;
        String tName = "Alaska";

        Territory foundTerritory = getGameEngine().findTerritory(tName, tIndex, territories);

        assertEquals(foundTerritory.getTerritoryName(), "Alaska");
        assertEquals(foundTerritory.getTerritoryIndex(), 0);
    }

    @Test
    public void DisplayMap() {
        getGameEngine().displayMap(mapPath);
    }

    @Test
    public void buildFrame() {
        assertNotNull(getGameEngine().buildFrame());
    }

    @Test
    public void setAllTerritory() {
        List<Territory> territories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));

        List<Territory> finalTerritories = new ArrayList<>();
        finalTerritories.add(new Territory("Alaska", 0));
        finalTerritories.add(new Territory("Northwest", 1));
        finalTerritories.add(new Territory("Alberta", 2));
        finalTerritories.add(new Territory("Kamchatka", 3));

        Player player = new Player("Ton", 3);
        List<Player> players = new ArrayList<>();
        players.add(player);
        String tName = "Alaska";
        int tIndex = 0;

        getGameEngine().setAllTerritory(player, players, territories, finalTerritories, null, mapPath);
    }
}