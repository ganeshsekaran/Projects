package com.ganesh;

public class LinklistToBST {

	public TreeNode sortedListToBST(ListNode head) {
		if (head == null)
			return null;

		int len = getLength(head);
		return sortedListToBST(head, 0, len - 1);
	}

	// get list length
	private int getLength(ListNode head) {
		int len = 0;
		ListNode p = head;

		while (p != null) {
			len++;
			p = p.next;
		}
		return len;
	}

	// build tree bottom-up
	private TreeNode sortedListToBST(ListNode listNode, int lowIndex,
			int highIndex) {
		TreeNode root = null;
		if (lowIndex <= highIndex) {
			// mid
			int mid = (lowIndex + highIndex) / 2;

			TreeNode left = sortedListToBST(listNode, lowIndex, mid - 1);
			root = new TreeNode(listNode.val);
			listNode = listNode.next;
			TreeNode right = sortedListToBST(listNode, mid + 1, highIndex);

			root.left = left;
			root.right = right;
		}
		return root;
	}

	class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
}
