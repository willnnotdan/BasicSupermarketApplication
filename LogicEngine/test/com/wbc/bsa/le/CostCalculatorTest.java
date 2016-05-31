package com.wbc.bsa.le;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static com.wbc.bsa.le.DealEnums.BOGOF;
import static com.wbc.bsa.le.DealEnums.XFORCOST;
import static com.wbc.bsa.le.DealEnums.XFORY;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by willi on 31/05/2016.
 */
public class CostCalculatorTest {

    CostCalculator costCalculator;
    ArrayList<Double> totals;
    HashMap map;

    @Before
    public void setUp() {
        costCalculator = new CostCalculator();
        totals = new ArrayList<Double>();
        map = new HashMap();
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

    @Test
    public void ifAnItemIs3For£5TheCostIsCalculatedCorrectly() {
        map.put("dealPrice", 4.99);
        map.put("X", 3);
        totals.add(2.99);
        totals.add(2.99);
        totals.add(2.99);
        map.put("totals", totals);
        assertThat(costCalculator.costOf(XFORCOST, map)).isEqualTo(4.99);
    }

    @Test
    public void ifAnItemIs3For£5TheCostIsCalculatedCorrectlyForLargerQuantities() {
        map.put("dealPrice", 4.99);
        map.put("X", 3);
        totals.add(2.99);
        totals.add(2.99);
        totals.add(2.99);
        totals.add(1.99);
        map.put("totals", totals);
        assertThat(costCalculator.costOf(XFORCOST, map)).isEqualTo(7.98);
    }

    @Test
    public void ifAnItemIsBuyOneGetOneFreeCheapestItemIsFree() {
        totals.add(2.99);
        totals.add(1.99);
        map.put("totals", totals);
        assertThat(costCalculator.costOf(BOGOF, map)).isEqualTo(2.99);
    }

    @Test
    public void ifAnItemIsBuyOneGetOneFreeMultiplesAreAccountedFor() {
        totals.add(2.99);
        totals.add(1.99);
        totals.add(1.99);
        totals.add(1.99);
        map.put("totals", totals);
        assertThat(costCalculator.costOf(BOGOF, map)).isEqualTo(4.98);
    }

    @Test
    public void ifAnItemIsBuyOneGetOneFreeMultiplesAreAccountedForAndThePriciestConfigurationIsAlwaysSelected() {
        totals.add(2.99);
        totals.add(2.99);
        totals.add(1.99);
        totals.add(1.99);
        totals.add(1.99);
        map.put("totals", totals);
        assertThat(costCalculator.costOf(BOGOF, map)).isEqualTo(7.97);
    }

    @Test
    public void ifAnItemIsBuy3For2CostIsCalculatedCorrectly() {
        map.put("X", 3);
        map.put("Y", 2);
        totals.add(2.99);
        totals.add(2.99);
        totals.add(1.99);
        map.put("totals", totals);
        assertThat(costCalculator.costOf(XFORY, map)).isEqualTo(5.98);
    }

    @Test
    public void ifAnItemIsBuy3For2CostIsCalculatedCorrectlyForVaryingValues() {
        map.put("X", 3);
        map.put("Y", 2);
        totals.add(2.99);
        totals.add(1.99);
        totals.add(1.99);
        map.put("totals", totals);
        assertThat(costCalculator.costOf(XFORY, map)).isEqualTo(4.98);
    }

    @Test
    public void ifAnItemIsBuy3For2CostIsCalculatedCorrectlyForVaryingQuantities() {
        map.put("X", 3);
        map.put("Y", 2);
        totals.add(2.99);
        totals.add(1.99);
        totals.add(1.99);
        totals.add(1.99);
        map.put("totals", totals);
        assertThat(costCalculator.costOf(XFORY, map)).isEqualTo(6.97);
    }

    @Test
    public void canSortAnArrayListBySize() {
        totals.add(1.99);
        totals.add(2.99);
        totals.add(1.99);
        ArrayList<Double> comparison = new ArrayList<Double>();
        comparison.add(2.99);
        comparison.add(1.99);
        comparison.add(1.99);
        assertThat(costCalculator.sortArrayListGreatestFirst(totals)).isEqualTo(comparison);
    }

    @Test
    public void canSortAnArrayListBySizeToThePenny() {
        totals.add(1.98);
        totals.add(2.99);
        totals.add(1.99);
        ArrayList<Double> comparison = new ArrayList<Double>();
        comparison.add(2.99);
        comparison.add(1.99);
        comparison.add(1.98);
        assertThat(costCalculator.sortArrayListGreatestFirst(totals)).isEqualTo(comparison);
    }

    @Test
    public void canSortAnArrayListByReverseSizeToThePenny() {
        totals.add(1.98);
        totals.add(2.99);
        totals.add(1.99);
        ArrayList<Double> comparison = new ArrayList<Double>();
        comparison.add(1.98);
        comparison.add(1.99);
        comparison.add(2.99);
        assertThat(costCalculator.sortArrayListSmallestFirst(totals)).isEqualTo(comparison);
    }

    @Test (expected = IllegalArgumentException.class)
    public void doesntAllowInvalidDeals() {
        costCalculator.costOf(null, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void doesntAllowInvalidMapForDeal() {
        costCalculator.costOf(XFORY, map);
    }

    @Test (expected = IllegalArgumentException.class)
    public void doesntAllowInvalidMapForTotals() {
        map.put("X", 3);
        map.put("Y", 2);
        costCalculator.costOf(XFORY, map);
    }

    @Test
    public void handlesEmptyResults() {
        map.put("X", 3);
        map.put("Y", 2);
        map.put("totals", totals);
        costCalculator.costOf(XFORY, map);
    }
}