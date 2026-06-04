package com.wipro.day8;

public class Observerdemo2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Subscriber Investor1 = new Investor1("Aman");

		Subscriber Investor2 = new Investor2("Saquib");

		Subscriber Investors[] = { Investor1, Investor2 };
		Stockmarket sm = new Stockmarket(Investors);
		sm.notifyall("Stock price increased");

	}

}

interface Subscriber {
	void updates(String message);
}

class Investor1 implements Subscriber {

	String name;

	public Investor1(String name) {
		this.name = name;
	}

	public void updates(String message) {
		System.out.println(name + " - " + message);
	}
}
class Investor2 implements Subscriber {

	String name;

	public Investor2(String name) {
		this.name = name;
	}

	public void updates(String message) {
		System.out.println(name + " - " + message);
	}
}

class Stockmarket {
	Subscriber Investors[];
	
	public Stockmarket(Subscriber Investors[]) {
		this.Investors=Investors;
	}
	
	public void notifyall(String msg) {
		for (Subscriber Investors : Investors) {
				Investors.updates(msg);
		}
	}
}