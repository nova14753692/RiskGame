package RiskGame;

public abstract class Army {

    protected int numbOfArmy;
    protected String armyName;
    protected int lowerBound;
    protected int upperBound;

    protected String nextType;

    protected String previousType;

    public Army(int numbOfArmy) {
        this.numbOfArmy = numbOfArmy;
    }

    public int getNumbOfArmy() {
        return numbOfArmy;
    }

    public void setNumbOfArmy(int numbOfArmy) {
        this.numbOfArmy = numbOfArmy;
    }

    public String getArmyName() {
        return armyName;
    }

    public void setArmyName(String armyName) {
        this.armyName = armyName;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public String getNextType() {
        return nextType;
    }

    public String getPreviousType() {
        return previousType;
    }

    public void setNextType(String nextType) {
        this.nextType = nextType;
    }

    public void setPreviousType(String previousType) {
        this.previousType = previousType;
    }
}
