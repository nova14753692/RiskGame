package RiskGame;

import java.util.Comparator;
import java.util.Scanner;

public abstract class Battle {

    protected Player thisPlayer;
    protected Territory thisTerritory;
    protected Territory otherTerritory;
    protected int numbOfInvolvedArmy;
    protected int numbOfMaxDie;

    protected Battle( Player thisPlayer,  Territory thisTerritory,  Territory otherTerritory,
                     int numbOfInvolvedArmy, int numbOfSpareArmy) {
        this.thisPlayer = thisPlayer;
        this.thisTerritory = thisTerritory;
        this.otherTerritory = otherTerritory;
        this.numbOfInvolvedArmy = numbOfInvolvedArmy;
        if (this.numbOfInvolvedArmy <= numbOfSpareArmy)
            numbOfMaxDie = 0;
        else {
            numbOfMaxDie = this.numbOfInvolvedArmy - numbOfSpareArmy;
            if (numbOfMaxDie > thisPlayer.getDice().size()) numbOfMaxDie = thisPlayer.getDice().size();
        }
    }

    public abstract boolean startBattle(int numbOfDie);

    public abstract void afterBattle(int result, TelegramBot bot);

    public static int getBattleResult(Battle attacker,  Battle defender) {
        if (attacker != null && defender != null && attacker.numbOfMaxDie > 0 && defender.numbOfMaxDie > 0) {
            Die attackerMaxFaceValueDie = attacker.thisPlayer.getDice().stream()
                    .max(Comparator.comparing(die -> die.getCurrentValue())).get();
            Die defenderMaxFaceValueDie = defender.thisPlayer.getDice().stream()
                    .max(Comparator.comparing(die -> die.getCurrentValue())).get();
            if (attackerMaxFaceValueDie.getCurrentValue() > defenderMaxFaceValueDie.getCurrentValue())
                return 1;
            else if (attackerMaxFaceValueDie.getCurrentValue() < defenderMaxFaceValueDie.getCurrentValue())
                return -1;
            else return 0;
        }
        return  -2;
    }

    public int askUserNumberOfDice(TelegramBot bot) {
        int numbOfDie = 0;
        bot.clearMessage();
        while (numbOfDie <= 0 || numbOfDie > this.getNumbOfMaxDie()) {
            bot.sendMessage("Enter the number of dice you want to roll (Max = " + this.getNumbOfMaxDie() + "): ");
            if (bot.waitForInput() && bot.getMessage() != null) {
                try {
                    numbOfDie = Integer.parseInt(bot.getMessage());
                }
                catch (NumberFormatException e) {
                    bot.sendMessage("You need to enter a number.");
                }
                if (numbOfDie <= 0 || numbOfDie > this.getNumbOfMaxDie())
                    bot.sendMessage("The number of dice must be > 0 and <= " + this.getNumbOfMaxDie() + ".");
            }
        }
        return numbOfDie;
    }

    public int getNumbOfMaxDie() {
        return numbOfMaxDie;
    }
}