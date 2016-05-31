package com.wbc.bsa.le;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by willi on 31/05/2016.
 */
public class CostCalculatorTest {

    CostCalculator costCalculator;

    @Before
    public void setUp() {
        costCalculator = new CostCalculator();
    }

    @Test
    public void atTheEndOfCalculationRoundsToTheNearestPenny() {
        assertThat(costCalculator.costOf(4.988)).isEqualTo(4.99);
    }

    @Test
    public void atTheEndOfCalculationRoundsUpToTheNearestPenny() {
        assertThat(costCalculator.costOf(4.922)).isEqualTo(4.93);
    }

    @Test
    public void calculatesCostBasedOnWeightAndCostPerWeight() {
        assertThat(costCalculator.costOf(3.05, 4.99)).isEqualTo(15.22);
    }

}