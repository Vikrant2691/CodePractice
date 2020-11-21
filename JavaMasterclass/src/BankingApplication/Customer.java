package BankingApplication;

import java.util.ArrayList;

public class Customer {

    private String customerName;
    private ArrayList<Double> transactions;

    public Customer(String customerName, Double initalAmount) {
        this.customerName = customerName;
        addTransaction(initalAmount);
    }

    public void addTransaction(double amount){
        this.transactions.add(amount);
    }

    public static Customer createCustomer(String customerName, Double initialAmount){
        return new Customer(customerName,initialAmount);
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<Double> getTransactions() {
        return transactions;
    }
}
