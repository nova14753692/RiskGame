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
        assertFalse(getGameEngine().checkDuplicationsInTerritoryNames(null));
    }

    @Test
    public void filterOutTerritoryNamesDuplications() {
        List<String> territoryNames = getGameEngine()
                .readTerritoriesData(territoriesDataPath, territoriesDataFileName, ".list");

        assertEquals(getGameEngine().filterOutTerritoryNamesDuplications(territoryNames).size(), 42);
        assertNull(getGameEngine().filterOutTerritoryNamesDuplications(null));
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
    public void printTerritory1() {
        List<Territory> territories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));

        List<Player> players = new ArrayList<>();
        players.add(new Player("Ton"));
        players.add(new Player("AI"));

        getGameEngine().printTerritory(players, territories, null);
    }

    @Test
    public void printTerritory2() {
        List<Territory> territories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));

        List<Player> players = new ArrayList<>();
        Player player = new Player("Ton");
        players.add(player);
        player.getOwnedTerritories().addAll(territories);
        players.add(new Player("AI"));

        getGameEngine().printTerritory(player, players, territories, null);
    }

    @Test
    public void printTerritory3() {
        List<Territory> territories = new ArrayList<>();
        Territory territory = new Territory("Alaska", 0);
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));
        territory.getAdjTerritories().addAll(territories);
        territories.add(territory);

        getGameEngine().printTerritory(0, territories, null);
        getGameEngine().printTerritory(4, territories, null);
    }

    @Test
    public void printTerritory4() {
        List<Territory> territories = new ArrayList<>();
        Territory territory = new Territory("Alaska", 0);
        territories.add(new Territory("Northwest", 1));
        territories.add(new Territory("Alberta", 2));
        territories.add(new Territory("Kamchatka", 3));
        territory.getAdjTerritories().addAll(territories);
        territories.add(territory);

        getGameEngine().printTerritory("Alaska", territories, null);
        getGameEngine().printTerritory("something", territories, null);
    }

    @Test
    public void DisplayMap() {
        getGameEngine().displayMap();
    }

    @Test
    public void buildFrame() {
        assertNotNull(getGameEngine().buildFrame());
    }

    @Test
    public void play() {
        Player atk = new Player("Ton");
        Player def = new Player("Ton");

        Territory atkTerritory = new Territory("Alaska", 0);
        Territory defTerritory = new Territory("Northwest", 1);

        atk.getOwnedTerritories().add(atkTerritory);
        def.getOwnedTerritories().add(defTerritory);

        atkTerritory.setNumbOfArmy(2);
        defTerritory.setNumbOfArmy(2);

        atkTerritory.setOccupiedBy(atk);
        defTerritory.setOccupiedBy(def);

        getGameEngine().play(atk, atkTerritory, def, defTerritory, null);

        //Case 2
        defTerritory.setNumbOfArmy(1);
        getGameEngine().play(atk, atkTerritory, def, defTerritory, null);
    }

    @Test
    public void battleStage() {
        Player atk = new Player("Ton");
        Player def = new Player("Ton");
        List<Player> players = new ArrayList<>();
        players.add(atk);
        players.add(def);

        Territory atkTerritory = new Territory("Alaska", 0);
        Territory defTerritory = new Territory("Northwest", 1);
        List<Territory> territories = new ArrayList<>();
        territories.add(atkTerritory);
        territories.add(defTerritory);

        atk.getOwnedTerritories().add(atkTerritory);
        def.getOwnedTerritories().add(defTerritory);

        atkTerritory.setNumbOfArmy(2);
        defTerritory.setNumbOfArmy(2);

        atkTerritory.setOccupiedBy(atk);
        defTerritory.setOccupiedBy(def);

        getGameEngine().battleStage(territories, players, null);
    }

    @Test
    public void setAllTerritory() {
        Player player = new Player("Ton");
        List<Player> players = new ArrayList<>();
        players.add(player);
        player.setNumOfAvailableArmy(3);
        List<Territory> territories = new ArrayList<>();
        List<Territory> finalTerritories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        finalTerritories.add(new Territory("Alaska", 0));
        finalTerritories.add(new Territory("Northwest", 1));

        getGameEngine().setAllTerritory(player, players, territories, finalTerritories, null);
    }

    @Test
    public void createTerritory() {
        getGameEngine().createTerritories(territoriesDataPath, territoriesDataFileName, null);
    }

    @Test
    public void createPlayer2() {
        getGameEngine().createPlayers(1, null);
    }

    @Test
    public void executeSpecialCommand() {
        Player player = new Player("Ton");
        List<Player> players = new ArrayList<>();
        players.add(player);
        player.setNumOfAvailableArmy(3);
        List<Territory> territories = new ArrayList<>();
        List<Territory> finalTerritories = new ArrayList<>();
        territories.add(new Territory("Alaska", 0));
        territories.add(new Territory("Northwest", 1));
        finalTerritories.add(new Territory("Alaska", 0));
        finalTerritories.add(new Territory("Northwest", 1));

        getGameEngine().executeSpecialCommand("-la", players, player, territories, territories, null);
    }

    @Test
    public void startGame() {
        getGameEngine().startGame(null);
    }
}