package com.ganesh.binarytree;

import java.util.HashMap;
import java.util.Map;

public class HorizontalSum {
	
	public Map<Integer, Integer> sum(TreeNode node) {
		Map<Integer, Integer> levelSum = new HashMap<Integer, Integer>();
		sum(node, levelSum, 0);
		return levelSum;
	}

	private void sum(TreeNode node, Map<Integer, Integer> map, int level) {
		if (node != null) {
			Integer data = map.get(level);
			data = data == null ? 0 : data;
			data = data + node.data;
			
			map.put(level, data);
			sum(node.left, map, level + 1);
			sum(node.right, map, level + 1);
		}
	}
}
