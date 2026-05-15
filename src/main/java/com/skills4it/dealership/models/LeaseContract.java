package com.skills4it.dealership.models;

import com.skills4it.dealership.ui.enums.ContractOption;

public class LeaseContract extends Contract{

    private static final double ENDING_VALUE_RATE = 0.5;
    private static final double LEASE_FEE_RATE = 0.07;

    public LeaseContract(String contractDate, String customerName, String customerEmail, boolean isSold, Vehicle vehicle) {
        super(contractDate, customerName, customerEmail, isSold, vehicle);
    }


    public LeaseContract(String contractDate, String customerName, String customerEmail, boolean isSold, double totalPrice, double monthlyPayment, Vehicle vehicle) {
        super(contractDate, customerName, customerEmail, isSold, totalPrice, monthlyPayment, vehicle);
    }

    @Override
    public double getTotalPrice() {
        double vehiclePrice= getVehicle().getPrice();

        return vehiclePrice+(vehiclePrice*LEASE_FEE_RATE);
    }

    @Override
    public double getMonthlyPayment() {
        double price = getVehicle().getPrice();

        double expectedEndingValue = price * 0.50;

        double loanAmount = getTotalPrice() - expectedEndingValue;

        double annualInterestRate = 0.04;
        int months = 36;

        double monthlyInterestRate = annualInterestRate / 12;

        return (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -months));
    }

    @Override
    public String toCsvHeaderLine() {
        return String.join("|",
                ContractOption.LEASE.toString(),
                getContractDate(),
                getCustomerName(),
                getCustomerEmail(),
                Boolean.toString(isSold()),
                String.format("%.2f", getTotalPrice()),
                String.format("%.2f", getMonthlyPayment()),
                "N/A",
                ""
        );
    }


}
