import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TerritoryTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Starting Test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test Ended\n");
    }

    Territory territory = new Territory("Alaska", 1);
    //List<String> adjTer = Arrays.asList("Northwest", "Alberta", "Kamchatka");

    @Test
    public void getTerritoryName() {
        System.out.println("getTerritoryName test in progress...");
        String result = territory.getTerritoryName();
        assertEquals("Alaska", result);
    }

    @Test
    public void getTerritoryIndex() {
        System.out.println("getTerritoryIndex test in progress...");
        int result = territory.getTerritoryIndex();
        assertEquals(1, result);
    }

    @Test
    public void getAdjTerritories() {
        System.out.println("getAdjTerritories test in progress...");
        List<Territory> result = territory.getAdjTerritories();
        assertEquals(territory.getAdjTerritories(), result);
    }

    @Test
    public void isOccupied() {

    }

    @Test
    public void setOccupied() {
    }

    @Test
    public void getOccupiedBy() {
        System.out.println("getOccupiedBy test in progress...");
        Player result = territory.getOccupiedBy();
        assertEquals(territory.getOccupiedBy(), result);
    }

    @Test
    public void setOccupiedBy() {
    }

    @Test
    public void getNumbOfArmy() {
    }

    @Test
    public void setNumbOfArmy() {
    }

    @Test
    public void getArmy() {
    }

    @Test
    public void setArmy() {
    }

    @Test
    public void printAdjTerritories() {
    }
}