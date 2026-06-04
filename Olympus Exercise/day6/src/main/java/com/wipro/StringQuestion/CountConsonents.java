package com.wipro.StringQuestion;

public class CountConsonents {
	public static int count(String s) {
		int count=0;
		for(int i=0;i<s.length();i++) {
			char ch=s.charAt(i);
			if(ch!='a'&& ch!='e' && ch!='i' && ch!='o' &&ch!='u' ) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
      System.out.println(CountConsonents.count("amankumar"));
	}

}
