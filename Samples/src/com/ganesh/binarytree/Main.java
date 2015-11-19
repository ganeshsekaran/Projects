package com.ganesh.binarytree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.Set;

import com.ganesh.binarytree.PrintTraversal.TraversalType;
import com.ganesh.linklist.LinkList;
import com.ganesh.linklist.LinkList.LinkListRandomOption;
import com.ganesh.linklist.LinkListToBST;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		// main.test();
		//main.test2();
		main.mirror();
	}
	
	private void mirror(){
		TreeNode head = createTree();
		PrintTraversal traversal = new PrintTraversal();
	//	traversal.traversal(head, TraversalType.IN_ORDER);
		
		traversal.traversal(head, TraversalType.TREE_VERTICAL);
		
		traversal.traversal(head, TraversalType.SPIRAL_HORIZONTAL);
		
		
		MirrorTree mirror = new MirrorTree();
		//mirror.mirror(head);
		//traversal.traversal(head, TraversalType.IN_ORDER);
	}

	private void test() {
		LinkList list = new LinkList();
		list.generateRandomLinkList(10, LinkListRandomOption.RANDOM_UNIQUE);
		list.sort();
		System.out.println(list.toString());

		LinkListToBST toBST = new LinkListToBST();
		TreeNode treeNode = toBST.toBST(list);

		PrintTraversal traversal = new PrintTraversal();
		// traversal.traversal(treeNode, TraversalType.PRE_ORDER);
		traversal.traversal(treeNode, TraversalType.IN_ORDER);
		// traversal.traversal(treeNode, TraversalType.POST_ORDER);

		HorizontalSum horiSum = new HorizontalSum();
		Map<Integer, Integer> sums = horiSum.sum(treeNode);
		Set set = sums.entrySet();
		Object[] data = set.toArray();
		System.out.println();
		for (Object sum : data) {
			System.out.println(sum);
		}

		/*
		 * VerticalSum veriSum = new VerticalSum(); sums =
		 * veriSum.sum(treeNode); set = sums.entrySet(); data = set.toArray();
		 * System.out.println(); for(Object sum : data){
		 * System.out.println(sum); }
		 */
	}

	private void test2() {
		TreeNode head = createTree();

		PrintTraversal traversal = new PrintTraversal();
		traversal.traversal(head, TraversalType.IN_ORDER);

		SprialTraversal sprTraversal = new SprialTraversal();
		Map<Integer, Collection<Integer>> map = sprTraversal.traversal(head);
		Set<Entry<Integer, Collection<Integer>>> set = map.entrySet();
		Iterator<Entry<Integer, Collection<Integer>>> itr = set.iterator();

		System.out.println();
		while (itr.hasNext()) {
			Entry<Integer, Collection<Integer>> entry = itr.next();
			System.out.print("Level : " + entry.getKey());
			System.out.print("  Values : ");
			Collection<Integer> values = entry.getValue();

			if (values instanceof ArrayList) {

				for (Integer value : values) {
					System.out.print(" " + value);
				}
			} else if (values instanceof Stack) {
				Stack<Integer> stackValues = (Stack<Integer>) values;
				
				int size = stackValues.size();
				for(int i=0;i<size;i++){
					System.out.print(" " + stackValues.pop());
				}
			}

			System.out.println();
		}
	}

	private TreeNode createTree() {
		TreeNode head = new TreeNode();
		head.data = 0;

		TreeNode node1 = new TreeNode();
		node1.data = 1;
		TreeNode node2 = new TreeNode();
		node2.data = 2;
		head.left = node1;
		head.right = node2;

		TreeNode node3 = new TreeNode();
		node3.data = 3;
		TreeNode node4 = new TreeNode();
		node4.data = 4;
		node1.left = node3;
		node1.right = node4;

		TreeNode node5 = new TreeNode();
		node5.data = 5;
		TreeNode node6 = new TreeNode();
		node6.data = 6;
		node2.left = node5;
		node2.right = node6;
		return head;
	}
}
