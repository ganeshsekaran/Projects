package com.ganesh.linklist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * User: Ganesh
 */
public class LinkList {
	private Node head;
	private Node tail;
	private int count;
	private boolean isDoubleLinkList;

	/**
	 * enum for link list type.
	 */
	public enum LinkListType {
		TYPE_SINGLE_LIST(8), TYPE_DOUBLE_LIST(12);
		final int size;
		LinkListType(int size){
			this.size = size;
		}
		
		public int getSize(){
			return size;
		}
	}

	/**
	 * enum for link list find option.
	 */
	public enum LinkListFindOption {
		FIND_ALL, FIND_FIRST
	}

	/**
	 * enum for link list random generation option
	 */
	public enum LinkListRandomOption {
		RANDOM, RANDOM_UNIQUE
	}

	/**
	 * Constructor with option for double link list
	 *
	 * @param type
	 *            LinkListType
	 */
	public LinkList(LinkListType type) {
		super();
		if (LinkListType.TYPE_DOUBLE_LIST.equals(type)) {
			this.isDoubleLinkList = true;
		}
	}

	/**
	 * Default constructor.
	 */
	public LinkList() {
		this(LinkListType.TYPE_SINGLE_LIST);
	}

	private LinkListType getLinkListType() {
		LinkListType type = LinkListType.TYPE_SINGLE_LIST;

		if (isDoubleLinkList()) {
			type = LinkListType.TYPE_DOUBLE_LIST;
		}
		return type;
	}

	/**
	 * Clones the existing link list and gives new object of link list.
	 *
	 * @return LinkList
	 */
	public synchronized LinkList getClonedCopy() {
		LinkList newList = new LinkList(getLinkListType());
		List<Integer> items = toArrayList();

		newList.toLinkList(items);
		return newList;
	}

	/**
	 * Gets the link list.
	 *
	 * @return Node
	 */
	public synchronized Node getLinkList() {
		return head;
	}

	/**
	 * Gets if the link list is double link list.
	 *
	 * @return boolean
	 */
	public synchronized boolean isDoubleLinkList() {
		return isDoubleLinkList;
	}

	/**
	 * Adds a data to link list at front.
	 *
	 * @param data
	 *            int
	 */
	public synchronized void addFirst(int data) {
		Node tmp = new Node(data);
		tmp.setNext(head);

		if (head == null) {
			tail = tmp;
		}

		Node tmp2 = head;
		head = tmp;

		if (isDoubleLinkList() && tmp2 != null) {
			tmp2.setPrev(head);
		}

		count++;
	}

	/**
	 * Searches a Data from the link list.
	 *
	 * @param data
	 *            int
	 * @return boolean
	 */
	public synchronized Node find(int data) {
		return traversal(data, false);
	}

	public synchronized List<Node> findAsList(int data) {
		return findAsList(data, LinkListFindOption.FIND_ALL);
	}

	public synchronized List<Node> findAsList(int data,
			LinkListFindOption option) {
		boolean found = false;
		Node tmp = head;
		List<Node> list = new ArrayList<Node>();

		while (tmp != null
				&& (!found || LinkListFindOption.FIND_ALL.equals(option))) {
			if (tmp.getData() == data) {
				list.add(tmp);
				found = true;
			}

			tmp = tmp.getNext();
		}

		return list;
	}

	/**
	 * Private method to traversal the link list and get data.
	 *
	 * @param data
	 *            int
	 * @param isDelete
	 *            boolean
	 * @return Node
	 */
	private synchronized Node traversal(int data, boolean isDelete) {
		boolean found = false;
		Node tmp = head;
		Node tmpPrev = null;

		while (tmp != null && !found) {
			int tmpData = tmp.getData();
			if (tmpData == data) {
				if (isDelete) {
					if (tmpPrev == null) {
						head = tmp.getNext();

						if (isDoubleLinkList()) {
							head.setPrev(null);
						}
					} else {
						tmpPrev.setNext(tmp.getNext());

						if (tmp.getNext() != null && isDoubleLinkList()) {
							tmp.getNext().setPrev(tmpPrev);
						}

						if (tmp.getNext() == null) {
							tail = tmpPrev;
						}
					}

					count--;
				}
				found = true;
			}
			tmpPrev = tmp;
			tmp = tmp.getNext();
		}

		return (found ? tmpPrev : tmp);
	}

	/**
	 * Delete the data from the link list
	 *
	 * @param data
	 *            int
	 */
	public synchronized void delete(int data) {
		traversal(data, true);
	}

	/**
	 * Adds a data to link list at back.
	 *
	 * @param data
	 *            int
	 */
	public synchronized void addLast(int data) {
		Node tmp = new Node(data);

		if (tail != null) {
			tail.setNext(tmp);
		} else {
			head = tmp;
		}

		Node tmp2 = tail;
		tail = tmp;

		if (isDoubleLinkList() && tmp2 != null) {
			tmp.setPrev(tmp2);
		}

		count++;
	}

	/**
	 * Gets the size of the link list
	 *
	 * @return int
	 */
	public synchronized int getSize() {
		return count;
	}

	/**
	 * Deletes the first data from link list.
	 */
	public synchronized void deleteFirst() {
		if (head != null) {
			head = head.getNext();

			if (head == null) {
				tail = head;
			}

			if (head != null && isDoubleLinkList()) {
				head.setPrev(null);
			}
			count--;
		}
	}

	/**
	 * Deletes the last data from the link list.
	 */
	public synchronized void deleteLast() {
		if (tail != null) {
			Node tmp;
			if (isDoubleLinkList()) {
				tmp = tail.getPrev();
				tail.setPrev(null);

				if (tmp != null) {
					tmp.setNext(null);
					tail = tmp;
				} else {
					tail = tmp;
					head = tmp;
				}
			} else {
				tmp = head;
				int size = getSize();

				if (size == 1) {
					head = null;
					tail = null;
				}

				for (int i = 0; i < size - 2; i++) {
					tmp = tmp.getNext();
				}

				tmp.setNext(null);
				tail = tmp;
			}

			count--;
		}
	}

	/**
	 * Displays the link list
	 */
	public synchronized void displayList() {
		if (head != null) {
			Node tmp = head;

			while (tmp != null) {
				display(tmp);
				tmp = tmp.getNext();
			}
		} else {
			System.out.println("The list is empty");
		}
	}

	/**
	 * Private method to display the node.
	 *
	 * @param node
	 *            node
	 */
	private synchronized void display(Node node) {
		if (node != null) {
			node.display(isDoubleLinkList());
		}
	}

	/**
	 * Reverses the link list.
	 */
	public synchronized void reverseList() {
		Node tmpHead = getClonedCopy().getLinkList();

		try {
			reverseList(head, null);
		} catch (StackOverflowError e) {
			head = tmpHead;
			reverseListIterative();
		}
	}

	/**
	 * Reverses the link list in blocks. TODO should implement this logic.
	 *
	 * @param blockSize
	 *            int
	 */
	public synchronized void reverseList(int blockSize) {
		if (isDoubleLinkList()) {
			System.out
					.println("Reversal of Double Link list it not implemented");
		} else {
			reverseList(head, null, blockSize);
		}
	}

	/**
	 * Private helper method to reverse the list in blocks.
	 *
	 * @param current
	 *            Node
	 * @param previous
	 *            Node
	 * @param blockSize
	 *            int
	 */
	private synchronized void reverseList(Node current, Node previous,
			int blockSize) {
		if (current != null) {
			for (int i = 0; i < blockSize - 1; i++) {
				if (current.getNext() != null) {
					current = current.getNext();
				}
			}

			Node tmpNext = current.getNext();
			current.setNext(previous);
			head = current;

			reverseList(tmpNext, current, blockSize);
		}
	}

	/**
	 * Private helper method to reverse the list.
	 *
	 * @param current
	 *            node
	 * @param previous
	 *            node
	 */
	private synchronized void reverseList(Node current, Node previous) {
		if (previous == null) {
			tail = current;
			if (isDoubleLinkList() && current != null) {
				tail.setPrev(current.getNext());
			}
		}

		if (current != null) {
			Node tmpNext = current.getNext();

			current.setNext(previous);

			if (isDoubleLinkList()) {
				current.setPrev(tmpNext);
			}
			head = current;

			reverseList(tmpNext, current);
		}
	}

	/**
	 * Private method to reverse the list iteratively.
	 */
	private synchronized void reverseListIterative() {
		List<Integer> items = toArrayList();
		Collections.reverse(items);

		toLinkList(items);
	}

	/**
	 * To make the link list empty.
	 */
	public synchronized void reset() {
		head = null;
		tail = null;
		count = 0;
	}

	/**
	 * Converts the list to array list.
	 *
	 * @return List<Integer>
	 */
	public synchronized List<Integer> toArrayList() {
		List<Integer> array = new ArrayList<Integer>();
		Node tmp = head;

		while (tmp != null) {
			array.add(tmp.getData());
			tmp = tmp.getNext();
		}

		return array;
	}

	/**
	 * Converts the list to a array.
	 *
	 * @return int[]
	 */
	public synchronized int[] toArray() {
		int array[] = new int[getSize()];
		List<Integer> list = toArrayList();

		int i = 0;
		for (Integer data : list) {
			array[i] = data;
			i++;
		}

		return array;
	}

	/**
	 * Converts the array to a link list.
	 *
	 * @param list
	 *            List<Integer>
	 */
	public synchronized void toLinkList(List<Integer> list) {
		reset();

		if (list.size() > 0) {
			for (Integer data : list) {
				addLast(data);
			}
		}
	}

	/**
	 * Converts the link list to double link list.
	 */
	public synchronized void toDoubleLinkList() {
		if (!isDoubleLinkList()) {
			Node current = head;
			Node previous = null;

			while (current != null) {
				current.setPrev(previous);
				previous = current;
				current = current.getNext();
			}

			isDoubleLinkList = true;
		}
	}

	/**
	 * Converts the double link list to single link list.
	 */
	public synchronized void toSingleLinkList() {
		if (isDoubleLinkList()) {
			Node current = head;
			while (current != null) {
				current.setPrev(null);
				current = current.getNext();
			}

			isDoubleLinkList = false;
		}
	}

	/**
	 * Compares the two link list along with data.
	 *
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public synchronized boolean equals(Object obj) {
		boolean isEqual = false;

		if (this == obj) {
			isEqual = true;
		}

		if (obj instanceof LinkList && !isEqual) {
			isEqual = compare((LinkList) obj);
		}

		return isEqual;
	}

	/**
	 * Helper method to compare the two link list along with data.
	 *
	 * @param obj
	 *            LinkList
	 * @return boolean
	 */
	private synchronized boolean compare(LinkList obj) {
		boolean isEqual = true;

		if (this.getSize() == obj.getSize()) {
			Node tmp = this.getLinkList();
			Node tmp2 = obj.getLinkList();

			while (tmp != null && tmp2 != null && isEqual) {
				if (tmp2.getData() != tmp.getData()) {
					isEqual = false;
				}

				tmp = tmp.getNext();
				tmp2 = tmp2.getNext();
			}
		} else {
			isEqual = false;
		}

		return isEqual;
	}

	/**
	 * Method to generate a random link list for the given size;
	 *
	 * @param size
	 *            int
	 */
	public synchronized void generateRandomLinkList(int size) {
		generateRandomLinkList(size, LinkListRandomOption.RANDOM);
	}

	/**
	 * Method to generate a random link list for the given size;
	 *
	 * @param size
	 *            int
	 * @param option
	 *            LinkListRandomOption
	 */
	public synchronized void generateRandomLinkList(int size,
			LinkListRandomOption option) {
		reset();
		Random randomGenerator = new Random();

		if (LinkListRandomOption.RANDOM.equals(option)) {
			int j = size + 100;

			for (int i = 0; i < size; i++) {
				int data = randomGenerator.nextInt(j);
				addLast(data);
			}
		} else {
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < size; i++) {
				list.add(i + 1);
			}

			Collections.shuffle(list);
			toLinkList(list);
		}
	}
}