package KevinTonRafael.company;

import java.util.Scanner;

public class Defend extends Battle {

    public Defend(Player thisPlayer, Territory thisTerritory, Territory otherTerritory, int numbOfInvolvedArmy,
                  int numbOfSpareArmy) {
        super(thisPlayer, thisTerritory, otherTerritory, numbOfInvolvedArmy, numbOfSpareArmy);
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
            if (result > 0) {
                if (thisPlayer.getOwnedTerritories().size() == 0) {
                    thisPlayer.setLost(true);
                    System.out.println(thisPlayer.getPlayerName() + " is lost.");
                }
            }
            else {
                System.out.println(thisPlayer.getPlayerName() + " successfully defended " + thisTerritory.getTerritoryName() + ".");
            }
        }
    }
}