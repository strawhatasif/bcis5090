/*
Asif Fasih
001040771
July 20, 2022
July 05, 2022
This program calculates a customer's monthly bill based on usage, location (zip code, county), and package
 */
package com.bcis5090._fasiha_lab04;

import java.util.Scanner;

public class MonthlyBillingCalculator {

    //Package costs, including additional cost per hour for some packages
    private static final double PACKAGE_A_COST = 9.95;
    private static final double PACKAGE_A_ADDITIONAL_COST_PER_HOUR = 2.00;
    private static final double PACKAGE_B_COST = 13.95;
    private static final double PACKAGE_B_ADDITIONAL_COST_PER_HOUR = 1.00;
    private static final double PACKAGE_C_COST = 19.95;

    private static int totalCustomers = 0;
    private static double totalDiscount = 0.00;
    private static double totalCharge = 0.00;

    public static void main(String[] args) {

        var scanner = new Scanner(System.in);

        System.out.println("Enter hours used: ");
        var hoursUsed = scanner.nextInt();

        while (hoursUsed != -1) {
            if (totalCustomers >= 1) {
                System.out.println("Enter hours used: ");
                hoursUsed = scanner.nextInt();

                //Printing a message and gracefully exiting the program if a user enters -1.
                if (hoursUsed == -1) {
                    System.out.println("Good-bye!");
                    scanner.close();
                    return;
                }
            }

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

            //Converting to upperCase in case the user enters a lower case value.
            packageType = packageType.toUpperCase();
            countyName = countyName.toUpperCase();
            
            //Validation complete, commence calculation!

            double baseCharge = determineBaseCharge(packageType);
            double additionalCharge = calculateAdditionalCharge(packageType, hoursUsed);
            double surcharge = determineZipCodeSurcharge(zipCode.charAt(0));
            double subtotal = calculateSubtotal(baseCharge, additionalCharge, surcharge);
            var countyDiscountPercentage = determineCountyDiscountPercentage(countyName);
            var countyDiscount = calculateDiscountAmount(subtotal, countyDiscountPercentage);
            var billTotal = calculateBillTotal(subtotal, countyDiscount);
            
            //Add the current discount to the running total for discount
            totalDiscount += countyDiscount;
            //Add the current bill's total to the overall running total
            totalCharge += billTotal;
            
            totalCustomers++;
            
            displayOutputForBill(packageType, baseCharge, additionalCharge, surcharge, subtotal, countyDiscount, billTotal, countyName, zipCode);
            calculateAndDisplayPotentialSavings(hoursUsed, packageType, surcharge, countyDiscountPercentage, billTotal);
        }

        //Handling edge case where a user enters -1 for hoursUsed at the beginning
        if (hoursUsed == -1) {
            System.out.println("Good-bye!");
        }

    }

    /**
     * This method determines the base charge based on package type entered.
     *
     * @param packageType - String denoting the package type
     * @return double - base charge, with the default as 0.00
     */
    protected static double determineBaseCharge(String packageType) {
        return switch (packageType) {
            case "A":
                yield PACKAGE_A_COST;
            case "B":
                yield PACKAGE_B_COST;
            case "C":
                yield PACKAGE_C_COST;
            default:
                yield 0.00;
        };
    }

    /**
     * This method determines the additional charge based on package type and
     * hours used entered.
     *
     * @param packageType - String denoting the package type
     * @param hoursUsed - must be a positive value, denotes the number of hours
     * used by the customer.
     * @return double - the calculated additional charge, with the default being
     * 0.00
     */
    protected static double calculateAdditionalCharge(String packageType, int hoursUsed) {
        double additionalCharge = 0.00;
        if ("a".equalsIgnoreCase(packageType) && hoursUsed > 10) {
            additionalCharge = (hoursUsed - 10) * PACKAGE_A_ADDITIONAL_COST_PER_HOUR;
        } 
        else if ("b".equalsIgnoreCase(packageType) && hoursUsed > 20) {
            additionalCharge = (hoursUsed - 20) * PACKAGE_B_ADDITIONAL_COST_PER_HOUR;
        }

        return additionalCharge;
    }

    /**
     * This method assigns a surcharge for certain zip codes.
     *
     * @param zipCodeFirstCharacter - the first character from the zipCode
     * variable (data type: String)
     * @return surcharge - the default surcharge is 0.00
     */
    protected static double determineZipCodeSurcharge(char zipCodeFirstCharacter) {
        return switch (zipCodeFirstCharacter) {
            case '3':
                yield 1.50;
            case '5':
                yield 1.75;
            case '8':
                yield 1.95;
            default:
                yield 0.00;
        };
    }

    /**
     * Adds up parameters and returns the subtotal sum.
     *
     * @param basePackageCost
     * @param additionalPackageCost
     * @param surcharge
     * @return subtotal
     */
    protected static double calculateSubtotal(double basePackageCost, double additionalPackageCost, double surcharge) {
        return basePackageCost + additionalPackageCost + surcharge;
    }

    /**
     *
     * @param countyName
     * @return countyDiscountPercentage - the default percentage is 0.00
     */
    protected static double determineCountyDiscountPercentage(String countyName) {
        return switch (countyName) {
            case "COMANCHE":
                yield 0.05;
            case "PARKER":
                yield 0.10;
            case "ERATH":
                yield 0.20;
            default:
                yield 0.00;
        };
    }

    /**
     * Multiplies the subtotal and county discount percentage and returns the
     * product.
     *
     * @param subtotal
     * @param countyDiscountPercentage
     * @return discountAmount
     */
    protected static double calculateDiscountAmount(double subtotal, double countyDiscountPercentage) {
        return subtotal * countyDiscountPercentage;
    }

    /**
     * Subtracts the county discount amount from the subtotal returns the total
     * sum.
     *
     * @param subtotal
     * @param countyDiscount
     * @return billTotal
     */
    protected static double calculateBillTotal(double subtotal, double countyDiscount) {
        return subtotal - countyDiscount;
    }

    /**
     * This method is responsible for displaying output to the user. Calculated
     * amounts are in the United States Dollar (USD) format and this method uses
     * the String.format method to accomplish formatting. Tab escape sequences
     * assist with consistent layout for a better user experience.
     *
     * @param packageType
     * @param basePackageCost
     * @param additionalPackageCost
     * @param surcharge
     * @param subtotal
     * @param countyDiscount
     * @param billTotal
     * @param countyName
     * @param zipCode
     */
    protected static void displayOutputForBill(String packageType, double basePackageCost, double additionalPackageCost, double surcharge, double subtotal, double countyDiscount, double billTotal, String countyName, String zipCode) {
        //Note: \t is a horizontal tab escape sequence. Each represents one tab
        //Display base package cost
        System.out.println("Base Charge for Package " + packageType + ": \t\t\t"
                + String.format("$%,.2f", basePackageCost));
        //Display additional package cost
        System.out.println("Additional Charge for Package " + packageType
                + ": \t\t" + String.format("$%,.2f", additionalPackageCost));
        //Display previously calculated surcharge 
        System.out.println("Surcharge for Zip Code - " + zipCode + ": "
                + "\t\t" + String.format("$%,.2f", surcharge));
        //Display subtotal based on previous calculation
        System.out.println("Subtotal for Package " + packageType + ": \t\t\t"
                + String.format("$%,.2f", subtotal));
        //Display county discount against subtotal 
        System.out.println("Discount Amount for " + countyName + " County: "
                + "\t\t" + String.format("$%,.2f", countyDiscount));
        //Display total bill cost 
        System.out.println("Total Charge for Package " + packageType + ": \t\t\t"
                + String.format("$%,.2f", billTotal));

        System.out.println("\n");
    }

    /**
     * This method is responsible for calculating potential savings IF the
     * customer has either Package A or B. It also displays output to the user.
     *
     * Calculated amounts are in the United States Dollar (USD) format and this
     * method uses the String.format method to accomplish formatting. Tab escape
     * sequences assist with consistent layout for a better user experience.
     *
     * @param hoursUsed
     * @param packageType
     * @param surcharge
     * @param countyDiscountPercentage
     * @param billTotal
     */
    protected static void calculateAndDisplayPotentialSavings(int hoursUsed, String packageType, double surcharge, double countyDiscountPercentage, double billTotal) {
        //Note: \t is a horizontal tab escape sequence. Each represents one tab
        
        //Display running total for the number of customers.
        System.out.println("Total Discount Amount for All Packages:  \t" + String.format("$%,.2f", totalDiscount));

        //Display running total for the number of customers.
        System.out.println("Total Charge for All Packages: \t\t\t" + String.format("$%,.2f", totalCharge));

        //Display running total for the number of customers.
        System.out.println("Average Discount Amount: \t\t\t" + String.format("$%,.2f", ((totalDiscount / totalCustomers))));

        //Display running total for the number of customers.
        System.out.println("Average Total Charge: \t\t\t\t" + String.format("$%,.2f", (totalCharge / totalCustomers)));

        //Display running total for the number of customers.
        System.out.println("Total Customers: \t\t\t\t" + totalCustomers + "\n");

        //Calculate and display other package potential savings 
        //for customers of Package A IF they are eligible
        if ("a".equalsIgnoreCase(packageType) && hoursUsed >= 20) {
            //If the total cost of the bill is greater than the base price of package B
            //determine the cost of package B (including the additional cost and surcharge)
            //then, subtract that from the total for package A.
            if (billTotal > PACKAGE_B_COST) {

                var subtotalForPackageB = PACKAGE_B_COST + ((hoursUsed - 20) * 1) + surcharge;

                var discountAmount = subtotalForPackageB * countyDiscountPercentage;

                var totalForPackageB = subtotalForPackageB - discountAmount;

                System.out.println("Package B Savings: " + "\t\t\t\t"
                        + String.format("$%,.2f", billTotal - totalForPackageB));
            }

            //If the total cost of the bill is greater than the base price of package C
            //determine the cost of package B (including the surcharge)
            //then, subtract that from the total for package A.
            if (billTotal > PACKAGE_C_COST) {
                var subtotalForPackageC = PACKAGE_C_COST + surcharge;

                var discountAmount = subtotalForPackageC * countyDiscountPercentage;

                var totalForPackageC = subtotalForPackageC - discountAmount;

                System.out.println("Package C Savings: " + "\t\t\t\t"
                        + String.format("$%,.2f", billTotal - totalForPackageC));

                System.out.println("\n");
            }
        } 
        //Calculate and display other package potential savings 
        //for customers of Package B IF they are eligible
        else if ("b".equalsIgnoreCase(packageType) && hoursUsed > 20) {
            //If the total cost of the bill is greater than the base price of package C
            //determine the cost of package B (including the surcharge)
            //then, subtract that from the total for package B.
            if (billTotal > PACKAGE_C_COST) {
                var subtotalForPackageC = PACKAGE_C_COST + surcharge;

                var discountAmount = subtotalForPackageC * countyDiscountPercentage;

                var totalForPackageC = subtotalForPackageC - discountAmount;

                System.out.println("Package C Savings: " + "\t\t\t\t"
                        + String.format("$%,.2f", billTotal - totalForPackageC));

                System.out.println("\n");
            }
        }

    }
}
