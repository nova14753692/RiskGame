package RiskGame;

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
        if (numbOfDie > 0 && numbOfDie <= numbOfMaxDie && numbOfDie <= thisPlayer.getDice().size()) {
            for (int i = 0; i < numbOfDie; i++) thisPlayer.getDice().get(i).roll();
            //thisPlayer.printRolledDice(bot);
            return true;
        }
        return false;
    }

    public void afterBattle(int result, TelegramBot bot) {
        if (result >= -1) {
            if (result <= 0) {
                thisTerritory.setNumbOfArmy(thisTerritory.getNumbOfArmy() - armyPenalty);
                if (thisTerritory.getNumbOfArmy() == 0) thisPlayer.getOwnedTerritories().remove(thisTerritory);
                if (thisPlayer.getOwnedTerritories().size() == 0) {
                    thisPlayer.setLost(true);
                    if (bot != null) bot.sendMessage(thisPlayer.getPlayerName() + " is lost.");
                }
            } else {
                if (bot != null) bot.sendMessage(thisPlayer.getPlayerName() + " won.");
                otherTerritory.setNumbOfArmy(otherTerritory.getNumbOfArmy() - armyPenaltyToDefender);
                if (otherTerritory.getNumbOfArmy() == 0) {

                    int numbOfWinArmy = askForTroopWillMove(bot);
                    if (numbOfWinArmy > 0) {
                        otherTerritory.getOccupiedBy().getOwnedTerritories().remove(otherTerritory);
                        thisPlayer.addOwnedTerritory(otherTerritory);
                        otherTerritory.setNumbOfArmy(numbOfWinArmy);
                    }
                }
            }
        }
    }

    public int askForTroopWillMove(TelegramBot bot) {
        int numbOfWinArmy = -1;
        while (numbOfWinArmy >= numbOfInvolvedArmy || numbOfWinArmy < numbOfMaxDie) {
            if (bot != null) {
                bot.clearMessage();
                bot.sendMessage("Enter number of army will move from " + thisTerritory.getTerritoryName() +
                        " to " + otherTerritory.getTerritoryName() + ": ");
                if (bot.waitForInput() && bot.getMessage() != null)
                    numbOfWinArmy = Integer.parseInt(bot.getMessage());
                else {
                    otherTerritory.setNumbOfArmy(otherTerritory.getNumbOfArmy() + armyPenaltyToDefender);
                    break;
                }
            } else numbOfWinArmy = numbOfInvolvedArmy - 1;
        }
        return numbOfWinArmy;
    }

    public int getArmyPenalty() {
        return this.armyPenalty;
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