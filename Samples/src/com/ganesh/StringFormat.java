package com.ganesh;

import java.util.Stack;

public class StringFormat {

	public static void main(String[] args) {
		StringFormat main = new StringFormat();

		String str = "Jack and Jill {0} up the {1} \\to {{{2}}} a pale of {3} Jack \\{4} fell {5}";
		String[] replace = new String[] { "went", "hill", "fetch", "water",
				"down" };
		String output = main.format(str, replace);
		System.out.println(output);
	}

	public String format(String str, String[] replace) {
		StringBuffer output = new StringBuffer();
		Stack<Character> stack = new Stack<Character>();
		boolean isEscaped = false;

		int len = str == null ? 0 : str.length();
		for (int i = 0; i < len; i++) {
			char charater = str.charAt(i);
			String charStr = String.valueOf(charater);

			if (charStr.equals("\\")) {
				isEscaped = !isEscaped;
				continue;
			}

			if (isEscaped) {
				isEscaped = !isEscaped;
				output.append(charater);
				continue;
			}

			if (charStr.equals("{")) {
				if (stack.size() > 0) {
					output.append(charStr);
				} else {
					stack.push(charater);
				}
				continue;
			}

			if (isInteger(charStr)) {
				if (stack.size() > 0) {
					char peaked = stack.peek();
					int index = Integer.parseInt(charStr);
					if (String.valueOf(peaked).equals("{")) {
						if (index >= replace.length) {
							for (int j = 0; j < stack.size(); j++) {
								output.append(stack.pop());
							}
							output.append(charStr);
						} else {
							output.append(replace[index]);
						}
					} else {
						output.append(charater);
					}
				} else {
					output.append(charater);
				}
				continue;
			}

			if (charStr.equals("}")) {
				if (stack.size() > 0) {
					char peaked = stack.peek();
					if (String.valueOf(peaked).equals("{")) {
						stack.pop();
					} else {
						output.append(charater);
					}
				} else {
					output.append(charater);
				}
				continue;
			}
			output.append(charater);
		}

		return output.toString();
	}

	private boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
}
