package com.ganesh;

public class StringPermutation {
	static int count =0;

	public static void main(String[] args) {
		StringPermutation main = new StringPermutation();
		main.findPermutations("abc");
		
		Integer i1 = 100;
		Integer i2 = 100;
		
		System.out.println(i1 == i2);
		
		Integer i3 = new Integer(100);
		Integer i4 = new Integer(100);
		
		System.out.println(i3 == i4);
	}

	public void findPermutations(String str) {
		permutate("", str);
	}

	private void permutate(String prefix, String str) {
		System.out.println(prefix+","+str+", len --> "+str.length());
		if (str.isEmpty()) {
			System.out.println(prefix);
		} else {
			int len = str.length();

			for (int i = 0; i < len; i++) {
				System.out.println("Count --> "+(++count)+", i --> "+i);
				permutate(prefix + str.charAt(i),
						str.substring(0, i) + str.substring(i + 1, len));
			}
		}
	}
}
