package com.wipro.StringQuestion;

public class RepeatCharacter {

	 public static int countChar(String str, char target) {

	        int count = 0;

	        for(int i = 0; i < str.length(); i++) {

	            if(str.charAt(i) == target) {
	                count++;
	            }
	        }

	        return count;
	    }

	    public static void main(String[] args) {

	        String str = "amankumar";

	        System.out.println(countChar(str, 'a'));
	    }

}
