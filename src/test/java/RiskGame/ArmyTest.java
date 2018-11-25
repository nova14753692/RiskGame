package RiskGame;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArmyTest {

    Army army1 = new Calvary(3);
    Army army2 = new FootSoldierArmy(1);

    @Test
    public void getNumbOfArmy() {
        assertEquals(army1.getNumbOfArmy(), 3);
        assertEquals(army2.getNumbOfArmy(), 1);
    }

    @Test
    public void setNumbOfArmy() {
        army1.setNumbOfArmy(2);
        assertEquals(army1.getNumbOfArmy(), 2);

        army2.setNumbOfArmy(2);
        assertEquals(army2.getNumbOfArmy(), 2);
    }

    @Test
    public void getArmyName() {
        assertEquals(army1.getArmyName(), "Calvary");
        assertEquals(army2.getArmyName(), "Foot Soldier");
    }

    @Test
    public void setArmyName() {
        army1.setArmyName("Calvary");
        assertEquals(army1.getArmyName(), "Calvary");

        army2.setArmyName("FootSoldierArmy");
        assertEquals(army2.getArmyName(), "FootSoldierArmy");
    }

    @Test
    public void getLowerBound() {
        assertEquals(army1.getLowerBound(), 3);
        assertEquals(army2.getLowerBound(), 1);
    }

    @Test
    public void setLowerBound() {
        army1.setLowerBound(3);
        assertEquals(army1.getLowerBound(), 3);

        army2.setLowerBound(1);
        assertEquals(army2.getLowerBound(), 1);
    }

    @Test
    public void getUpperBound() {
        assertEquals(army1.getUpperBound(), 5);
        assertEquals(army2.getUpperBound(), 2);
    }

    @Test
    public void setUpperBound() {
        army1.setUpperBound(5);
        assertEquals(army1.getUpperBound(), 5);

        army2.setUpperBound(2);
        assertEquals(army2.getUpperBound(), 2);
    }

    @Test
    public void getNextType() {
        assertEquals(army1.getNextType(), "");
        assertEquals(army2.getNextType(), Calvary.class.getName());
    }

    @Test
    public void getPreviousType() {
        assertEquals(army1.getPreviousType(), "FootSoldierArmy");
        assertEquals(army2.getPreviousType(), "");
    }

    @Test
    public void setNextType() {
        army1.setNextType("None");
        assertEquals(army1.getNextType(), "None");

        army2.setNextType("None");
        assertEquals(army2.getNextType(), "None");
    }

    @Test
    public void setPreviousType() {
        army1.setPreviousType("FootSoldierArmy");
        assertEquals(army1.getPreviousType(), "FootSoldierArmy");

        army2.setPreviousType("None");
        assertEquals(army2.getPreviousType(), "None");
    }
}