package com.wipro.day6;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;
import com.wipro.day6.JavaJunit;



public class Testing {

	@BeforeAll
	static void show()
	{
		System.out.println("before all test case working");
	}
	
	@BeforeEach
	void display()
	{
		System.out.println("before each test case method is running!");
	}
	
	@Test
	void test1()
	{
		Maths obj=new Maths();
    	int result= obj.add(4,5);
	   	assertEquals(9,result);		
	}
	
	

	@Test
	void test2()
	{
		 Maths obj=new Maths();
	   	int result= obj.square(8);
	   	assertEquals(64,result);	   	 		
		
}
	
	@Test
	void test3()
	{
		 Maths obj=new Maths();
	   	int result= obj.square(5);
	   assertNotEquals(8, result);   	 			
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"radar", "level", "racecar"})
	void isPalindrome(String candidate) {
		 Maths obj=new Maths();
	    assertTrue(obj.isPalindrome(candidate));
	}
	
	@Test
	void test4()
	{
		 Maths obj=new Maths();
	   	String result= obj.getName("ggg");
           assertTrue(result.length()>2);  	 			
	}
	@Test
	void test5()
	{
		 Maths obj=new Maths();
	   	String result= obj.getName("AmanKumarrrrrr vghjlhfiurhfoklerh");
           assertFalse(result.length()<25);  	 			
	}
	@Test
	void test6()
	{
		int arr1[]= {1,2,3,4,5};	 //actual output 
		int arr2[]= {1,2,3,4,5};	 //expected
		assertArrayEquals(arr2,arr1);
	}
	@Test
	void test7()
	{
		 Maths obj=new Maths();
		assertThrows(MyException.class, () -> obj.throwException());
		
	}	
	
	@Test
	void test8()
	{
		Maths obj=new Maths();
		assertTrue(obj.prime(7));
	}
	
	void test9()
	{
		Maths obj=new Maths();
		assertTrue(obj.evenodd(4));
		
	}
	
	@Test
	void test10() {
	    Maths obj = new Maths();
	    assertEquals(2, obj.countVowel("aman"));
	}
	
	@Test
	void test11() {
		Maths obj = new Maths();
	    assertEquals("nama",  obj.reverseString("aman"));
		
	}
	
	@Test
	void test12() {
		Maths obj = new Maths();
		int arr[] = {5, 8, 2, 10, 3};

        assertEquals(10, obj.maxArray(arr));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}