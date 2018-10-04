package RiskGame;

public class Calvary extends Army {

    public Calvary(int numbOfArmy) {
        super(numbOfArmy);
        armyName = "RiskGame.Calvary";
        lowerBound = 3;
        upperBound = 5;
        nextType = "";
        previousType = "RiskGame.FootSoldierArmy";
    }
}
