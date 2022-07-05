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
            
            //Validation complete, commence calculation!
            
            //Instantiate a MonthlyBill class
            //Convert county name and package type values to uppercase just in case users entered lower case values.
            var bill = new Bill(hoursUsed, zipCode.charAt(0), countyName.toUpperCase(), Character.toUpperCase(packageType.charAt(0)));
            var monthlyBill = new MonthlyBill();

            monthlyBill.determineBaseCharge(bill.packageType());
            monthlyBill.calculateAdditionalCharge(bill.packageType(), bill.hoursUsed());
            monthlyBill.determineZipCodeSurcharge(bill.zipCode());
            monthlyBill.calculateSubtotal(bill.packageType(), bill.hoursUsed(), bill.zipCode());
            monthlyBill.determineCountyDiscountPercentage(bill.countyName());
            
            var countyDiscount = monthlyBill.calculateDiscountAmount(bill.packageType(), bill.hoursUsed(), bill.zipCode(), bill.countyName());
            var billTotal = monthlyBill.calculateBillTotal();
            
            //Add the current discount to the running total for discount
            totalDiscount += countyDiscount;
            //Add the current bill's total to the overall running total
            totalCharge += billTotal;
            
            totalCustomers++;
            
            monthlyBill.displayOutputForBill(bill.packageType(), bill.zipCode(), bill.countyName());
            monthlyBill.calculateAndDisplayPotentialSavings(bill.packageType(), bill.hoursUsed(), bill.countyName());
        }

        //Handling edge case where a user enters -1 for hoursUsed at the beginning
        if (hoursUsed == -1) {
            System.out.println("Good-bye!");
        }
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
