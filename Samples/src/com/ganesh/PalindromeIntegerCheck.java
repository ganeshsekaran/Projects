package com.ganesh;

public class PalindromeIntegerCheck {

	public static void main(String args[]) {
		Integer number = 1234321;
		PalindromeIntegerCheck check = new PalindromeIntegerCheck();
		System.out.println(check.checkPalindrome(number));
	}

	public boolean checkPalindrome(final Integer number, final int length) {
		System.out.println(number);
		boolean isPalindrome = false;
		if (length > 0) {
			Integer n = number;
			int firstDigit = n / getDiveder(length);
			int lastDigit = n % 10;
			System.out.println("First : " + firstDigit);
			System.out.println("Last : " + lastDigit);
			if (firstDigit == lastDigit) {
				n = removeFisrtAndLastDigits(n);
				isPalindrome = checkPalindrome(n);
			}
		}
		return isPalindrome;
	}

	public boolean checkPalindrome(final Integer number) {
		return checkPalindrome(number, numberOfDigits(number));
	}

	private Integer removeFisrtAndLastDigits(final Integer number) {
		Integer modified = number;
		int length = numberOfDigits(modified);
		modified = modified % getDiveder(length);
		modified = modified / 10;
		return modified;
	}

	private int getDiveder(int length) {
		StringBuffer divider = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				divider.append(1);
			} else {
				divider.append(0);
			}
		}
		return new Integer(divider.toString());
	}

	private int numberOfDigits(final Integer number) {
		int i = 0;
		Integer n = number;
		while (n > 0) {
			n = n / 10;
			i++;
		}
		return i;
	}
}
