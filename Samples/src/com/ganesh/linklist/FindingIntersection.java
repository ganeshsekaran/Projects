package com.ganesh.linklist;

import com.ganesh.linklist.LinkList.LinkListRandomOption;

public class FindingIntersection {

	public static void main(String[] args) {
		FindingIntersection main = new FindingIntersection();

		LinkList list1 = new LinkList();
		list1.generateRandomLinkList(10, LinkListRandomOption.RANDOM_UNIQUE);
		System.out.println(list1.toString());

		LinkList list2 = new LinkList();
		list2.generateRandomLinkList(4, LinkListRandomOption.RANDOM_UNIQUE);
		System.out.println(list2.toString());

		Node node = list2.getLinkList();
		
		Node last = main.getLastNode(node);
		last.display();
		
		
		node = list1.getLinkList();
		
		while(node != null){
			if(last.equalsNode(node)){
				break;
			}else{
				node = node.getNext();
			}
		}
		
		node.display();

	}

	private Node getLastNode(Node node) {
		if (node == null) {
			return null;
		}

		if (node.getNext() == null) {
			return node;
		}

		return getLastNode(node.getNext());
	}

	public Node getIntersection(LinkList list1, LinkList list2) {

		Node intersectionNode = null;

		return intersectionNode;

	}

}
