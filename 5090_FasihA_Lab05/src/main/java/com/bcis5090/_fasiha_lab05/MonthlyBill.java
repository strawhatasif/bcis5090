/*
Asif Fasih
001040771
August 03, 2022
July 26, 2022
This class contains attributes used by  MonthlyBillCalculator.java
*/
package com.bcis5090._fasiha_lab05;

public class MonthlyBill {

    //Package costs, including additional cost per hour for some packages
    private final double PACKAGE_A_COST = 9.95;
    private final double PACKAGE_A_ADDITIONAL_COST_PER_HOUR = 2.00;
    private final double PACKAGE_B_COST = 13.95;
    private final double PACKAGE_B_ADDITIONAL_COST_PER_HOUR = 1.00;
    private final double PACKAGE_C_COST = 19.95;

    private int hoursUsed;
    private char zipCode;
    private String countyName;
    private char packageType;

    /**
     * Default constructor that does not accept any arguments.
     */
    public MonthlyBill() {
        //Initialize values with defaults.
        hoursUsed = 0;
        zipCode = ' ';
        countyName = "";
        packageType = ' ';
    }

    /**
     * Parameterized constructor that uses parameters to assign values.
     *
     * @param hoursUsed
     * @param zipCode
     * @param countyName
     * @param packageType
     */
    public MonthlyBill(int hoursUsed, char zipCode, String countyName, char packageType) {

        //Use of the 'this' keyword in Constructors is an industry best practice from my experience.
        //The concept is also covered on p. 567 in the 6th edition (Chapter 8).
        //This practice solves the shadowing problem.
        this.hoursUsed = hoursUsed;
        this.zipCode = zipCode;
        this.countyName = countyName;
        this.packageType = packageType;
    }

    public int getHoursUsed() {
        return hoursUsed;
    }

    public void setHoursUsed(int hoursUsed) {
        this.hoursUsed = hoursUsed;
    }

    public char getZipCode() {
        return zipCode;
    }

    public void setZipCode(char zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public char getPackageType() {
        return packageType;
    }

    public void setPackageType(char packageType) {
        this.packageType = packageType;
    }

    /**
     * This method determines the base charge based on package type.
     *
     * @return double - base charge, with the default as 0.00
     */
    protected double determineBaseCharge() {
        return switch (getPackageType()) {
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
     * @return double - the calculated additional charge, with the default being
     * 0.00
     */
    protected double calculateAdditionalCharge() {
        double customerPackage = getPackageType();
        int hours = getHoursUsed();

        double additionalCharge = 0.00;
        if ('A' == customerPackage && hours > 10) {
            additionalCharge = (hours - 10) * PACKAGE_A_ADDITIONAL_COST_PER_HOUR;
        } else if ('B' == customerPackage && hours > 20) {
            additionalCharge = (hours - 20) * PACKAGE_B_ADDITIONAL_COST_PER_HOUR;
        }

        return additionalCharge;
    }

    /**
     * This method assigns a surcharge for certain zip codes.
     *
     * @return surcharge - the default surcharge is 0.00
     */
    protected double determineZipCodeSurcharge() {
        return switch (getZipCode()) {
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
     * Adds up base charge, additional charge, and surcharge and returns the
     * subtotal sum.
     *
     * @return subtotal
     */
    protected double calculateSubtotal() {
        var subtotal = determineBaseCharge() + calculateAdditionalCharge() + determineZipCodeSurcharge();
        
        return subtotal;
    }

    /**
     *
     * @return countyDiscountPercentage - the default percentage is 0.00
     */
    protected double determineCountyDiscountPercentage() {
        return switch (getCountyName()) {
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
     * @return discountAmount
     */
    protected double calculateDiscountAmount() {
        var discountAmount = calculateSubtotal() * determineCountyDiscountPercentage();
        
        return discountAmount;
    }

    /**
     * Subtracts the county discount amount from the subtotal returns the total
     * sum.
     *
     * @return billTotal
     */
    protected double calculateBillTotal() {
        var total = calculateSubtotal() - calculateDiscountAmount();
        
        return total;
    }

    /**
     * This method is responsible for displaying output to the user. Calculated
     * amounts are in the United States Dollar (USD) format and this method uses
     * the String.format method to accomplish formatting. Tab escape sequences
     * assist with consistent layout for a better user experience.
     *
     */
    protected void displayOutputForBill() {
        double basePackageCost = determineBaseCharge();
        double additionalPackageCost = calculateAdditionalCharge();
        double surcharge = determineZipCodeSurcharge();
        double subtotal = calculateSubtotal();
        double countyDiscount = calculateDiscountAmount();
        double billTotal = calculateBillTotal();

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
     */
    protected void calculateAndDisplayPotentialSavings() {
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

        double billTotal = calculateBillTotal();
        char customerPackage = getPackageType();
        int hours = getHoursUsed();
        
        double surcharge = determineZipCodeSurcharge();
        double countyDiscountPercentage = determineCountyDiscountPercentage();

        //Calculate and display other package potential savings 
        //for customers of Package A IF they are eligible
        if ('A' == customerPackage && hours >= 20) {
            //If the total cost of the bill is greater than the base price of package B
            //determine the cost of package B (including the additional cost and surcharge)
            //then, subtract that from the total for package A.
            if (billTotal > PACKAGE_B_COST) {

                var subtotalForPackageB = PACKAGE_B_COST + ((hours - 20) * 1) + surcharge;

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
        else if ('B' == customerPackage && getHoursUsed() > 20) {
            //If the total cost of the bill is greater than the base price of package C
            //determine the cost of package B (including the surcharge)
            //then, subtract that from the total for package B.
            if (billTotal > PACKAGE_C_COST) {
                var subtotalForPackageC = PACKAGE_C_COST + determineZipCodeSurcharge();

                var discountAmount = subtotalForPackageC * determineCountyDiscountPercentage();

                var totalForPackageC = subtotalForPackageC - discountAmount;
                
                var packageCSavings = billTotal - totalForPackageC;

                System.out.println("Package C Savings: " + "\t\t\t\t"
                        + String.format("$%,.2f", packageCSavings));

                System.out.println("\n");
            }
        }
    }
}
