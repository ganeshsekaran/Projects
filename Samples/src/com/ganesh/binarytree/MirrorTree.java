package com.ganesh.binarytree;

public class MirrorTree {

	public void mirror(TreeNode head) {

		if (head == null) {
			return;
		}

		TreeNode left = head.left;
		TreeNode right = head.right;

		mirror(left);
		mirror(right);

		head.left = right;
		head.right = left;
	}

}
