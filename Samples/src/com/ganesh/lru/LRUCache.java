package com.ganesh.lru;

import java.util.HashMap;

public class LRUCache<K, V> {

	private final int capacity;
	private final LRULinkList linkList;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		this.linkList = new LRULinkList();
	}

	class LRULinkList {

		private final HashMap<K, Node<K, V>> map;
		private Node<K, V> head;
		private Node<K, V> tail;

		LRULinkList() {
			map = new HashMap<K, Node<K, V>>();
			head = null;
			tail = null;
		}

		void add(K key, V value) {
			if(map.containsKey(key)){
				Node<K,V> old = map.get(key);
				old.setValue(value);
			}else{
				Node<K, V> n = new Node<K, V>();
				n.setKey(key);
				n.setValue(value);
			}
		}
	}

	class Node<K, V> {

		private K key;
		private V value;
		private Node<K, V> next;
		private Node<K, V> previous;

		Node() {
			super();
			// TODO Auto-generated constructor stub
		}

		Node(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		K getKey() {
			return key;
		}

		void setKey(K key) {
			this.key = key;
		}

		V getValue() {
			return value;
		}

		void setValue(V value) {
			this.value = value;
		}

		Node<K, V> getNext() {
			return next;
		}

		void setNext(Node<K, V> next) {
			this.next = next;
		}

		Node<K, V> getPrevious() {
			return previous;
		}

		void setPrevious(Node<K, V> previous) {
			this.previous = previous;
		}
	}
}
