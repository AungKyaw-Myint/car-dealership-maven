package com.skills4it.dealership.models;

public abstract class Contract {

    private String contractDate;
    private String customerName;
    private String customerEmail;
    private boolean isSold;
    private double totalPrice;
    private double monthlyPayment;
    private Vehicle vehicle;

    public Contract(String contractDate, String customerName, String customerEmail, boolean isSold, Vehicle vehicle) {
        this.contractDate = contractDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.isSold = isSold;
        this.vehicle= vehicle;
    }

    public Contract(String contractDate, String customerName, String customerEmail, boolean isSold, double totalPrice, double monthlyPayment, Vehicle vehicle) {
        this.contractDate = contractDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.isSold = isSold;
        this.totalPrice = totalPrice;
        this.monthlyPayment = monthlyPayment;
        this.vehicle = vehicle;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();

    public abstract String toCsvHeaderLine() ;

    @Override
    public String toString() {
        return String.format(
                "%-12s %-15s %-20s %-6s %-10s %-10s $%,10.2f $%,10.2f",
                contractDate,
                customerName,
                customerEmail,
                (isSold ? "SALE" : "LEASE"),
                vehicle.getMake(),
                vehicle.getModel(),
                totalPrice,
                monthlyPayment
        );
    }

    public void header(){
        System.out.printf("%-12s %-15s %-20s %-6s %-10s %-10s %-12s %-12s%n",
                "Date", "Name", "Email", "Type", "Make", "Model", "Price", "Monthly");
    }
}
