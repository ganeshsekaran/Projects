package com.ganesh;

import java.util.Stack;

public class PostfixNotation {

	public static void main(String[] args) {
		PostfixNotation main = new PostfixNotation();
		String notation = "423+*";
		int output = main.evaluate(notation);
		System.out.println(output);
	}

	public int evaluate(String notation) {
		int value = 0;

		if (notation == null || notation.isEmpty()) {
			return value;
		}

		Stack<Integer> stack = new Stack<Integer>();
		int length = notation.length();

		for (int i = 0; i < length; i++) {
			char c = notation.charAt(i);
			if (isNumeric(notation.charAt(i)))
				stack.push(Character.getNumericValue(c));
			else {
				value = perform(stack.pop(), stack.pop(), c);
				stack.push(value);
			}
		}
		return value;
	}

	private boolean isNumeric(char character) {
		return character >= '0' && character <= '9';
	}

	private int perform(int poped1, int poped2, char operation) {
		int result;
		switch (operation) {
		case '+':
			result = poped2 + poped1;
			break;
		case '-':
			result = poped2 - poped1;
			break;
		case '*':
			result = poped2 * poped1;
			break;
		case '/':
			result = poped2 / poped1;
			break;
		default:
			throw new RuntimeException("Invalid");
		}
		return result;
	}
}
