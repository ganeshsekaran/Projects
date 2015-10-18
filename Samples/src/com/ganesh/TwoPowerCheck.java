package com.ganesh;

public class TwoPowerCheck {

	public static void main(String[] args) {
		int number = 4;
		
		//System.out.println(number & number-1);
		
		for(int i=0;i<30;i++){
			String binaryOfi = new DecimalToBinary().convert(i);
			String binaryOfiminus1 = new DecimalToBinary().convert(i-1);
			System.out.println("Binary of " + i + " is : " + binaryOfi);
			System.out.println("Binary of " + (i-1) + " is : " + binaryOfiminus1);
			if((i & i-1) == 0){
				System.out.println(i);
			}
		}
	}
	
}
