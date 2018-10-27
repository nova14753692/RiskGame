import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TerritoryTest {

    Territory territory = new Territory("Alaska", 1);
    //List<String> adjTer = Arrays.asList("Northwest", "Alberta", "Kamchatka");

    @BeforeEach
    void setUp() {
        System.out.println("Starting Test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test Ended\n");
    }

    @Test
    void getTerritoryName() {
        System.out.println("getTerritoryName test in progress...");
        String result = territory.getTerritoryName();
        assertEquals("Alaska", result);
    }

    @Test
    void getTerritoryIndex() {
        System.out.println("getTerritoryIndex test in progress...");
        int result = territory.getTerritoryIndex();
        assertEquals(1, result);
    }

    @Test
    void getAdjTerritories() {
        System.out.println("getAdjTerritories test in progress...");
        List<Territory> result = territory.getAdjTerritories();
        assertEquals(territory.getAdjTerritories(), result);
    }

    @Test
    void getOccupiedBy() {
        System.out.println("getOccupiedBy test in progress...");
        Player result = territory.getOccupiedBy();
        assertEquals(territory.getOccupiedBy(), result);
    }
}