/*
Asif Fasih
001040771
August 03, 2022
July 26, 2022
This program calculates a customer's monthly bill based on usage, location (zip code, county), and package
*/
package com.bcis5090._fasiha_lab05;

import java.util.Scanner;

public class MonthlyBillCalculator {

    //Accumulator variables 
    private static int totalCustomers = 0;
    private static double totalDiscount = 0.00;
    private static double totalCharge = 0.00;

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Enter hours used: ");
        var hoursUsed = scanner.nextInt();

        while (hoursUsed != -1) {
            //Validate that hours used is greater than zero hours. 
            while (hoursUsed <= 0) {
                System.out.println("Invalid hours used. Must be greater than zero. Try again: ");
                hoursUsed = scanner.nextInt();
            }

            //Consume the newline character in the buffer
            scanner.nextLine();

            System.out.println("Enter zip code: ");
            var zipCode = scanner.nextLine();

            //Validate that the first character in zip code is greater than 0. 
            while (zipCode.charAt(0) <= '0') {
                System.out.println("Invalid zip code. First character must be greater than zero. Try again: ");
                zipCode = scanner.nextLine();
            }

            System.out.println("Enter county: ");
            var countyName = scanner.nextLine();

            //County name cannot be null or empty
            while (countyName.isBlank()) {
                System.out.println("Invalid County. County is required. Try again: ");
                countyName = scanner.nextLine();
            }

            System.out.println("Enter package type: ");
            var packageType = scanner.nextLine();

            //Valid package types are "a", "b", and "c". Case does not matter.
            while (!("a".equalsIgnoreCase(packageType)
                    || "b".equalsIgnoreCase(packageType)
                    || "c".equalsIgnoreCase(packageType))) {
                System.out.println("Invalid package type. Must be A, B, or C. Try again: ");
                packageType = scanner.nextLine();
            }

            //Validation complete, commence calculation!
            //Instantiate a MonthlyBill class
            //Convert county name and package type values to uppercase just in case users entered lower case values.
            var monthlyBill = new MonthlyBill(hoursUsed, zipCode.charAt(0), countyName.toUpperCase(), Character.toUpperCase(packageType.charAt(0)));

            monthlyBill.determineBaseCharge();
            monthlyBill.calculateAdditionalCharge();
            monthlyBill.determineZipCodeSurcharge();
            monthlyBill.calculateSubtotal();
            monthlyBill.determineCountyDiscountPercentage();

            var countyDiscount = monthlyBill.calculateDiscountAmount();
            var billTotal = monthlyBill.calculateBillTotal();

            //Add the current discount to the running total for discount
            totalDiscount += countyDiscount;
            //Add the current bill's total to the overall running total
            totalCharge += billTotal;

            totalCustomers++;

            monthlyBill.displayOutputForBill();
            monthlyBill.calculateAndDisplayPotentialSavings();

            //After the first customer entry, collect hours used here.
            //Reason: the original assignment (line 30) is no longer accessible during the iteration.
            System.out.println("Enter hours used: ");
            hoursUsed = scanner.nextInt();
        }
        System.out.println("Good-bye!");
    }

    //Accessor methods, getters only!
    //No setters because these values can only be set by the loop in the main() method.
    public int getTotalCustomers() {
        return totalCustomers;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public double getTotalCharge() {
        return totalCharge;
    }
}
