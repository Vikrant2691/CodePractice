package BankingApplication;

import java.util.ArrayList;

public class Branch {

    private String branchName;
    private ArrayList<Customer> customers;

    public String getBranchName() {
        return branchName;
    }

    public Branch(String branchName) {
        this.branchName = branchName;
        customers= new ArrayList<>();
    }

    public boolean newCustomer(String customerName,double initialAmount){
        if(findCustomer(customerName)!=null) {
            System.out.println("Customer already present.");
            return false;
        }
        else{
            customers.add(Customer.createCustomer(customerName,initialAmount));
            System.out.println("Customet added.");
            return true;
        }

    }

    public boolean customerTransaction(String customerName, double amount){

        Customer customer =findCustomer(customerName);
        if(customer!=null){
            customer.addTransaction(amount);
            return true;
        }
        return false;
    }

    private Customer findCustomer(String customerName){

        for(int i=0;i<customers.size();i++){
            if(customers.get(i).getCustomerName().equals(customerName))
                return customers.get(i);
        }
        return null;
    }
}
