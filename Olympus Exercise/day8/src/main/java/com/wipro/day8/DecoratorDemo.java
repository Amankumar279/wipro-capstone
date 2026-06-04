package com.wipro.day8;

public class DecoratorDemo {
	public static void main(String[] args) {
		
		
		   Pizza pizza = new PlainPizza();

	       pizza = new Cheese(pizza); //decorator
	   

	       System.out.println(pizza.getDescription());
	       System.out.println(pizza.cost());
	}
	   
	
}
interface Pizza {

 String getDescription();
 int cost();
}

class PlainPizza implements Pizza {
 public String getDescription() {
     return "Plain Pizza";
 }
 public int cost() {
     return 100;
 }
}

abstract class PizzaDecorator implements Pizza {
 protected Pizza pizza;

 PizzaDecorator(Pizza pizza) {
     this.pizza = pizza;
 }
}

//Cheese topping
class Cheese extends PizzaDecorator {
 Cheese(Pizza pizza) {
     super(pizza);
 }

 public String getDescription() {
     return pizza.getDescription() + ", Cheese";
 }

 public int cost() {
     return pizza.cost() + 30;
 }
}

//Paneer topping
class Paneer extends PizzaDecorator {
 Paneer(Pizza pizza) {
     super(pizza);
 }

 public String getDescription() {
     return pizza.getDescription() + ", Paneer";
 }

 public int cost() {
     return pizza.cost() + 50;
 }

}
