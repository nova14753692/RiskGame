package KevinTonRafael.company;

import java.util.Scanner;

public class Attack extends Battle {

    private int armyPenalty;
    private int armyPenaltyToDefender;

    public Attack(Player thisPlayer, Territory thisTerritory, Territory otherTerritory, int numbOfInvolvedArmy,
                  int numbOfSpareArmy, int armyPenalty, int armyPenaltyToDefender) {
        super(thisPlayer, thisTerritory, otherTerritory, numbOfInvolvedArmy, numbOfSpareArmy);
        this.armyPenalty = armyPenalty;
        this.armyPenaltyToDefender = armyPenaltyToDefender;
    }

    public boolean startBattle(int numbOfDie) {
        thisPlayer.getDice().forEach(die -> die.reset());
        if (numbOfMaxDie > 0 && numbOfMaxDie <= thisPlayer.getDice().size()) {
            for (int i = 0; i < numbOfMaxDie; i++) thisPlayer.getDice().get(i).roll();
            thisPlayer.printRolledDice();
            return true;
        }
        return false;
    }

    public void afterBattle(int result, Scanner userInput) {
        if (result > -2) {
            if (result <= 0) {
                thisTerritory.setNumbOfArmy(thisTerritory.getNumbOfArmy() - armyPenalty);
                if (thisTerritory.getNumbOfArmy() == 0) thisPlayer.getOwnedTerritories().remove(thisTerritory);
                if (thisPlayer.getOwnedTerritories().size() == 0) {
                    thisPlayer.setLost(true);
                    System.out.println(thisPlayer.getPlayerName() + " is lost.");
                }
            } else {
                System.out.println(thisPlayer.getPlayerName() + " won.");
                otherTerritory.setNumbOfArmy(otherTerritory.getNumbOfArmy() - armyPenaltyToDefender);
                if (otherTerritory.getNumbOfArmy() == 0) {
                    int numbOfWinArmy = -1;
                    while (numbOfWinArmy > numbOfInvolvedArmy || numbOfWinArmy < numbOfMaxDie) {
                        numbOfWinArmy = -1;
                        System.out.println("Enter number of army will move from " + thisTerritory.getTerritoryName() +
                                " to " + otherTerritory.getTerritoryName() + ": ");
                        if (userInput.hasNextInt()) numbOfWinArmy = userInput.nextInt();
                        userInput.nextLine();
                    }
                    otherTerritory.getOccupiedBy().getOwnedTerritories().remove(otherTerritory);
                    thisPlayer.addOwnedTerritory(otherTerritory);
                    otherTerritory.setNumbOfArmy(numbOfWinArmy);
                    Card card = new TerritoryCard();
                    thisPlayer.getOwnedCards().add(card);
                }
            }
        }
    }

    public int getArmyPenalty() {
        return armyPenalty;
    }

    public void setArmyPenalty(int armyPenalty) {
        this.armyPenalty = armyPenalty;
    }

    public int getArmyPenaltyToDefender() {
        return armyPenaltyToDefender;
    }

    public void setArmyPenaltyToDefender(int armyPenaltyToDefender) {
        this.armyPenaltyToDefender = armyPenaltyToDefender;
    }
}