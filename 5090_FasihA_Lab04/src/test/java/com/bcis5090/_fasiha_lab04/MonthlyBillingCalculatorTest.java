package com.bcis5090._fasiha_lab04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonthlyBillingCalculatorTest {
    
    private static final double PACKAGE_A_COST = 9.95;
    private static final double PACKAGE_B_COST = 13.95;
    private static final double PACKAGE_C_COST = 19.95;
    
    private static final double PACKAGE_A_ADDITIONAL_COST_PER_HOUR = 2.00;
    private static final double PACKAGE_B_ADDITIONAL_COST_PER_HOUR = 1.00;
    
    @Test
    public void test_GivenPackageA_ExpectPackageACost() {
        var packageType = "A";
        double result = MonthlyBillingCalculator.determineBaseCharge(packageType);
        assertEquals(PACKAGE_A_COST, result);
    }
    
    @Test
    public void test_GivenPackageB_ExpectPackageACost() {
        var packageType = "B";
        double result = MonthlyBillingCalculator.determineBaseCharge(packageType);
        assertEquals(PACKAGE_B_COST, result);
    }
    
    @Test
    public void test_GivenPackageC_ExpectPackageACost() {
        var packageType = "C";
        double result = MonthlyBillingCalculator.determineBaseCharge(packageType);
        assertEquals(PACKAGE_C_COST, result);
    }
    
    @Test
    public void test_GivenPackageF_ExpectDefaultCost() {
        var packageType = "F";
        double result = MonthlyBillingCalculator.determineBaseCharge(packageType);
        assertEquals(0.00, result);
    }
    
    @Test
    public void test_GivenPackageA_AndSixtyHoursUsed_ExpectOneHundredDollarsInAdditionalCharges() {
        var packageType = "A";
        int hoursUsed = 60;
        double expected = (hoursUsed - 10) * PACKAGE_A_ADDITIONAL_COST_PER_HOUR;
        double result = MonthlyBillingCalculator.calculateAdditionalCharge(packageType, hoursUsed);
        assertEquals(expected, result);
    }
    
    @Test
    public void test_GivenPackageB_AndSixtyHoursUsed_ExpectFourtyDollarsInAdditionalCharges() {
        var packageType = "B";
        int hoursUsed = 60;
        double expected = (hoursUsed - 20) * PACKAGE_B_ADDITIONAL_COST_PER_HOUR;
        double result = MonthlyBillingCalculator.calculateAdditionalCharge(packageType, hoursUsed);
        assertEquals(expected, result);
    }

    @Test
    public void test_GivenThreeForFirstCharacter_ExpectDollarFiftySurcharge() {
        var zipCodeFirstCharacter = '3';
        double expResult = 1.50;
        double result = MonthlyBillingCalculator.determineZipCodeSurcharge(zipCodeFirstCharacter);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_GivenFiveForFirstCharacter_ExpectDollarSeventyFiveSurcharge() {
        var zipCodeFirstCharacter = '5';
        double expResult = 1.75;
        double result = MonthlyBillingCalculator.determineZipCodeSurcharge(zipCodeFirstCharacter);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_GivenEightForFirstCharacter_ExpectDollarNinetyFiveSurcharge() {
        var zipCodeFirstCharacter = '8';
        double expResult = 1.95;
        double result = MonthlyBillingCalculator.determineZipCodeSurcharge(zipCodeFirstCharacter);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_GivenTwoForFirstCharacter_ExpectNoSurcharge() {
        var zipCodeFirstCharacter = '2';
        double expResult = 0.00;
        double result = MonthlyBillingCalculator.determineZipCodeSurcharge(zipCodeFirstCharacter);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_GivenComancheCounty_ExpectFivePercentDiscount() {
        var countyName = "Comanche";
        double expResult = 0.05;
        double result = MonthlyBillingCalculator.determineCountyDiscountPercentage(countyName.toUpperCase());
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_GivenParkerCounty_ExpectTenPercentDiscount() {
        var countyName = "Parker";
        double expResult = 0.10;
        double result = MonthlyBillingCalculator.determineCountyDiscountPercentage(countyName.toUpperCase());
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_GivenErathCounty_ExpectTwentyPercentDiscount() {
        var countyName = "Erath";
        double expResult = 0.20;
        double result = MonthlyBillingCalculator.determineCountyDiscountPercentage(countyName.toUpperCase());
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_GivenRachelCounty_ExpectNoDiscount() {
        var countyName = "Rachel";
        double expResult = 0.00;
        double result = MonthlyBillingCalculator.determineCountyDiscountPercentage(countyName.toUpperCase());
        assertEquals(expResult, result);
    }

}