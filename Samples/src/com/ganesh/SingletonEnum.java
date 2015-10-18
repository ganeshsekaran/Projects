package com.ganesh;

public enum SingletonEnum {
	
	INSTANCE(10);
	
	private SingletonEnum(int count){
		System.out.println("Test");
	}
	
	public static SingletonEnum getInstance(){
		return INSTANCE;
	}
	
	public static void doThis(){
		SingletonEnum.getInstance();
	}
	
	public static void main(String[] args) {
		//doThis();
	}

}
