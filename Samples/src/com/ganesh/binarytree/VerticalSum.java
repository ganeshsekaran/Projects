package com.ganesh.binarytree;

import java.util.ArrayList;
import java.util.List;

public class VerticalSum {

	public List<Integer> sum(TreeNode node) {
		List<Integer> levelSum = new ArrayList<Integer>();
		sum(node, levelSum, 0);
		return levelSum;
	}

	private void sum(TreeNode node, List<Integer> list, int level) {
		if (node != null) {
			int data = list.get(level);
			data = data + node.data;
			list.add(level, data);
			sum(node.left, list, level + 1);
			sum(node.right, list, level + 1);
		}
	}
}
