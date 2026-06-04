package com.wipro.day8;

public class FactoryDemo {

	public static void main(String[] args) {
		  Payment p = PaymentFactory.getPayment("UPI");
	        p.pay();
	}

}

interface Payment
{
	void pay();
}

class CreditCard implements Payment {
  public void pay() {
      System.out.println("Paid using Credit Card");
  }
}

class UPI implements Payment {
  public void pay() {
      System.out.println("Paid using UPI");
  }
}
class CashOnDelivery implements Payment {
  public void pay() {
      System.out.println("Paid on CashOnDelivery");
  }
}

class PaymentFactory {
	
  public static Payment getPayment(String type) {
      if (type.equalsIgnoreCase("card"))
          return new CreditCard();
      else if (type.equalsIgnoreCase("upi"))
          return new UPI();
      return new CashOnDelivery();
  }
}