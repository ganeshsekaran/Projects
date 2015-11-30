package com.ganesh;

import java.io.BufferedReader;
import java.io.FileReader;

public class DocumentWordMatching {

	private final Tries trie;

	public DocumentWordMatching() {
		trie = new Tries();
	}

	public static void main(String[] args) throws Exception {
		DocumentWordMatching main = new DocumentWordMatching();
		
		//main.addWord("Ganesh");
		main.addWord("Jatin");
		main.addWord("Rajan");
		main.addWord("Anto");
		main.addWord("Ganesh Sekaran");
		
		main.search("C:\\Users\\ganeshs\\Desktop\\sample.txt");
	}

	private void search(String uri) throws Exception {
		FileReader fr = new FileReader(uri);
		BufferedReader br = new BufferedReader(fr);
		search(br);
	}

	private void search(BufferedReader br) throws Exception {
		try {
			int index = 0;
			int wordStartIndex = 0;
			StringBuffer buffer = new StringBuffer();
			String line = br.readLine();

			while (line != null) {
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					index++;
					if (c == ' ') {
						if (isAvailable(buffer.toString())) {
							System.out.println(buffer + " <" + wordStartIndex
									+ "> " + "----" + " <" + (index-1) + "> ");
						}
						wordStartIndex = index;
						buffer.setLength(0);
					}else if(i == line.length()-1) {
						buffer.append(c);
						if (isAvailable(buffer.toString())) {
							System.out.println(buffer + " <" + wordStartIndex
									+ "> " + "----" + " <" + (index) + "> ");
							wordStartIndex = index;
							buffer.setLength(0);
						}
					}else {
						buffer.append(c);
					}
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
	}

	public void addWord(String word) {
		trie.insert(word);
	}

	private boolean isAvailable(String word) {
		return trie.search(word);
	}

}
