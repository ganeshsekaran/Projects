package com.ganesh;

public class IntegerPalindrome {
	
	public static void main(String[] args) {
		IntegerPalindrome main = new IntegerPalindrome();
		boolean isPalindrome = main.isPalindrome(101);
		System.out.println(isPalindrome);
		
	}
	
	public boolean isPalindrome(Integer number){
		Integer reverse = 0;
		Integer num = number;
		
		while(num != 0){
			System.out.println("reverse*10 : " + reverse*10);
			System.out.println("num%10 : " +  num%10);
			reverse = reverse*10 + num%10; 
			System.out.println("reverse : " + reverse);
			System.out.println("num/10 : " +  num/10);
			num = num/10;
		}
		
		return reverse.equals(number);
	}

}
