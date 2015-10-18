package com.ganesh;

import java.util.Arrays;

public class AnagramCheck {

	public static void main(String[] args) {
		AnagramCheck main = new AnagramCheck();
		System.out.println("Is anagram : "
				+ main.isAnagramWithoutCollections("dabc", "bacd"));
	}

	public boolean isAnagramWithoutCollections(String s1, String s2) {
		boolean isAnagram = true;

		if (s1.length() != s1.length()) {
			isAnagram = false;
		} else {
			char[] c1 = s1.toCharArray();
			for (int i = 0; i < c1.length; i++) {
				char c = c1[i];
				int index = s2.indexOf(c);
				if (index == -1) {
					isAnagram = false;
					break;
				} else {
					s2 = s2.substring(0, index)
							+ s2.substring(index + 1, s2.length());
				}
			}
		}

		return isAnagram && s2.isEmpty();
	}

	public boolean isAnagram(String s1, String s2) {
		boolean isAnagram = false;
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();
		Arrays.sort(c1);
		Arrays.sort(c2);
		for (char c : c1)
			System.out.print(c);
		System.out.println();
		for (char c : c2)
			System.out.print(c);
		System.out.println();
		isAnagram = Arrays.equals(c1, c2);

		return isAnagram;
	}

}
