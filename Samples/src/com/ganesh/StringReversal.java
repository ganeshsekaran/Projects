package com.ganesh;

public class StringReversal {
	
	public static void main(String[] args) {
		StringReversal main = new StringReversal();
		String str = "Ganesh";
		String reversed = main.reverse(str);
		System.out.println(str.substring(1));
		System.out.println(reversed);
	}
	
	
	public String reverse(String str){
		if(str == null || str.length() < 2){
			return str;
		}
		return reverse(str.substring(1))+str.charAt(0);
	}

}
