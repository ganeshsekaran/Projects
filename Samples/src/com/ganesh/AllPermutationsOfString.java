package com.ganesh;

import java.util.HashSet;

public class AllPermutationsOfString {

	public static void main(String[] args) {
		AllPermutationsOfString main = new AllPermutationsOfString();
		String str = "gan";
		HashSet<String> set = new HashSet<String>();
		main.getAllPermutation("", str, set);
		
		for(String st : set){
			System.out.println(st);
		}

	}

	private void getAllPermutation(String prefix, String str, HashSet<String>set) {
		int n = str.length();
		if (n == 0){
			set.add(prefix);
		}
		else {
			for (int i = 0; i < n; i++) {
				getAllPermutation(prefix + str.charAt(i), str.substring(0, i)
						+ str.substring(i + 1, n), set);
			}
		}
	}
}
