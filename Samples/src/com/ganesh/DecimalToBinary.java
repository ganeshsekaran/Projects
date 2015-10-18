package com.ganesh;

public class DecimalToBinary {

	public static void main(String[] args) {
		DecimalToBinary main = new DecimalToBinary();
		for(int i=0;i<20;i++){
			System.out.println(i + " : " + main.convert(i));
		}
	}

	public String convert(int decimal) {
		String binary = "";
		if (decimal <= 0) {
			binary = "0";
		} else {

			while (decimal > 0) {
				int mod = decimal % 2;
				binary = mod + binary;
				decimal = decimal / 2;
			}
		}
		return binary;
	}
}
