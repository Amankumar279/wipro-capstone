package FrameworkPracticequestion;

import java.util.ArrayList;
import java.util.List;

public class Shoppingcart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> al=new ArrayList<String>();

	
	 al.add("Apple");
     al.add("Milk");
     al.add("Bread");
    al.add("Apple");
     al.add("Eggs");
     
     System.out.println("Shopping Cart : " + al);

     // Remove one item
     al.remove("Milk");
     
     // Display after removal
     System.out.println("After Removing Milk : " + al);

     // Add more items
     al.add("Rice");
     al.add("Apple");

     // Final cart
     System.out.println("Final Cart : " + al);

}
}
