package KevinTonRafael.company;
import org.jetbrains.annotations.NotNull;

import java.lang.String;
import java.util.List;
import java.util.ArrayList;

public class Player {

    private String playerName;
    private int numOfArmy;
    private List<Territory> ownedTerritories;
    private boolean isLost;
    private int currentRollValue;
    private int numOfDice;
    private List<Die> dice;

    public Player(@NotNull String playerName) {
        this.playerName = playerName;
        this.numOfArmy = 0;
        this.ownedTerritories = new ArrayList<>();
        this.isLost = false;
        this.currentRollValue = 0;
        this.numOfDice = 1;
        this.dice = new ArrayList<>() {
            {
                add(new Die(1, 6));
            }
        };
    }

    public Player(@NotNull String playerName, int numbOfDie) {
        this.playerName = playerName;
        this.numOfArmy = 0;
        this.ownedTerritories = new ArrayList<>();
        this.isLost = false;
        this.currentRollValue = 0;
        this.numOfDice = 1;
        this.dice = new ArrayList<Die>();
        for (int i = 0; i < numbOfDie; i++) {
            this.dice.add((new Die(1, 6)));
        }
    }

    public Player(@NotNull String playerName, int numbOfDie, int minRollValue, int maxRollValue) {
        this.playerName = playerName;
        this.numOfArmy = 0;
        this.ownedTerritories = new ArrayList<>();
        this.isLost = false;
        this.currentRollValue = 0;
        this.numOfDice = 1;
        this.dice = new ArrayList<Die>();
        for (int i = 0; i < numbOfDie; i++) {
            this.dice.add((new Die(minRollValue, maxRollValue)));
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getNumOfArmy() {
        return numOfArmy;
    }

    public void setNumOfArmy(int numOfArmy) {
        this.numOfArmy = numOfArmy;
    }

    public List<Territory> getOwnedTerritories() {
        return ownedTerritories;
    }

    public void addOwnedTerritory(Territory territory) {
        if (ownedTerritories.contains(territory)) territory.setNumbOfArmy(territory.getNumbOfArmy() + 1);
        else {
            territory.setNumbOfArmy(1);
            territory.setOccupied(true);
            territory.setOccupiedBy(this);
            ownedTerritories.add(territory);
        }
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }

    public int getCurrentRollValue() {
        return currentRollValue;
    }

    public void setCurrentRollValue() {
        int total = 0;
        for (Die theDie : dice) { total += theDie.roll(); }
        this.currentRollValue = total;
    }

    public int getNumOfDice() {
        return numOfDice;
    }

    public void setNumOfDice(int numOfDice) {
        this.numOfDice = numOfDice;
    }

    public List<Die> getDice() {
        return dice;
    }

}
