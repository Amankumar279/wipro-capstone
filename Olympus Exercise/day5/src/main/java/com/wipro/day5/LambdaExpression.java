package com.wipro.day5;

public class LambdaExpression {
	interface Shape
	{
		void area(int x,int y);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Shape square= (x,y) ->
        {  System.out.println("area of rectangle:"+(x*y));
              System.out.println("Thankyou!");
               };

square.area(4,5);

	}

}
