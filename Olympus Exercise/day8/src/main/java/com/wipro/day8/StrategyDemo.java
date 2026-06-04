package com.wipro.day8;

public class StrategyDemo {
	public static void main(String[] args) {

		ShoppingCart order1=new ShoppingCart();
		order1.setPaymentStrategy(new COD());
		order1.placeOrder(500);
	}
}

interface PaymentStrategy {
	void pay(int amount);
}

class Card implements PaymentStrategy {
	public void pay(int amount) {
		System.out.println(amount + " Paid using Credit Card");
	}
}

class UpiPay implements PaymentStrategy {
	public void pay(int amount) {
		System.out.println(amount + "  Paid using UPI");
	}
}

class COD implements PaymentStrategy {
	public void pay(int amount) {
		System.out.println(amount + " Paid on CashOnDelivery");
	}
}

class ShoppingCart {

	PaymentStrategy strategy;

	void setPaymentStrategy(PaymentStrategy strategy) {
		this.strategy = strategy;
	}

	void placeOrder(int amount) {
     strategy.pay(amount);
	}

}