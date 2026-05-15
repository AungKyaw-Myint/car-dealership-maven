package com.skills4it.dealership.models;

public abstract class Contract {

    private String contractDate;
    private String customerName;
    private String customerEmail;
    private boolean soldStatus;
    private double totalPrice;
    private double monthlyPayment;

    public Contract(String contractDate, String customerName, String customerEmail, boolean soldStatus, double totalPrice, double monthlyPayment) {
        this.contractDate = contractDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.soldStatus = soldStatus;
        this.totalPrice = totalPrice;
        this.monthlyPayment = monthlyPayment;
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

    public boolean isSoldStatus() {
        return soldStatus;
    }

    public void setSoldStatus(boolean soldStatus) {
        this.soldStatus = soldStatus;
    }

    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();
}
