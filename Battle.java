package KevinTonRafael.company;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Scanner;

public abstract class Battle {

    protected Player thisPlayer;
    protected Territory thisTerritory;
    protected Territory otherTerritory;
    protected int numbOfInvolvedArmy;
    protected int numbOfMaxDie;

    protected Battle(@NotNull Player thisPlayer, @NotNull Territory thisTerritory, @NotNull Territory otherTerritory,
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

    public abstract void afterBattle(int result, Scanner userInput);

    public static final int getBattleResult(@NotNull Battle attacker, @NotNull Battle defender) {
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

    public int askUserNumberOfDice(Scanner userInput) {
        int numbOfDie = 0;
        while (numbOfDie <= 0 || numbOfDie > this.getNumbOfMaxDie()) {
            System.out.println("Enter the number of dice you want to roll (Max = " + this.getNumbOfMaxDie() + "): ");
            if (userInput.hasNextLine()) {
                try {
                    numbOfDie = Integer.parseInt(userInput.nextLine());
                }
                catch (NumberFormatException e) {
                    System.out.println("You need to enter a number.");
                }
                if (numbOfDie <= 0 || numbOfDie > this.getNumbOfMaxDie())
                    System.out.println("The number of dice must be > 0 and <= " + this.getNumbOfMaxDie() + ".");
            }
        }
        return numbOfDie;
    }

    public int getNumbOfMaxDie() {
        return numbOfMaxDie;
    }
}