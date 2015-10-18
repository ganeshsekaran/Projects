package com.ganesh;

public class ConcurrentHashMapMain {

	public static void main(String[] args) {
		ConcurrentHashMapMain main = new  ConcurrentHashMapMain();
		ConcurrentHashMap map = new ConcurrentHashMap();
		int index = map.getIndex(89);
		System.out.println("Index : " + index);
		
		System.out.println(10/3);
		System.out.println((double)10%3);
	}
}
