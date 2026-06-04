package com.wipro.day6;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class FunctionInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		Predicate<Integer> p = (n) -> n > 18;

		System.out.println(p.test(20)); // true
		System.out.println(p.test(10)); // false
		
		
		
		// it takes two type input type return type
		
		Function<Integer, Integer> f = n -> n * n;

		System.out.println(f.apply(5)); // 25
		
		
		 Consumer<Student> cons = t -> System.out.println(t);

	        cons.accept(new Student("Sakshi", "123"));
	        
	        
	        
	        Supplier<Student> supp = () -> new Student("Sakshi","123");
	        Student s = supp.get();

	        System.out.println(s);
	        
	        
	        
		

	}

}
