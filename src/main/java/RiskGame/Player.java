package RiskGame;

import java.io.PrintWriter;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

/**
 * <p style="color:blue;">.Player object that stores everything related to the player</p>
 */
public class Player {

    private String playerName; //Name of player
    private int numOfAvailableArmy; //Number of available army the player has
    private List<Territory> ownedTerritories; //List contains player's owned territories
    private boolean isLost; //Is this player lose
    private int numOfDice; //Number of die the player can at the same time in a roll
    private List<Die> dice; //List contains the number of die that player can roll at the same time
    private int minBonusArmies; //Minimum number of bonus armies the player will get each turn

    /**
     * <p style="color:blue;">.Player constructor, create player</p>
     * <p>Assign only 1 die for the player to roll at the same time</p>
     * @param playerName The name which the player will be created with
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.numOfAvailableArmy = 0;
        this.ownedTerritories = new ArrayList<Territory>();
        this.isLost = false;
        this.numOfDice = 1;
        this.minBonusArmies = 3;
        this.dice = new ArrayList<Die>() {
            {
                add(new Die(1, 6));
            }
        };
    }

    /**
     * <p style="color:blue;">.Player constructor, create player</p>
     * <p>Assign numbOfMaxDie dice for the player to roll at the same time</p>
     * @param playerName The name which the player will be created with
     * @param numbOfDie The number of die the player can roll at the same time in a roll
     */
    public Player(String playerName, int numbOfDie) {
        this.playerName = playerName;
        this.numOfAvailableArmy = 0;
        this.ownedTerritories = new ArrayList<Territory>();
        this.isLost = false;
        this.numOfDice = 1;
        this.minBonusArmies = 3;
        this.dice = new ArrayList<Die>();
        for (int i = 0; i < numbOfDie; i++) {
            this.dice.add((new Die(1, 6)));
        }
    }

    /**
     * <p style="color:blue;">.Player constructor, create player</p>
     * <p>Assign numbOfMaxDie dice for the player to roll at the same time</p>
     * <p>The smallest face value and the largest face value can be determine with
     * <b>minRollValue</b> and <b>maxRollValue</b> respectively</p>
     * @param playerName The name which the player will be created with
     * @param numbOfDie The number of die the player can roll at the same time in a roll
     * @param minRollValue The smallest face value
     * @param maxRollValue The largest face value
     */
    public Player(String playerName, int numbOfDie, int minRollValue, int maxRollValue) {
        this.playerName = playerName;
        this.numOfAvailableArmy = 0;
        this.ownedTerritories = new ArrayList<Territory>();
        this.isLost = false;
        this.numOfDice = 1;
        this.minBonusArmies = 3;
        this.dice = new ArrayList<Die>();
        for (int i = 0; i < numbOfDie; i++) {
            this.dice.add((new Die(minRollValue, maxRollValue)));
        }
    }

    /**
     * <p style="color:blue;">Get player's name</p>
     * @return .Player's name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * <p style="color:blue;">Get number of army the player has</p>
     * @return The number of army the player has
     */
    public int getNumOfAvailableArmy() {
        return numOfAvailableArmy;
    }

    /**
     * <p style="color:blue;">Change the number of army the player has</p>
     * @param numOfArmy The new number of army the player will have
     */
    public void setNumOfAvailableArmy(int numOfArmy) {
        //lastPlayer.setNumOfAvailableArmy(this.numOfAvailableArmy);
        this.numOfAvailableArmy = numOfArmy;
    }

    /**
     * <p style="color:blue;">Get the list of player's owned territories</p>
     * @return The list of player's owned territories
     */
    public List<Territory> getOwnedTerritories() {
        return ownedTerritories;
    }

    /**
     * <p style="color:blue;">Add the territory the player is going to claim</p>
     * @param territory The territory the player is going to claim
     */
    public void addOwnedTerritory(Territory territory) {
        if (ownedTerritories.contains(territory)) {
            //territory.setNumbOfArmy(territory.getNumbOfArmy() + 1);
            territory.setArmy(territory.getNumbOfArmy() + 1);
        }
        else {
            territory.setNumbOfArmy(1);
            territory.setOccupied(true);
            territory.setOccupiedBy(this);
            ownedTerritories.add(territory);
        }
        //lastPlayer.setNumOfAvailableArmy(this.numOfAvailableArmy);
        setNumOfAvailableArmy(getNumOfAvailableArmy() - 1);
    }

    public int getTotalNumbOfArmy() {
        int total = 0;
        for (Territory ownedTerritory : getOwnedTerritories()) total += ownedTerritory.getNumbOfArmy();
        return total;
    }

    public void setBonusArmies(int bonusArmies) {
        bonusArmies += getOwnedTerritories().size()/3;
        if (bonusArmies < minBonusArmies) bonusArmies = minBonusArmies;
        setNumOfAvailableArmy(getNumOfAvailableArmy() + bonusArmies);
    }

    public void printRolledDice(TelegramBot bot) {
        if (bot != null) {
            bot.sendMessage(getPlayerName() + "'s roll result:");
            bot.sendMessage("====> ");
        }
        dice.forEach(die -> {
            if (die.getCurrentValue() > 0) {
                if (bot != null) bot.sendMessage(String.valueOf(die.getCurrentValue()));
            }
        });
    }

    /**
     * <p style="color:blue;">Get whether or not the player is lost the game</p>
     * @return <b>True</b> when the player is lost, <b>False</b> when the player can still play
     */
    public boolean isLost() {
        return isLost;
    }

    /**
     * <p style="color:blue;">Change the win/lose state of the player</p>
     * @param lost If <b>True</b>, the player will lose. If <b>False</b> the player will continue to play
     */
    public void setLost(boolean lost) {
        //lastPlayer.setLost(this.isLost);
        isLost = lost;
    }

    /**
     * <p style="color:blue;">Get the number of dice that player can roll at the same time in a roll</p>
     * @return The number of dice that player can roll at the same time in a roll
     */
    public int getNumOfDice() {
        return numOfDice;
    }

    public void setNumOfDice(int numbOfDice) {
        dice.clear();
        for (int i = 0; i < numbOfDice; i++) {
            this.dice.add((new Die(1, 6)));
        }
        this.numOfDice = dice.size();
    }

    /**
     * <p style="color:blue;">Get the list of all the dice the player has</p>
     * @return The list of all the dice the player has
     */
    public List<Die> getDice() {
        return dice;
    }

}