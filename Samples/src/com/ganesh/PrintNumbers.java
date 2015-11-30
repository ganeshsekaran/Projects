package com.ganesh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class PrintNumbers {
	
	public static void main(String[] args) throws Exception {
		PrintNumbers main = new PrintNumbers();
		main.printNumberPatern1(1, 5);
		
		
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(ir);
		FileReader fr = new FileReader(new File(""));
		FileInputStream fs = new FileInputStream(new File(""));
	}
	
	public void printNumberPatern1(int startNumber, int max){
		
		if(startNumber > max){
			return;
		}
		
		for(int i=1;i<=startNumber;i++){
			System.out.print(i);
		}
		
		System.out.print(" ");
		printNumberPatern1(startNumber +1 , max);
	}

}
