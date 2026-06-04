package com.wipro.day5;

public class BalancecheckwithException {
	static void withdraw(int balance, int amount) throws LowBalanceException {

        if(amount > balance) {
            throw new LowBalanceException("Insufficient Balance");
        }

        balance = balance - amount;
        System.out.println("Withdraw Successful");
        System.out.println("Remaining Balance: " + balance);
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
	            withdraw(5000, 7000);
	        } catch(LowBalanceException e) {
	            System.out.println(e.getMessage());
	        }

	}

}
