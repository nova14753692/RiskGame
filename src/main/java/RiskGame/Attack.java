package RiskGame;

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

    public boolean startBattle(int numbOfDie, TelegramBot bot) {
        thisPlayer.getDice().forEach(die -> die.reset());
        if (numbOfMaxDie > 0 && numbOfMaxDie <= thisPlayer.getDice().size()) {
            for (int i = 0; i < numbOfMaxDie; i++) thisPlayer.getDice().get(i).roll();
            thisPlayer.printRolledDice(bot);
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
                }
            }
        }
    }

    public void afterBattle(int result, TelegramBot bot) {
        if (result > -2) {
            if (result <= 0) {
                thisTerritory.setNumbOfArmy(thisTerritory.getNumbOfArmy() - armyPenalty);
                if (thisTerritory.getNumbOfArmy() == 0) thisPlayer.getOwnedTerritories().remove(thisTerritory);
                if (thisPlayer.getOwnedTerritories().size() == 0) {
                    thisPlayer.setLost(true);
                    bot.sendMessage(thisPlayer.getPlayerName() + " is lost.");
                }
            } else {
                bot.sendMessage(thisPlayer.getPlayerName() + " won.");
                otherTerritory.setNumbOfArmy(otherTerritory.getNumbOfArmy() - armyPenaltyToDefender);
                if (otherTerritory.getNumbOfArmy() == 0) {
                    int numbOfWinArmy = -1;
                    while (numbOfWinArmy > numbOfInvolvedArmy || numbOfWinArmy < numbOfMaxDie) {
                        numbOfWinArmy = -1;
                        bot.sendMessage("Enter number of army will move from " + thisTerritory.getTerritoryName() +
                                " to " + otherTerritory.getTerritoryName() + ": ");

                        while (bot.getMessage() == null) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {

                            }
                        }
                        if (bot.getMessage() != null) numbOfWinArmy = Integer.parseInt(bot.getMessage());
                    }
                    otherTerritory.getOccupiedBy().getOwnedTerritories().remove(otherTerritory);
                    thisPlayer.addOwnedTerritory(otherTerritory);
                    otherTerritory.setNumbOfArmy(numbOfWinArmy);
                }
            }
        }
    }

    //For test
    public void afterBattle(int result, int numbOfWinArmy) {
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
                    otherTerritory.getOccupiedBy().getOwnedTerritories().remove(otherTerritory);
                    thisPlayer.addOwnedTerritory(otherTerritory);
                    otherTerritory.setNumbOfArmy(numbOfWinArmy);
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