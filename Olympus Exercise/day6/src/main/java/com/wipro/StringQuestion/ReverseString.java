package com.wipro.StringQuestion;

public class ReverseString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String a="aman";
//		String b="";
//		for(int i=a.length()-1;i>=0;i--) {
//			char ch=a.charAt(i);
//			
//			b+=ch;
//		}
//		System.out.println(b);
//				
//
//	}
		//second method
		StringBuilder sb=new StringBuilder(a);
		String c=sb.reverse().toString();
		System.out.println(c);

}
}
