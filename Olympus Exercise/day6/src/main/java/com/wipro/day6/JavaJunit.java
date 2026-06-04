package com.wipro.day6;

public class JavaJunit {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Maths obj=new Maths();
		System.out.println(obj.add(4, 5));
		System.out.println(obj.square(4));
		System.out.println(obj.prime(4));
		
	}

}


class Maths {
	 public int add(int x,int y) { 
		 return x+y;
	 }
	 public int square(int x) {
		 return x*x;
	 }
	 public String getName(String name)
		{
			return  name;
		}
		
		 public int throwException() throws MyException {
		        throw new MyException("This is Custom exception message");
		 }
		

			    public  boolean isPalindrome(String str) {

			        String reverse =
			                new StringBuilder(str).reverse().toString();

			        return str.equalsIgnoreCase(reverse);
			    }
			    
	   public boolean prime(int no) {
		   if(no<=1) return false ;
		   
		   for(int i=2;i<no;i++) {
			   if(no%i==0) return false;
		   }
		   return true;
	   }
	   
	   public boolean evenodd(int n) {
		   if(n%2==0) {
			   return true;
		   }
		   else {
			   return false;
		   }
	   }
	   
	   // Count vowels
	    public int countVowel(String str) {
	        int count = 0;

	        str = str.toLowerCase();

	        for (int i = 0; i < str.length(); i++) {
	            char ch = str.charAt(i);

	            if (ch == 'a' || ch == 'e' || ch == 'i' ||
	                ch == 'o' || ch == 'u') {
	                count++;
	            }
	        }

	        return count;
	    }

	    // Reverse String
	    public String reverseString(String str) {
	        return new StringBuilder(str).reverse().toString().toLowerCase();
	    }

	    // Max number in array
	    public int maxArray(int arr[]) {

	        int max = arr[0];

	        for (int i = 1; i < arr.length; i++) {
	            if (arr[i] > max)
	                max = arr[i];
	        }

	        return max;
	    }
			
		
	
}