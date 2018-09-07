package KevinTonRafael.company;

import java.util.Random;

public class Die {

    private int currentValue; //Current face value of this die
    private final int minValue; //Smallest face value of this die
    private final int maxValue; //Largest face value of this die

    /**
     * <p style="color:blue;">Die constructor, create a die</p>
     * @param minValue The smallest face value of this die
     * @param maxValue The largest face value of this die
     */
    public Die(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * <p style="color:blue;">Roll this die, and store the result face value in <b>currentValue</b> variable</p>
     * @return The result face value of this die after roll
     */
    public int roll()
    {
        Random rand = new Random();
        this.currentValue = rand.nextInt(maxValue) + minValue;
        return currentValue;
    }

}
