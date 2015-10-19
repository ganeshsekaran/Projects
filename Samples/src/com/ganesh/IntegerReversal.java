package com.ganesh;

public class IntegerReversal {

	public static void main(String[] args) {
		IntegerReversal main = new IntegerReversal();

		System.out.println(main.reverse(123));
	}

	public Integer reverse(Integer i) {
		int rev = 0;

		while (i > 0) {
			int last = i % 10;
			i = i / 10;
			rev = (rev * 10) + last;
			// System.out.println(last);
		}
		return rev;
	}

}
