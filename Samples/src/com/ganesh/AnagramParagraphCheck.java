package com.ganesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class AnagramParagraphCheck {

	public static void main(String[] args) {
		AnagramParagraphCheck main = new AnagramParagraphCheck();
		String paragraph = "dog god dad dag boy yob dda abc aab acc cba dda baa efg eef gfe tata aatta aat taat";
		HashMap<Integer, AnagramBucket> map = main.getAnagrams(paragraph);
		Set<Entry<Integer, AnagramBucket>> set = map.entrySet();
		for (Entry<Integer, AnagramBucket> entry : set) {
			AnagramBucket bucket = entry.getValue();
			if (bucket.size() > 1) {
				bucket.printStrings();
				System.out.println();
			}
		}
	}

	public HashMap<Integer, AnagramBucket> getAnagrams(String paragraph) {
		String[] strArray = paragraph.split(" ");
		HashMap<Integer, AnagramBucket> map = new HashMap<Integer, AnagramBucket>();
		for (String str : strArray) {
			String sorted = sort(str);
			int hash = sorted.hashCode();
			AnagramBucket bucket = map.get(hash);
			if (bucket == null) {
				bucket = new AnagramBucket(hash);
				map.put(hash, bucket);
			}
			bucket.addToBucket(str);
		}
		return map;
	}

	private String sort(String str) {
		char[] chars = str.toCharArray();
		Arrays.sort(chars);
		return String.valueOf(chars);
	}

	class AnagramBucket {
		final int hashCode;
		final ArrayList<String> list;

		AnagramBucket(int hashCode) {
			this.list = new ArrayList<String>();
			this.hashCode = hashCode;
		}

		int getHashCode() {
			return hashCode;
		}

		void addToBucket(String str) {
			list.add(str);
		}

		int size() {
			return list.size();
		}

		String[] getStrings() {
			String[] strings = new String[list.size()];
			int i = 0;
			for (String str : list) {
				strings[i++] = str;
			}
			return strings;
		}

		void printStrings() {
			for (String str : list) {
				System.out.println(str);
			}
		}
	}
}
