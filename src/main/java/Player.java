import java.lang.String;
import java.util.List;
import java.util.ArrayList;

/**
 * <p style="color:blue;">Player object that stores everything related to the player</p>
 */
public class Player {

    private String playerName; //Name of player
    private int numOfAvailableArmy; //Number of available army the player has
    private List<Territory> ownedTerritories; //List contains player's owned territories
    private boolean isLost; //Is this player lose
    private int numOfDice; //Number of die the player can at the same time in a roll
    private List<Die> dice; //List contains the number of die that player can roll at the same time
    private int minBonusArmies; //Minimum number of bonus armies the player will get each turn
    private List<Card> ownedCards; //The list of card the player owns
    private Player lastPlayer;

    /**
     * <p style="color:blue;">Player constructor, create player</p>
     * <p>Assign only 1 die for the player to roll at the same time</p>
     * @param playerName The name which the player will be created with
     */
    public Player( String playerName) {
        this.playerName = playerName;
        this.numOfAvailableArmy = 0;
        this.ownedTerritories = new ArrayList<Territory>();
        this.ownedCards = new ArrayList<Card>();
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
     * <p style="color:blue;">Player constructor, create player</p>
     * <p>Assign numbOfMaxDie dice for the player to roll at the same time</p>
     * @param playerName The name which the player will be created with
     * @param numbOfDie The number of die the player can roll at the same time in a roll
     */
    public Player(String playerName, int numbOfDie) {
        this.playerName = playerName;
        this.numOfAvailableArmy = 0;
        this.ownedTerritories = new ArrayList<Territory>();
        this.ownedCards = new ArrayList<Card>();
        this.isLost = false;
        this.numOfDice = 1;
        this.dice = new ArrayList<Die>();
        for (int i = 0; i < numbOfDie; i++) {
            this.dice.add((new Die(1, 6)));
        }
    }

    /**
     * <p style="color:blue;">Player constructor, create player</p>
     * <p>Assign numbOfMaxDie dice for the player to roll at the same time</p>
     * <p>The smallest face value and the largest face value can be determine with
     * <b>minRollValue</b> and <b>maxRollValue</b> respectively</p>
     * @param playerName The name which the player will be created with
     * @param numbOfDie The number of die the player can roll at the same time in a roll
     * @param minRollValue The smallest face value
     * @param maxRollValue The largest face value
     */
    public Player( String playerName, int numbOfDie, int minRollValue, int maxRollValue) {
        this.playerName = playerName;
        this.numOfAvailableArmy = 0;
        this.ownedTerritories = new ArrayList<Territory>();
        this.isLost = false;
        this.numOfDice = 1;
        this.dice = new ArrayList<Die>();
        this.ownedCards = new ArrayList<Card>();
        for (int i = 0; i < numbOfDie; i++) {
            this.dice.add((new Die(minRollValue, maxRollValue)));
        }
    }

    /**
     * <p style="color:blue;">Get player's name</p>
     * @return Player's name
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

    public void printRolledDice() {
        System.out.println(getPlayerName() + "'s roll result:");
        System.out.print("====> ");
        dice.forEach(die -> {
            if (die.getCurrentValue() > 0) {
                if (dice.indexOf(die) > 0 && dice.indexOf(die) < dice.size() - 1) ;
                System.out.print(die.getCurrentValue());
                System.out.print("  ");
            }
        });
        System.out.println("\n====================================================================");
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

    /**
     * <p style="color:blue;">Set the number of dice that player can roll at the same time in a roll</p>
     * @param numOfDice The new number of dice player will now have
     */
    public void setNumOfDice(int numOfDice) {
        //lastPlayer.setNumOfDice(this.numOfDice);
        this.numOfDice = numOfDice;
    }

    /**
     * <p style="color:blue;">Get the list of all the dice the player has</p>
     * @return The list of all the dice the player has
     */
    public List<Die> getDice() {
        return dice;
    }

    public List<Card> getOwnedCards() {
        return ownedCards;
    }

    public void Revoke() {
        numOfAvailableArmy = lastPlayer.getNumOfAvailableArmy();
        ownedTerritories.clear();
        ownedTerritories.addAll(lastPlayer.getOwnedTerritories());
        playerName = lastPlayer.getPlayerName();
        isLost = lastPlayer.isLost();
        numOfDice = lastPlayer.getNumOfDice();
        dice.clear();
        dice.addAll(lastPlayer.getDice());

        //ownedTerritories.stream().forEach(item -> item.setOccupiedBy(this));
    }

    public void saveState() {
        lastPlayer.setNumOfDice(this.numOfDice);
        lastPlayer.setLost(this.isLost);
        lastPlayer.setNumOfAvailableArmy(this.numOfAvailableArmy);
        lastPlayer.getOwnedTerritories().clear();
        lastPlayer.getOwnedTerritories().addAll(this.getOwnedTerritories());
        lastPlayer.getDice().clear();
        lastPlayer.getDice().addAll(this.getDice());
        lastPlayer.ownedCards.clear();
        lastPlayer.getOwnedCards().addAll(this.getOwnedCards());
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(Player lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

}