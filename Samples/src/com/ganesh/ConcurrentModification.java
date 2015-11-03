package com.ganesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ConcurrentModification {

	public static void main(String[] args) {
		ConcurrentModification main = new ConcurrentModification();
		List<String> data = new ArrayList<String>();

		data.add("Ganesh");
		data.add("Sekaran");
		//main.test(data);
		//main.test2();
		main.test3();
	}

	private void test(List<String> data) {
		ListIterator<String> itr = data.listIterator();
		while (itr.hasNext()) {
			String str = itr.next();
			if (str.equals("Ganesh")) {
				itr.remove();
			}
		}

		// itr.add(e);
		/*
		 * for(String name : data){ if(name.equals("Sekaran")){
		 * data.remove(name); break; } }
		 */

		for (String name : data) {
			System.out.println(data);
		}
	}

	private void test2() {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();

		String s1 = new String("Ganesh");
		String s2 = new String("Ganesh");

		map.put(s1, "Sekaran");
		map.put(s2, "Sekaran1");

		System.out.println(map.size());

		Map<StringBuffer, String> map2 = new HashMap<StringBuffer, String>();

		StringBuffer sb1 = new StringBuffer("Ganesh");
		StringBuffer sb2 = new StringBuffer("Ganesh");

		map2.put(sb1, "Sekaran");
		map2.put(sb2, "Sekaran1");

		System.out.println(map2.size());
		
		Map<String, String> map3 = new HashMap<String, String>();

		StringBuffer sb11 = new StringBuffer("Ganesh");
		StringBuffer sb22 = new StringBuffer("Ganesh");

		map3.put(sb11.toString(), "Sekaran");
		map3.put(sb22.toString(), "Sekaran1");

		System.out.println(map3.size());

	}
	
	private void test3(){
		Map map = new HashMap();
		
		class A {
			public int hashCode() {
				// TODO Auto-generated method stub
				return 1;			}
		}
		
		map.put(new A(), "Hi");
		map.put(new A(), "Hi");
		
		System.out.println(map.size());
	}

}
