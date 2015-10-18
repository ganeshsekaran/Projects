package com.ganesh;

public class EnumSamples {
	
	public enum Singleton{
		INSTATNCE;
		
		private Singleton() {
			// TODO Auto-generated constructor stub
		}
		
		public static final Singleton getInstance(){
			return INSTATNCE;
		}
	}
	
	
	public void test(){
		Singleton tetst = 	Singleton.getInstance();
	}

}
