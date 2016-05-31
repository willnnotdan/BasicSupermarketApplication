package com.wbc.bsa.le;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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

    public double costOf(double massInKg, double costPerKg) {
        return costOf(massInKg * costPerKg);
    }

    public double costOf(DealEnums deal, HashMap values) {
        try {
            switch (deal) {
                case XFORCOST:
                    return xForCost(values);
                case BOGOF:
                    return 0;
                case XFORY:
                    return 0;
                default:
                    throw new IllegalArgumentException("Invalid Deal.");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private double xForCost(HashMap values) {
        int x = (Integer) values.get("X");
        int iterator = 1;
        double total = 0;
        double runningTotal = 0;
        double dealPrice = (Double) values.get("dealPrice");
        for (double value : (sortArrayListSmallestFirst((ArrayList<Double>) values.get("totals")))
                ) {
            runningTotal += value;
            if (iterator == x) {
                iterator = 0;
                runningTotal = 0;
                total += dealPrice;
            }
            iterator++;
        }
        return runningTotal + total;
    }

    public ArrayList<Double> sortArrayListGreatestFirst(ArrayList<Double> list) {
        Collections.sort(list, new Comparator<Double>(){
            public int compare(Double a1, Double a2) {
                Double a11 = a1 * 100;
                Double a21 = a2 * 100;
                return a21.intValue() - a11.intValue();
            }
        });
        return list;
    }

    public ArrayList<Double> sortArrayListSmallestFirst(ArrayList<Double> list) {
        Collections.sort(list, new Comparator<Double>(){
            public int compare(Double a1, Double a2) {
                Double a11 = a1 * 100;
                Double a21 = a2 * 100;
                return a11.intValue() - a21.intValue();
            }
        });
        return list;
    }
}
