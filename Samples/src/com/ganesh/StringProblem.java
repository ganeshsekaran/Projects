package com.ganesh;

public class StringProblem {

	public static void main(String[] args) {

		String str = "ababaa";

		int len = str.length();
		int count = 0;
		for (int i = 0; i < len; i++) {
			count += compare(str.substring(i, len), str);
		}
		System.out.println(count);
	}

	private static int compare(String str1, String str2) {

		int similar = 0;

		int len = str1 == null ? 0 : str1.length();

		for (int i = 0; i < len; i++) {
			char ch = str1.charAt(i);

			char ch2 = str2.charAt(i);

			if (ch2 == ch) {
				similar++;
			} else {
				break;
			}

		}

		return similar;

	}

}
