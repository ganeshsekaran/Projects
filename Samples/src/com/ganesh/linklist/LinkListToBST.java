package com.ganesh.linklist;

import com.ganesh.binarytree.TreeNode;

public class LinkListToBST {
	
	Node h;

	public TreeNode toBST(LinkList linkList) {
		if (linkList == null) {
			return null;
		}
		int len = linkList.getSize();
		System.out.println("Len : " + len);

		if (len < 1) {
			return null;
		}
		Node node = linkList.getLinkList();
		h = node;
		return convert(node, 0, len-1);
	}

	private TreeNode convert(Node node, int lowIndex, int highIndex) {
		TreeNode treeNode = null;
		if (lowIndex <= highIndex) {
			int mid = (lowIndex + highIndex) / 2;
			//System.out.println(mid);
			TreeNode left = convert(node, lowIndex, mid - 1);
			
			treeNode = new TreeNode();
			//treeNode.data = node.getData();
			treeNode.data = h.getData();
			h = h.getNext();
			
			TreeNode right = convert(node, mid + 1, highIndex);
			//node = node.getNext();
			treeNode.left = left;
			treeNode.right = right;
		}
		return treeNode;
	}
}
