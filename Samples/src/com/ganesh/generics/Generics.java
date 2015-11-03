package com.ganesh.generics;

import java.util.ArrayList;
import java.util.List;


class A {

}

class B extends A {

}

class C extends B {

}

public class Generics {
	
	public static void main(String[] args) {
		List<A> list1 = new ArrayList<A>();
		list1.add(new A());
		list1.add(new B());
		list1.add(new C());
		list1.add(null);
		
		sum(list1);

		
		List<Object> list2 = new ArrayList<Object>();
		list2.add(new A());
		list2.add(new B());
		list2.add(new C());
		//sum(list2);
		
		List<B> list = new ArrayList<B>();
//		list.add(new A());
		list.add(new B());
		list.add(new C());
		sum(list);
		
		List<C> listc = new ArrayList<C>();
//		list.add(new A());
//		listc.add(new B());
		listc.add(new C());
		sum(listc);
		
		List<? extends B> list3 = new ArrayList<C>();
	//	list3.add(new A());
		list3.add(new B());
		list3.add(new C());
		
		
		List<? super B> list4 = new ArrayList<B>();
		list4.add(new Object());
		list4.add(new A());
		list4.add(new B());
		list4.add(new C());
		sum(list4);
		
	}
	
//	public static double sum(List<B> list){
//		return 0;
//	}

	
	public static double sum(List<? super B> list){
		//list.add(new A());
		list.add(new A());
		list.add(new B());
		list.add(new C());
		return 0;
	}
	
	
	pu
	
	

}


