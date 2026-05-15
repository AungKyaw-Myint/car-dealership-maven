package com.skills4it.dealership.models;

public class SalesContract extends Contract{

    private static final double SALES_TAX_RATE = 0.05;
    private static final double RECORDING_FEE = 100.00;
    private static final double PROCESSING_FEE_UNDER_10000 = 295.00;
    private static final double PROCESSING_FEE_10000_OR_MORE = 495.00;

    private boolean isFinancing;

    public SalesContract(String contractDate, String customerName, String customerEmail, boolean isSold, boolean isFinancing, Vehicle vehicle) {
        super(contractDate, customerName, customerEmail, isSold, vehicle);
        this.isFinancing = isFinancing;
    }

    @Override
    public double getTotalPrice() {
        double vehiclePrice= getVehicle().getPrice();
        double totalPrice =0;
        totalPrice += vehiclePrice < 10000 ? PROCESSING_FEE_UNDER_10000 : PROCESSING_FEE_10000_OR_MORE;
        totalPrice += vehiclePrice + (vehiclePrice * SALES_TAX_RATE) + RECORDING_FEE;
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        if (!isFinancing) {
            return 0;
        }

        double loanAmount = getTotalPrice();

        double annualInterestRate;
        int months;

        if (loanAmount >= 10000) {
            annualInterestRate = 0.0425;
            months = 48;
        } else {
            annualInterestRate = 0.0525;
            months = 24;
        }

        double monthlyInterestRate = annualInterestRate / 12;

        return (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -months));
    }
}
