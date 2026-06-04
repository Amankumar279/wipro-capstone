package FrameworkPracticequestion;

import java.util.List;
import java.util.ArrayList;



public class StudentMarksSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> al=new ArrayList<Integer>();
		
		al.add(40);
		al.add(70);
		al.add(60);
		al.add(99);
		
		int high=al.get(0);
		int low=al.get(0);
		int sum=0;
		
		for(int marks:al) {
			if(high<marks) {
				high=marks;
			}
			if(low>marks) {
				low=marks;
			}
			
			sum+=marks;
		}
		
		System.out.println("highest marks :"+high+"\nlowest marks :"+low+"\navg marks :"+sum/al.size());
		
		

	}

}
