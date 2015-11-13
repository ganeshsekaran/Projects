package com.ganesh.linklist;

/**
 * User: Ganesh
 */
public class Node {
	private int data;
	private Node next;
	private Node prev;

	public Node(int data) {
		super();
		setData(data);
	}

	public void setData(int data) {
		this.data = data;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public int getData() {
		return data;
	}

	public Node getNext() {
		return next;
	}

	public Node getPrev() {
		return prev;
	}

	public void display() {
		display(false);
	}

	public boolean equalsNode(Node node) {
		boolean equals = equals(node);

		if (!equals && node != null) {
			equals = getData() == node.getData();
		}

		return equals;
	}

	public void display(boolean isDoubleLinkList) {
		StringBuffer buff = new StringBuffer(6);
		buff.append("Data : ");
		buff.append(getData());
		buff.append("\n");

		if (isDoubleLinkList) {
			buff.append("Previous : ");
			buff.append(getPrev() == null ? "null" : getPrev().getData());
			buff.append("\n");
		}

		buff.append("Next : ");
		buff.append(getNext() == null ? "null" : getNext().getData());
		buff.append("\n");

		System.out.println(buff.toString());
	}
}
