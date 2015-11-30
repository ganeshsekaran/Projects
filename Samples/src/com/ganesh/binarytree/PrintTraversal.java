package com.ganesh.binarytree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

public class PrintTraversal {

	public enum TraversalType {
		PRE_ORDER, IN_ORDER, POST_ORDER, TREE_VERTICAL, SPIRAL_HORIZONTAL, SPIRAL_VERTICAL, IN_ORDER_LOOPING, PRE_ORDER_LOOPING, POST_ORDER_LOOPING
	}

	public void traversal(TreeNode treeNode, TraversalType type) {
		if (type == TraversalType.PRE_ORDER) {
			if (treeNode == null) {
				System.out.println("No Data");
			} else {
				System.out.println();
				System.out.println("Pre order traversal");
				preOrder(treeNode);
			}
		} else if (type == TraversalType.IN_ORDER) {
			if (treeNode == null) {
				System.out.println("No Data");
			} else {
				System.out.println();
				System.out.println("In order traversal");
				inOrder(treeNode);
			}
		} else if (type == TraversalType.POST_ORDER) {
			if (treeNode == null) {
				System.out.println("No Data");
			} else {
				System.out.println();
				System.out.println("Post order traversal");
				postOrder(treeNode);
			}
		} else if (type == TraversalType.TREE_VERTICAL) {
			if (treeNode == null) {
				System.out.println("No Data");
			} else {
				System.out.println();
				System.out.println("Printing tree vertical");
				verticalTree(treeNode);
			}
		} else if (type == TraversalType.SPIRAL_HORIZONTAL) {
			if (treeNode == null) {
				System.out.println("No Data");
			} else {
				System.out.println();
				System.out.println("Printing tree Spiral Horizontal");
				horizontalSprial(treeNode);
			}
		} else if (type == TraversalType.IN_ORDER_LOOPING) {
			if (treeNode == null) {
				System.out.println("No Data");
			} else {
				System.out.println();
				System.out.println("In order traversal using loop");
				inOrderWithLoop(treeNode);
			}
		} else if (type == TraversalType.PRE_ORDER_LOOPING) {
			if (treeNode == null) {
				System.out.println("No Data");
			} else {
				System.out.println();
				System.out.println("Pre order traversal using loop");
				preOrderWithLoop(treeNode);
			}
		} else if (type == TraversalType.POST_ORDER_LOOPING) {
			if (treeNode == null) {
				System.out.println("No Data");
			} else {
				System.out.println();
				System.out.println("Post order traversal using loop");
				postOrderWithLoop(treeNode);
			}
		}
	}

	private void preOrder(TreeNode treeNode) {
		if (treeNode != null) {
			System.out.print(treeNode.data + " ");
			preOrder(treeNode.left);
			preOrder(treeNode.right);
		}
	}

	private void preOrderWithLoop(TreeNode treeNode) {
		TreeNode currenNode = treeNode;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(currenNode);

		while (!stack.isEmpty()) {
			while (currenNode != null) {
				System.out.print(currenNode.data + " ");
				currenNode = currenNode.left;
				if (currenNode != null) {
					stack.push(currenNode);
				}
			}

			TreeNode popedNode = stack.pop();
			
			currenNode = popedNode.right;
			if (currenNode != null) {
				stack.push(currenNode);
			}
		}
	}

	private void inOrder(TreeNode treeNode) {
		if (treeNode != null) {
			inOrder(treeNode.left);
			System.out.print(treeNode.data + " ");
			inOrder(treeNode.right);
		}
	}

	private void inOrderWithLoop(TreeNode treeNode) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode currentNode = treeNode;
		stack.push(currentNode);

		while (!stack.isEmpty()) {
			while (currentNode != null) {
				currentNode = currentNode.left;
				if (currentNode != null) {
					stack.push(currentNode);
				}
			}

			TreeNode popedNode = stack.pop();
			System.out.print(popedNode.data + " ");

			currentNode = popedNode.right;
			if (currentNode != null) {
				stack.push(currentNode);
			}
		}
	}

	private void postOrder(TreeNode treeNode) {
		if (treeNode != null) {
			postOrder(treeNode.left);
			postOrder(treeNode.right);
			System.out.print(treeNode.data + " ");
		}
	}

	private void postOrderWithLoop(TreeNode treeNode) {
		TreeNode currenNode = treeNode;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(currenNode);

		while (!stack.isEmpty()) {
			while (currenNode != null) {
				currenNode = currenNode.left;
				if (currenNode != null) {
					stack.push(currenNode);
				}
			}

			//TreeNode popedNode = stack.pop();
			currenNode = stack.pop();
			TreeNode tmp = currenNode;
			currenNode = currenNode.right;
			if (currenNode != null) {
				stack.push(currenNode);
			} else {
				
				System.out.print(tmp.data + " ");
				
			}
		}
	}

	private void verticalTree(TreeNode treeNode) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		verticalTree(treeNode, list);
	}

	private void verticalTree(TreeNode treeNode, List<TreeNode> list) {
		if (treeNode != null) {
			list.add(treeNode);

			verticalTree(treeNode.left, list);
			verticalTree(treeNode.right, list);

			if (treeNode.left == null && treeNode.right == null) {
				System.out.println();
				for (TreeNode node : list) {
					System.out.print(node.data + " ");
				}
			}
			list.remove(treeNode);
		}
	}

	private void horizontalSprial(TreeNode head) {
		Map<Integer, Collection<Integer>> map = new LinkedHashMap<Integer, Collection<Integer>>();
		horizontalSprial(head, map, 0);

		Set<Entry<Integer, Collection<Integer>>> set = map.entrySet();
		Iterator<Entry<Integer, Collection<Integer>>> itr = set.iterator();

		System.out.println();
		while (itr.hasNext()) {
			Entry<Integer, Collection<Integer>> entry = itr.next();
			Collection<Integer> values = entry.getValue();

			if (values instanceof ArrayList) {

				for (Integer value : values) {
					System.out.print(" " + value);
				}
			} else if (values instanceof Stack) {
				Stack<Integer> stackValues = (Stack<Integer>) values;

				int size = stackValues.size();
				for (int i = 0; i < size; i++) {
					System.out.print(" " + stackValues.pop());
				}
			}
		}
	}

	private void horizontalSprial(TreeNode node,
			Map<Integer, Collection<Integer>> map, int level) {
		if (node != null) {
			if (level % 2 != 0) {
				Collection<Integer> list = map.get(level);
				if (list == null) {
					list = new ArrayList<Integer>();
					map.put(level, list);
				}
				list.add(node.data);

				horizontalSprial(node.left, map, level + 1);
				horizontalSprial(node.right, map, level + 1);
			} else {

				Stack<Integer> stack = (Stack<Integer>) map.get(level);
				if (stack == null) {
					stack = new Stack<Integer>();
					map.put(level, stack);
				}
				stack.push(node.data);
				horizontalSprial(node.left, map, level + 1);
				horizontalSprial(node.right, map, level + 1);
			}
		}
	}
}
