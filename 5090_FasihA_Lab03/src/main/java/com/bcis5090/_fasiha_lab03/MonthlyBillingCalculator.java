/*
Asif Fasih
001040771
July 13, 2022
July 05, 2022
This program calculates a customer's monthly bill based on usage, location (zip code, county), and package
 */
package com.bcis5090._fasiha_lab03;

import java.util.Scanner;

public class MonthlyBillingCalculator {

    public static void main(String[] args) {
        //Package costs, including additional cost per hour for some packages
        final double PACKAGE_A_COST = 9.95;
        final double PACKAGE_A_ADDITIONAL_COST_PER_HOUR = 2.00;
        final double PACKAGE_B_COST = 13.95;
        final double PACKAGE_B_ADDITIONAL_COST_PER_HOUR = 1.00;
        final double PACKAGE_C_COST = 19.95;

        var scanner = new Scanner(System.in);
        //Accumulator and counters
        int totalCustomers = 0;
        double totalDiscount = 0.00;
        double totalCharge = 0.00;

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
            var county = scanner.nextLine();

            //County name cannot be null or empty
            while (county.isBlank()) {
                System.out.println("Invalid County. County is required. Try again: ");
                county = scanner.nextLine();
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

            totalCustomers++;

            //Converting to upperCase in case the user enters a lower case value.
            packageType = packageType.toUpperCase();

            //Calculate base package cost based on the package type provided by the customer.
            var basePackageCost = switch (packageType) {
                case "A":
                    yield PACKAGE_A_COST;
                case "B":
                    yield PACKAGE_B_COST;
                case "C":
                    yield PACKAGE_C_COST;
                default:
                    yield 0.00;
            };

            //Calculate additional charge based on provided input
            double additionalPackageCost = 0.00;
            if ("a".equalsIgnoreCase(packageType) && hoursUsed > 10) {
                additionalPackageCost = (hoursUsed - 10) * PACKAGE_A_ADDITIONAL_COST_PER_HOUR;
            } 
            else if ("b".equalsIgnoreCase(packageType) && hoursUsed > 20) {
                additionalPackageCost = (hoursUsed - 20) * PACKAGE_B_ADDITIONAL_COST_PER_HOUR;
            }

            //Calculate surcharge based on zip code
            var surcharge = switch (zipCode.charAt(0)) {
                case '3':
                    yield 1.50;
                case '5':
                    yield 1.75;
                case '8':
                    yield 1.95;
                default:
                    yield 0.00;
            };

            //Calculate the subtotal based on previous input
            var subtotal = basePackageCost + additionalPackageCost + surcharge;

            /**
             * This portion of the code uses an enhancement to the switch
             * statement introduced in Java 14. Determines a discount based on
             * the county of residence for a customer. Not all counties are
             * eligible.
             */
            var countyDiscountPercentage = switch (county.toLowerCase()) {
                case "comanche":
                    yield 0.05;
                case "parker":
                    yield 0.10;
                case "erath":
                    yield 0.20;
                default:
                    yield 0.00;
            };

            var countyDiscount = subtotal * countyDiscountPercentage;
            //Add the current discount to the running total for discount
            totalDiscount += countyDiscount;
            //Calculate the overall bill amount
            var billTotal = subtotal - countyDiscount;
            //Add the current bill's total to the overall running total
            totalCharge += billTotal;

            //Note: \t is a horizontal tab escape sequence. Each represents one tab
            //Display base package cost
            System.out.println("Base Charge for Package " + packageType + ": \t\t\t"
                    + String.format("$%,.2f", basePackageCost));
            //Display additional package cost
            System.out.println("Additional Charge for Package " + packageType
                    + ": \t\t" + String.format("$%,.2f", additionalPackageCost));
            //Display previously calculated surcharge 
            System.out.println("Surcharge for Zip Code: "
                    + "\t\t\t" + String.format("$%,.2f", surcharge));
            //Display subtotal based on previous calculation
            System.out.println("Subtotal for Package " + packageType + ": \t\t\t"
                    + String.format("$%,.2f", subtotal));
            //Display county discount against subtotal 
            System.out.println("Discount Amount for County: "
                    + "\t\t\t" + String.format("$%,.2f", countyDiscount));
            //Display total bill cost 
            System.out.println("Total Charge for Package " + packageType + ": \t\t\t"
                    + String.format("$%,.2f", billTotal));

            System.out.println("\n");

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
        }

        //Handling edge case where a user enters -1 for hoursUsed at the beginning
        if (hoursUsed == -1) {
            System.out.println("Good-bye!");
        }
    }
}
