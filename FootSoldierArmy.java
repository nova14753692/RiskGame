package KevinTonRafael.company;

public class FootSoldierArmy extends Army {

    public FootSoldierArmy(int numbOfArmy) {
        super(numbOfArmy);
        armyName = "Foot Soldier";
        lowerBound = 1;
        upperBound = 2;
        nextType = Calvary.class.getName();
    }
}
