package KevinTonRafael.company;

import java.util.Random;

public class Die {

    private int currentValue;
    private final int minValue;
    private final int maxValue;

    public Die(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int roll()
    {
        Random rand = new Random();
        this.currentValue = rand.nextInt(maxValue) + minValue;
        return currentValue;
    }

}
