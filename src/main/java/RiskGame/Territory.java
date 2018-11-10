package RiskGame;

import java.lang.String;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Territory {

    private String territoryName; //The territory name
    private int territoryIndex; //The territory index (base on order of TerritoryDataList)
    private boolean isOccupied; //Is the territory owned by any player?
    private Player occupiedBy; //Whom this territory is owned by?
    private int numbOfArmy; //Number of army currently are on this territory
    private Army army;  //The army on this territory

    private Territory lastTerritory;

    private List<Territory> adjTerritories; //The list contains all adjacent territories with this territory

    /**
     * <p style="color:blue;">.Territory constructor, create territory</p>
     * @param territoryName The name of this territory
     * @param territoryIndex The index of this territory (will be assigned base on its order in TerritoryDataList)
     */
    public Territory(String territoryName, int territoryIndex) {
        this.territoryName = territoryName;
        this.isOccupied = false;
        this.occupiedBy = null;
        this.numbOfArmy = 0;
        this.territoryIndex = territoryIndex;
        adjTerritories = new ArrayList<Territory>();
        this.army = null;
    }

    /**
     * <p style="color:blue;">Get territory's name</p>
     * @return .Territory's name
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * <p style="color:blue;">Get territory's index</p>
     * @return .Territory's index
     */
    public int getTerritoryIndex() {
        return territoryIndex;
    }

    /**
     * <p style="color:blue;">Get the list of all adjacent territories with this territory</p>
     * @return The list of all adjacent territories with this territory
     */
    public List<Territory> getAdjTerritories() {
        return adjTerritories;
    }

    /**
     * <p style="color:blue;">Whether this territory is owned by any player</p>
     * @return <b>True</b> when this territory is owned by any player,
     * <b>False</b> when this territory is <b>not</b> owned by any player
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * <p style="color:blue;">Change the state of this territory, whether it's owned by any player or free</p>
     * @param occupied The state this territory will have
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * <p style="color:blue;">Get which player owns this territory</p>
     * @return The player who owns this territory
     */
    public Player getOccupiedBy() {
        return occupiedBy;
    }

    /**
     * <p style="color:blue;">Set which player will own this territory</p>
     * @param occupiedBy The player which will be the new owner of this territory
     */
    public void setOccupiedBy(Player occupiedBy) {
        this.occupiedBy = occupiedBy;
        this.isOccupied = true;
    }

    /**
     * <p style="color:blue;">Get the number of army is on this territory</p>
     * @return The number of army that is on this territory
     */
    public int getNumbOfArmy() {
        return numbOfArmy;
    }

    /**
     * <p style="color:blue;">Change the number of army that is on this territory</p>
     * @param numbOfArmy The new number of army will be on this territory
     */
    public void setNumbOfArmy(int numbOfArmy) {
        this.numbOfArmy = numbOfArmy;
    }

    public Territory getLastTerritory() {
        return lastTerritory;
    }

    public void setLastTerritory(Territory lastTerritory) {
        this.lastTerritory = lastTerritory;
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(int numbOfArmy) {
        if (army == null) army = new FootSoldierArmy(numbOfArmy);

        if (army.getNumbOfArmy() > army.getUpperBound() && !army.getNextType().equals("")) {
            try {
                Class c = Class.forName(army.getNextType());
                army = (Army)c.getConstructor(Integer.TYPE).newInstance(numbOfArmy);
            }
            catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                    IllegalAccessException | InvocationTargetException e) {
                System.out.println("Error when convert army.");
            }
        }
        army.setNumbOfArmy(numbOfArmy);
        this.numbOfArmy = numbOfArmy;
    }

    /**
     * <p style="color:blue;">Print the list of all adjacent territories with this territory</p>
     */
    public void printAdjTerritories() {
        System.out.print(territoryName); //Print this territory name first

        //If the territory is owned by any player, print the name of that player
        if (occupiedBy != null) System.out.print(" - " + occupiedBy.getPlayerName());
        System.out.println(" has Adjacent territories: ");
        System.out.print("====> ");

        //Print every adjacent territories this territory has
        adjTerritories.forEach(t -> {
            System.out.print(t.getTerritoryName());
            if (t.getOccupiedBy() != null) System.out.print(" - " + t.getOccupiedBy().getPlayerName());
            System.out.print(" - index: " + t.getTerritoryIndex());
            System.out.print(" : " + t.getNumbOfArmy());
            if (adjTerritories.indexOf(t) < adjTerritories.size() - 1) System.out.print(", ");
        });
        System.out.println();
    }

    public void printAdjTerritories(TelegramBot bot) {
        bot.sendMessage(territoryName); //Print this territory name first

        //If the territory is owned by any player, print the name of that player
        if (occupiedBy != null) bot.sendMessage(" - " + occupiedBy.getPlayerName());
        bot.sendMessage(" has Adjacent territories: ");
        bot.sendMessage("====> ");

        //Print every adjacent territories this territory has
        adjTerritories.forEach(t -> {
            bot.sendMessage(t.getTerritoryName());
            if (t.getOccupiedBy() != null) bot.sendMessage(" - " + t.getOccupiedBy().getPlayerName());
            bot.sendMessage(" - index: " + t.getTerritoryIndex());
            bot.sendMessage(" : " + t.getNumbOfArmy());
            if (adjTerritories.indexOf(t) < adjTerritories.size() - 1) bot.sendMessage(", ");
        });
        bot.sendMessage("\n");
    }

    public void Revoke()
    {
        this.territoryName = lastTerritory.getTerritoryName();
        this.territoryIndex = lastTerritory.getTerritoryIndex();
        this.isOccupied = lastTerritory.isOccupied();
        this.occupiedBy = lastTerritory.getOccupiedBy();
        this.numbOfArmy = lastTerritory.getNumbOfArmy();
        this.army = lastTerritory.getArmy();
    }

    public void saveState() {
        lastTerritory.setOccupiedBy(this.getOccupiedBy());
        lastTerritory.setOccupied(this.isOccupied());
        lastTerritory.setNumbOfArmy(this.getNumbOfArmy());
        lastTerritory.setArmy(this.getArmy().numbOfArmy);
    }
}