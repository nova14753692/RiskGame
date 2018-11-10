package RiskGame;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class Tests {

    @Test
    public void createPlayers() {
        Player ePlayer1 = new Player("Ton", 3);
        Player ePlayer2 = new Player("AI", 3);
        List<Player> ePlayers = new ArrayList<>();
        ePlayers.add(ePlayer1);
        ePlayers.add(ePlayer2);
        List<String> playerNames = new ArrayList<>();
        playerNames.add("Ton");
        playerNames.add("AI");
        List<Player> players = Main.createPlayers(2,8, 3,  2, playerNames);
        assertEquals(players.size(), ePlayers.size());
        if (players.size() == ePlayers.size()) {
            for (int i = 0; i < ePlayers.size(); i++) {
                assertEquals(ePlayers.get(i).getPlayerName(), players.get(i).getPlayerName());
                assertEquals(ePlayers.get(i).getPlayerName(), players.get(i).getPlayerName());
            }
        }
    }

    @Test
    public void startBattle() {
        Player ePlayer = new Player("Ton", 3);
        Territory eThisTerritory = new Territory("Alaska", 1);
        Territory eOtherTerritory = new Territory("Alberta", 2);
        eThisTerritory.setArmy(5);
        eOtherTerritory.setArmy(4);
        Attack eAttacker = new Attack(ePlayer, eThisTerritory, eOtherTerritory, 5, 1, 0, 0);
        assertEquals(eAttacker.startBattle(3), true);
    }

    @Test
    public void afterBattle() {
        Player ePlayer1 = new Player("Ton", 3);
        Player ePlayer2 = new Player("AI", 3);
        Territory eThisTerritory = new Territory("Alaska", 1);
        Territory eOtherTerritory = new Territory("Alberta", 2);
        eOtherTerritory.setOccupiedBy(ePlayer2);
        ePlayer2.getOwnedTerritories().add(eOtherTerritory);
        eThisTerritory.setArmy(2);
        eOtherTerritory.setArmy(1);
        Attack eAttacker = new Attack(ePlayer1, eThisTerritory, eOtherTerritory, 5, 1, 0, 1);
        eAttacker.afterBattle(1, 2);
        assertEquals(2, eOtherTerritory.getNumbOfArmy());
    }

    @Test
    public void setArmy() {
        Territory eTerritory = new Territory("Alaska", 1);
        eTerritory.setNumbOfArmy(2);
        Army army = eTerritory.getArmy();
        army = new FootSoldierArmy(2);
        Territory territory = new Territory("Alaska", 1);
        territory.setArmy(2);
        assertEquals(eTerritory.getNumbOfArmy(), territory.getNumbOfArmy());
        assertEquals(army.getNumbOfArmy(), territory.getArmy().getNumbOfArmy());
        assertEquals(army.getArmyName(), territory.getArmy().getArmyName());

        eTerritory = new Territory("Alaska", 1);
        eTerritory.setNumbOfArmy(3);
        army = eTerritory.getArmy();
        army = new Calvary(3);
        territory = new Territory("Alaska", 1);
        territory.setArmy(3);
        assertEquals(eTerritory.getNumbOfArmy(), territory.getNumbOfArmy());
        assertEquals(army.getNumbOfArmy(), territory.getArmy().getNumbOfArmy());
        assertEquals(army.getArmyName(), territory.getArmy().getArmyName());

        eTerritory = new Territory("Alaska", 1);
        eTerritory.setNumbOfArmy(5);
        army = eTerritory.getArmy();
        army = new Calvary(5);
        territory = new Territory("Alaska", 1);
        territory.setArmy(5);
        assertEquals(eTerritory.getNumbOfArmy(), territory.getNumbOfArmy());
        assertEquals(army.getNumbOfArmy(), territory.getArmy().getNumbOfArmy());
        assertEquals(army.getArmyName(), territory.getArmy().getArmyName());
    }
}