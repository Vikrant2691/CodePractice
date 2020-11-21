package BankingApplication;

import java.util.ArrayList;

public class Bank {

    private String bankName;
    private ArrayList<Branch> branches;

    public boolean addBranch(String branchName){
        if(findBranch(branchName)!=null){
            this.branches.add(new Branch(branchName));
            System.out.println("Branch added");
            return true;
        }
        return false;

    }

    private Branch findBranch(String branchName){

        for(int i=0;i<branches.size();i++){
            if(branches.get(i).getBranchName().equals(branchName))
                return branches.get(i);
        }
        return null;
    }

    public boolean addCustomer(String branchName,String customerName,double initialAmount){
        Branch branch= findBranch(branchName);
        if(branch!=null){
            return branch.newCustomer(customerName, initialAmount);
        }
        else{
            System.out.println("Branch name invalid");
            return false;
        }
    }

    public boolean addTransaction(String branchName,String customerName, double amount){
        Branch branch= findBranch(branchName);
        if(branch!=null){
            return branch.customerTransaction(customerName, amount);
        }
        else{
            System.out.println("Branch name invalid");
            return false;
        }
    }

}
