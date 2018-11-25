package RiskGame;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TerritoryTest {

    Territory territory = new Territory("Alaska", 0);

    private List<Territory> getAdjTerritory() {
        if (territory.getAdjTerritories().size() == 0) {
            territory.getAdjTerritories().add(new Territory("Northwest", 1));
            territory.getAdjTerritories().add(new Territory("Alberta", 2));
            territory.getAdjTerritories().add(new Territory("Kamchatka", 3));
        }
        return territory.getAdjTerritories();
    }

    @Test
    public void getTerritoryName() {
        assertEquals(territory.getTerritoryName(), "Alaska");
    }

    @Test
    public void getTerritoryIndex() {
        assertEquals(territory.getTerritoryIndex(), 0);
    }

    @Test
    public void getAdjTerritories() {
        assertEquals(getAdjTerritory().size(), 3);
    }

    @Test
    public void isOccupied() {
        assertFalse(territory.isOccupied());
    }

    @Test
    public void setOccupied() {
        territory.setOccupied(false);
        assertFalse(territory.isOccupied());
    }

    @Test
    public void getOccupiedBy() {
        Player player = new Player("Ton");
        territory.setOccupiedBy(player);
        assertTrue(territory.isOccupied());
        assertEquals(territory.getOccupiedBy().getPlayerName(),"Ton");
    }

    @Test
    public void setOccupiedBy() {
        Player player = new Player("Ton");
        territory.setOccupiedBy(player);
        assertTrue(territory.isOccupied());
        assertEquals(territory.getOccupiedBy().getPlayerName(), "Ton");
    }

    @Test
    public void getNumbOfArmy() {
        assertEquals(territory.getNumbOfArmy(), 0);
    }

    @Test
    public void setNumbOfArmy() {
        territory.setNumbOfArmy(3);
        assertEquals(territory.getNumbOfArmy(), 3);
    }

    @Test
    public void getArmy() {
        territory.setArmy(3);
        assertEquals(territory.getNumbOfArmy(), 3);
        assertEquals(territory.getArmy().getArmyName(), "Calvary");
    }

    @Test
    public void setArmy() {
        territory.setArmy(3);
        assertEquals(territory.getNumbOfArmy(), 3);
        assertEquals(territory.getArmy().getArmyName(), "Calvary");
    }
}