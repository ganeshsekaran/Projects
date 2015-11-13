package com.ganesh;

public class StaticTest {
	
	public static void main(String[] args) {
		StaticTest main = new StaticTest();
		main.m(0);
	}
	
	public void m(int i){
		
		String s1 = "abcd";
		String s2 = "abc" + "d";
				
		System.out.println(s1 == s2);		
		
	}
	
	
}


class b extends StaticTest{
	
	public void m(int i, int j){
		
	}
	
public void m(int i){
		
	}
}

class main{
	
}
