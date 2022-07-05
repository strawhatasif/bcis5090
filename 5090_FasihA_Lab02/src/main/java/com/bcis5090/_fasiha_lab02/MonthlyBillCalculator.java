/*
Asif Fasih
001040771
June 29, 2022
June 25, 2022
This program calculates a customer's monthly bill based on usage, location (zip code, county), and package
 */
package com.bcis5090._fasiha_lab02;

import java.util.Scanner;

public class MonthlyBillCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Package costs, including additional cost per hour for some packages
        final var PACKAGE_A_COST = 9.95;
        final var PACKAGE_A_ADDITIONAL_COST_PER_HOUR = 2.00;
        final var PACKAGE_B_COST = 13.95;
        final var PACKAGE_B_ADDITIONAL_COST_PER_HOUR = 1.00;
        final var PACKAGE_C_COST = 19.95;
    
        var scanner = new Scanner(System.in);

        double surcharge;
        double countyDiscountPercentage;

        System.out.println("Enter hours used: ");
        var hoursUsed = scanner.nextInt();

        //Hours used cannot be less than or equal to zero!
        if (hoursUsed <= 0) {
            System.out.println("Incorrect hours used. Please try again.");
            return;
        }

        //Consumes the newline character in the buffer. 
        //This is necessary when mixing and matching next Scanner methods!
        scanner.nextLine();

        System.out.println("Enter zip code: ");
        var zipCode = scanner.nextLine();

        //The first character of the zip code cannot be less than or equal to zero!
        if (zipCode.charAt(0) <= '0') {
            System.out.println("Incorrect zip code entered. Please try again.");
            return;
        } 
        else {
            /**
             * This method uses the newer syntax introduced in Java 14 for switch statements. 
             * Instead of break, yield is responsible for returning the value.
             * Example from my personal GitHub: https://github.com/strawhatasif/satjug_nov2021_java11to17/blob/main/src/com/fun/java/switchfun/SwitchExpressions.java
             */
            surcharge = switch (zipCode.charAt(0)) {
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

        System.out.println("Enter county: ");
        var county = scanner.nextLine();

        //County name cannot be null or empty.
        if (county.isBlank()) {
            System.out.println("County name is required. Please try again.");
            return;
        } 
        else {
            /**
             * This portion of the code uses an enhancement to the switch statement introduced in Java 14.
             * Determines a discount based on the county of residence for a customer. Not all counties are eligible.
             */
            countyDiscountPercentage = switch (county.toLowerCase()) {
                case "comanche":
                    yield 0.05;
                case "parker":
                    yield 0.10;
                case "erath":
                    yield 0.20;
                default:
                    yield 0.00;
            };
        }

        System.out.println("Enter package type: ");
        var packageType = scanner.nextLine();

        //The package letter must be either "a", "b", or "c". Case does not matter.
        if (!("a".equalsIgnoreCase(packageType)
                || "b".equalsIgnoreCase(packageType)
                || "c".equalsIgnoreCase(packageType))) {
            System.out.println("Incorrect package entered. Please try again.");
        } //Let the calculation begin!
        else {
            //Best practice - close resources to avoid a resource leak!
            scanner.close();

            double basePackageCost;
            var additionalChargeSubtotal = 0.00;
            //Converting to upperCase in case the user enters a lower case value.
            packageType = packageType.toUpperCase();

            /**
             * This switch statement assigns the base package cost based on the package type    
             */
            basePackageCost = switch (packageType) {
                case "A":
                    yield PACKAGE_A_COST;
                case "B":
                    yield PACKAGE_B_COST;
                case "C":
                    yield PACKAGE_C_COST;
                default:
                    yield 0.00;
            };
            
            //Note: \t is a horizontal tab escape sequence. Each represents one tab
            //Display base package cost
            System.out.println("Base Charge for Package " + packageType + ": \t\t"
                    + String.format("$%,.2f", basePackageCost));

            //Calculate and display additional charge based on provided input
            if ("a".equalsIgnoreCase(packageType) && hoursUsed > 10) {
                additionalChargeSubtotal = (hoursUsed - 10) * PACKAGE_A_ADDITIONAL_COST_PER_HOUR;
            }
            else if ("b".equalsIgnoreCase(packageType) && hoursUsed > 20) {
                additionalChargeSubtotal = (hoursUsed - 20) * PACKAGE_B_ADDITIONAL_COST_PER_HOUR;
            }

            System.out.println("Additional Charge for Package " + packageType
                    + ": \t" + String.format("$%,.2f", additionalChargeSubtotal));

            //Display previously calculated surcharge 
            System.out.println("Surcharge for Zip Code: "
                    + "\t\t" + String.format("$%,.2f", surcharge));

            //Calculate and display the subtotal based on previous values
            var subtotal = basePackageCost + additionalChargeSubtotal + surcharge;
            System.out.println("Subtotal for Package " + packageType + ": \t\t"
                    + String.format("$%,.2f", subtotal));

            var countyDiscount = subtotal * countyDiscountPercentage;
            //Display county discount against subtotal 
            System.out.println("Discount Amount for County: "
                    + "\t\t" + String.format("$%,.2f", countyDiscount));

            //Calculate and display base package cost
            var billTotal = subtotal - countyDiscount;
            System.out.println("Total Charge for Package " + packageType + ": \t\t"
                    + String.format("$%,.2f", billTotal));

            //Calculate and display other package potential savings 
            //for customers of Package A IF they are eligible
            if ("a".equalsIgnoreCase(packageType) && hoursUsed >= 20) {
                //If the total cost of the bill is greater than the base price of package B
                //subtract the base cost package of package B from the bill total
                if (billTotal > PACKAGE_B_COST) {
                    var subtotalForPackageB = PACKAGE_B_COST + ((hoursUsed - 20) * 1) + surcharge;
                    
                    var discountAmount = subtotalForPackageB * countyDiscountPercentage;
                    
                    var totalForPackageB = subtotalForPackageB - discountAmount;
                    
                    System.out.println("Package B Savings: " + "\t\t\t"
                            + String.format("$%,.2f", billTotal - totalForPackageB));
                }

                //If the total cost of the bill is greater than the base price of package C
                //subtract the base cost package of package C from the bill total
                if (billTotal > PACKAGE_C_COST) {
                    var subtotalForPackageC = PACKAGE_C_COST + surcharge;
                    
                    var discountAmount = subtotalForPackageC * countyDiscountPercentage;
                    
                    var totalForPackageC = subtotalForPackageC - discountAmount;
                    
                    System.out.println("Package C Savings: " + "\t\t\t"
                            + String.format("$%,.2f", billTotal - totalForPackageC));
                }
            } 
            //Calculate and display other package potential savings 
            //for customers of Package B IF they are eligible
            else if ("b".equalsIgnoreCase(packageType) && hoursUsed > 20) {
                //If the total cost of the bill is greater than the base price of package C
                //subtract the base cost package of package C from the bill total
                if (billTotal > PACKAGE_C_COST) {
                    var subtotalForPackageC = PACKAGE_C_COST + surcharge;
                    
                    var discountAmount = subtotalForPackageC * countyDiscountPercentage;
                    
                    var totalForPackageC = subtotalForPackageC - discountAmount;
                    
                    System.out.println("Package C Savings: " + "\t\t\t"
                            + String.format("$%,.2f", billTotal - totalForPackageC));
                }
            }
        }
    }
}
