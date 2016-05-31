package com.wbc.bsa.le;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by willi on 31/05/2016.
 */
public class CostCalculator {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

    public CostCalculator() {
        DECIMAL_FORMAT.setRoundingMode(RoundingMode.UP);
    }

    public double costOf(double cost) {
        return Double.parseDouble(DECIMAL_FORMAT.format(cost));
    }
}
