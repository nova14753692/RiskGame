package KevinTonRafael.company;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Scanner;

public abstract class Battle {

    protected Player thisPlayer;
    protected Territory thisTerritory;
    protected Territory otherTerritory;
    protected int numbOfInvolvedArmy;
    protected int numbOfDie;

    protected Battle(@NotNull Player thisPlayer, @NotNull Territory thisTerritory, @NotNull Territory otherTerritory,
                     int numbOfInvolvedArmy, int numbOfSpareArmy) {
        this.thisPlayer = thisPlayer;
        this.thisTerritory = thisTerritory;
        this.otherTerritory = otherTerritory;
        this.numbOfInvolvedArmy = numbOfInvolvedArmy;
        if (this.numbOfInvolvedArmy < numbOfSpareArmy || this.numbOfInvolvedArmy > this.thisTerritory.getNumbOfArmy() - numbOfSpareArmy)
            numbOfDie = 0;
        else {
            numbOfDie = this.numbOfInvolvedArmy;
            if (numbOfDie > thisPlayer.getDice().size()) numbOfDie = thisPlayer.getDice().size();
        }
    }

    public abstract boolean startBattle();

    public abstract void afterBattle(int result, Scanner userInput);

    public static final int getBattleResult(@NotNull Battle attacker, @NotNull Battle defender) {
        if (attacker != null && defender != null && attacker.numbOfDie > 0 && defender.numbOfDie > 0) {
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
}