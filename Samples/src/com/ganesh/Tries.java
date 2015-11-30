package com.ganesh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tries {

	private final TrieNode root;
	private final String wordSperator = " ";

	public Tries() {
		root = new TrieNode(' ');
	}

	public static void main(String[] args) throws Exception {
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		Tries main = new Tries();

		main.insert("Ganesh Sekaran");
		System.out.println(main.search("Ganesh"));
		System.out.println(main.search("Sekaran"));
		System.out.println(main.search("Ganesh Sekaran"));

		main.insert("Jatin");
		main.insert("Jack");
		System.out.println(main.search("Jatin"));
		System.out.println(main.search("Jack"));

		List<String> words = main.getAllWords();
		for (String word : words) {
			System.out.println(word);
		}

		for (int i = 0; i < 100; i++) {
			// main.insert("Ganesh" + i);
		}
		for (int i = 0; i < 100; i++) {
			// main.remove("Ganesh" + i);
			// boolean search = main.search("Ganesh" + i);
			// System.out.println(search);
		}
	}

	public void insert(String word) {
		String[] words = word.split(wordSperator);
		if (words.length > 1) {
			for (String w : words) {
				processInsert(w);
			}
			processInsert(word);
		} else {
			processInsert(word);
		}
	}

	private void processInsert(String word) {
		if (search(word)) {
			return;
		}
		TrieNode current = root;
		for (char ch : word.toCharArray()) {
			TrieNode child = current.subNode(ch);
			if (child != null) {
				current = child;
			} else {
				TrieNode newChild = new TrieNode(ch, current);
				if (String.valueOf(ch) == wordSperator) {
					newChild.isWordSeperator = true;
				}
				current.subList.add(newChild);
				current = newChild;
			}
			current.count++;
		}
		current.isEnd = true;
	}

	public void remove(String word) {
		if (search(word) == false) {
			System.out.println(word + " does not exist in trie\n");
			return;
		}
		TrieNode current = root;
		for (char ch : word.toCharArray()) {
			TrieNode child = current.subNode(ch);
			if (child.count == 1) {
				current.subList.remove(child);
				return;
			} else {
				child.count--;
				current = child;
			}
		}
		current.isEnd = false;
	}

	public List<String> getAllWords() {
		List<String> words = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		getAllWords(words, root, buffer);
		return words;
	}

	public boolean search(String word) {
		TrieNode current = root;
		for (char ch : word.toCharArray()) {
			if (current.subNode(ch) == null)
				return false;
			else
				current = current.subNode(ch);
		}
		return current.isEnd;
	}

	private void getAllWords(List<String> list, TrieNode node,
			StringBuffer buffer) {
		if (node == null) {
			buffer.setLength(0);
			return;
		}

		if (!node.isRoot) {
			buffer.append(node.content);
		}

		if (node.isEnd) {
			list.add(buffer.toString());
			// buffer.setLength(0);
		}

		List<TrieNode> nodes = node.subList;
		for (TrieNode childNode : nodes) {
			getAllWords(list, childNode, buffer);
			if(childNode.parent == null){
				buffer.setLength(0);
			}
		}
	}

	class TrieNode {
		final char content;
		final boolean isRoot;
		List<TrieNode> subList;
		boolean isEnd;
		int count;
		boolean isWordSeperator;
		TrieNode parent;

		TrieNode(char content) {
			this(content, null);
		}

		TrieNode(char content, TrieNode parent) {
			this.content = Character.toLowerCase(content);
			subList = new ArrayList<TrieNode>();
			isEnd = false;
			if (parent == null) {
				this.isRoot = true;
			} else {
				this.isRoot = false;
			}

			count = 0;
		}

		TrieNode subNode(char c) {
			TrieNode child = null;
			for (TrieNode eachChild : subList) {
				if (eachChild.content == Character.toLowerCase(c)) {
					child = eachChild;
					break;
				}
			}
			return child;
		}
	}
}
