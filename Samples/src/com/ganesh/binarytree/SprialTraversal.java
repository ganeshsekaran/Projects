package com.ganesh.binarytree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class SprialTraversal {

	public Map<Integer, Collection<Integer>> traversal(TreeNode head) {
		Map<Integer, Collection<Integer>> traversal = new LinkedHashMap<Integer, Collection<Integer>>();
		traversal(head, traversal, 0);
		return traversal;
	}

	private void traversal(TreeNode node,
			Map<Integer, Collection<Integer>> map, int level) {
		if (node != null) {
			if (level % 2 != 0) {
				Collection<Integer> list = map.get(level);
				if (list == null) {
					list = new ArrayList<Integer>();
					map.put(level, list);
				}
				list.add(node.data);

				traversal(node.left, map, level + 1);
				traversal(node.right, map, level + 1);
			} else {

				Stack<Integer> stack = (Stack<Integer>) map.get(level);
				if (stack == null) {
					stack = new Stack<Integer>();
					map.put(level, stack);
				}
				stack.push(node.data);
				traversal(node.left, map, level + 1);
				traversal(node.right, map, level + 1);
			}
		}
	}
}
