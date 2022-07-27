package com.bcis5090._fasiha_lab05;

public class MonthlyBill {

    //Package costs, including additional cost per hour for some packages
    private final double PACKAGE_A_COST = 9.95;
    private final double PACKAGE_A_ADDITIONAL_COST_PER_HOUR = 2.00;
    private final double PACKAGE_B_COST = 13.95;
    private final double PACKAGE_B_ADDITIONAL_COST_PER_HOUR = 1.00;
    private final double PACKAGE_C_COST = 19.95;

    double basePackageCost;
    double additionalPackageCost;
    double subtotal;
    double countyDiscount;
    double billTotal;
    double surcharge;

    /**
     * No argument constructor
     */
    public MonthlyBill() { }

    /**
     * This method determines the base charge based on package type.
     *
     * @param packageType
     * @return double - base charge, with the default as 0.00
     */
    protected double determineBaseCharge(char packageType) {
        return switch (packageType) {
            case 'A':
                yield PACKAGE_A_COST;
            case 'B':
                yield PACKAGE_B_COST;
            case 'C':
                yield PACKAGE_C_COST;
            default:
                yield 0.00;
        };
    }

    /**
     * This method determines the additional charge based on package type and
     * hours used entered.
     *
     * @param packageType
     * @param hours
     * @return double - the calculated additional charge, with the default being
     * 0.00
     */
    protected double calculateAdditionalCharge(char packageType, int hours) {
        double additionalCharge = 0.00;
        if ('A' == packageType && hours > 10) {
            additionalCharge = (hours - 10) * PACKAGE_A_ADDITIONAL_COST_PER_HOUR;
        } 
        else if ('B' == packageType && hours > 20) {
            additionalCharge = (hours - 20) * PACKAGE_B_ADDITIONAL_COST_PER_HOUR;
        }

        return additionalCharge;
    }

    /**
     * This method assigns a surcharge for certain zip codes.
     *
     * @param zipCode
     * @return calculated surcharge
     */
    protected double determineZipCodeSurcharge(char zipCode) {
        surcharge = switch (zipCode) {
            case '3':
                yield 1.50;
            case '5':
                yield 1.75;
            case '8':
                yield 1.95;
            default:
                yield 0.00;
        };

        return surcharge;
    }

    /**
     * Adds up base charge, additional charge, and surcharge and returns the
     * subtotal sum.
     *
     * @param packageType
     * @param hoursUsed
     * @param zipCode
     * @return
     */
    protected double calculateSubtotal(char packageType, int hoursUsed, char zipCode) {
        subtotal = determineBaseCharge(packageType) + calculateAdditionalCharge(packageType, hoursUsed) + determineZipCodeSurcharge(zipCode);
        return subtotal;
    }

    /**
     *
     * @param countyName
     * @return countyDiscountPercentage - the default percentage is 0.00
     */
    protected double determineCountyDiscountPercentage(String countyName) {
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
     * @param packageType
     * @param hoursUsed
     * @param zipCode
     * @param countyName
     * @return
     */
    protected double calculateDiscountAmount(char packageType, int hoursUsed, char zipCode, String countyName) {
        countyDiscount = calculateSubtotal(packageType, hoursUsed, zipCode) * determineCountyDiscountPercentage(countyName);
        return countyDiscount;
    }

    /**
     * Subtracts the county discount amount from the subtotal returns the total
     * sum.
     *
     * @return billTotal
     */
    protected double calculateBillTotal() {
        billTotal = subtotal - countyDiscount;
        return billTotal;
    }

    /**
     * This method is responsible for displaying output to the user. Calculated
     * amounts are in the United States Dollar (USD) format and this method uses
     * the String.format method to accomplish formatting. Tab escape sequences
     * assist with consistent layout for a better user experience.
     *
     * @param packageType
     * @param zipCode
     * @param countyName
     */
    protected void displayOutputForBill(char packageType, char zipCode, String countyName) {
        //Note: \t is a horizontal tab escape sequence. Each represents one tab
        //Display base package cost
        System.out.println("Base Charge for Package " + packageType + ": \t\t\t"
                + String.format("$%,.2f", basePackageCost));
        //Display additional package cost
        System.out.println("Additional Charge for Package " + packageType
                + ": \t\t" + String.format("$%,.2f", additionalPackageCost));
        //Display previously calculated surcharge 
        System.out.println("Surcharge for Zip Code Starting With " + zipCode + ": "
                + "\t" + String.format("$%,.2f", surcharge));
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
     * @param packageType
     * @param hoursUsed
     * @param countyName
     */
    protected void calculateAndDisplayPotentialSavings(char packageType, int hoursUsed, String countyName) {
        //Instantiate the MonthlyBillCalculator class to access the accumulator variables.
        var monthlyBillCalculator = new MonthlyBillCalculator();

        int totalCustomers = monthlyBillCalculator.getTotalCustomers();
        double totalCharge = monthlyBillCalculator.getTotalCharge();
        double totalDiscount = monthlyBillCalculator.getTotalDiscount();

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

        double countyDiscountPercentage = determineCountyDiscountPercentage(countyName);

        //Calculate and display other package potential savings 
        //for customers of Package A IF they are eligible
        if ('A' == packageType && hoursUsed >= 20) {
            //If the total cost of the bill is greater than the base price of package B
            //determine the cost of package B (including the additional cost and surcharge)
            //then, subtract that from the total for package A.
            if (billTotal > PACKAGE_B_COST) {

                var subtotalForPackageB = PACKAGE_B_COST + ((hoursUsed - 20) * 1) + surcharge;

                var discountAmount = subtotalForPackageB * countyDiscountPercentage;

                var totalForPackageB = subtotalForPackageB - discountAmount;
                
                var packageBSavings = billTotal - totalForPackageB;

                System.out.println("Package B Savings: " + "\t\t\t\t"
                        + String.format("$%,.2f", packageBSavings));
            }

            //If the total cost of the bill is greater than the base price of package C
            //determine the cost of package B (including the surcharge)
            //then, subtract that from the total for package A.
            if (billTotal > PACKAGE_C_COST) {
                var subtotalForPackageC = PACKAGE_C_COST + surcharge;

                var discountAmount = subtotalForPackageC * countyDiscountPercentage;

                var totalForPackageC = subtotalForPackageC - discountAmount;
                
                var packageCSavings =  billTotal - totalForPackageC;

                System.out.println("Package C Savings: " + "\t\t\t\t"
                        + String.format("$%,.2f", packageCSavings));

                System.out.println("\n");
            }
        } 
        //Calculate and display other package potential savings 
        //for customers of Package B IF they are eligible
        else if ('B' == packageType && hoursUsed > 20) {
            //If the total cost of the bill is greater than the base price of package C
            //determine the cost of package B (including the surcharge)
            //then, subtract that from the total for package B.
            if (billTotal > PACKAGE_C_COST) {
                var subtotalForPackageC = PACKAGE_C_COST + surcharge;

                var discountAmount = subtotalForPackageC * countyDiscountPercentage;

                var totalForPackageC = subtotalForPackageC - discountAmount;
                
                var packageCSavings = billTotal - totalForPackageC;

                System.out.println("Package C Savings: " + "\t\t\t\t"
                        + String.format("$%,.2f", packageCSavings));

                System.out.println("\n");
            }
        }
    }
}
