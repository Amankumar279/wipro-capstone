package com.wipro.StringQuestion;

public class ReplaceConsonent {
	public static String count(String s) {
	
		 StringBuilder sb = new StringBuilder(s);

	        for(int i = 0; i < s.length(); i++) {

	            char ch = Character.toLowerCase(s.charAt(i));

	            

	                if(ch!='a' && ch!='e' && ch!='i'
	                   && ch!='o' && ch!='u') {

	                    sb.setCharAt(i, '0');
	                }
	            
	        }

	        return sb.toString();
	    }

	    public static void main(String[] args) {

	        System.out.println(count("amankumar"));
	    }

}
