package com.ganesh.binarytree;

import java.util.Map;
import java.util.Set;

import com.ganesh.binarytree.PrintTraversal.TraversalType;
import com.ganesh.linklist.LinkList;
import com.ganesh.linklist.LinkList.LinkListRandomOption;
import com.ganesh.linklist.LinkListToBST;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.test();
	}
	
	private void test(){
		LinkList list = new LinkList();
		list.generateRandomLinkList(10, LinkListRandomOption.RANDOM_UNIQUE);
		list.sort();
		System.out.println(list.toString());
		
		LinkListToBST toBST = new LinkListToBST();
		TreeNode treeNode = toBST.toBST(list);
		
		PrintTraversal traversal = new PrintTraversal();
		//traversal.traversal(treeNode, TraversalType.PRE_ORDER);
		traversal.traversal(treeNode, TraversalType.IN_ORDER);
		//traversal.traversal(treeNode, TraversalType.POST_ORDER);
		
		HorizontalSum horiSum = new HorizontalSum();
		Map<Integer, Integer> sums = horiSum.sum(treeNode);
		Set set = sums.entrySet();
		Object[] data = set.toArray();
		for(Object sum : data){
			System.out.println(sum);
		}
	}
}
