package com.ganesh.binarytree;

public class PrintTraversal {
	
	public enum TraversalType{
		PRE_ORDER, IN_ORDER, POST_ORDER
	}
	
	public void traversal(TreeNode treeNode, TraversalType type){
		if(type == TraversalType.PRE_ORDER){
			if(treeNode == null){
				System.out.println("No Data");
			}else{
				System.out.println("Pre order traversal");
				preOrder(treeNode);
			}
		}
		
		if(type == TraversalType.IN_ORDER){
			if(treeNode == null){
				System.out.println("No Data");
			}else{
				System.out.println("In order traversal");
				inOrder(treeNode);
			}
		}
		
		if(type == TraversalType.POST_ORDER){
			if(treeNode == null){
				System.out.println("No Data");
			}else{
				System.out.println("Post order traversal");
				postOrder(treeNode);
			}
		}
	}
	
	private void preOrder(TreeNode treeNode){
		if(treeNode != null){
			System.out.print(treeNode.data + " ");
			preOrder(treeNode.left);
			preOrder(treeNode.right);
		}
	}
	
	private void inOrder(TreeNode treeNode){
		if(treeNode != null){
			preOrder(treeNode.left);
			System.out.print(treeNode.data + " ");
			preOrder(treeNode.right);
		}
	}
	
	private void postOrder(TreeNode treeNode){
		if(treeNode != null){
			preOrder(treeNode.left);
			preOrder(treeNode.right);
			System.out.print(treeNode.data + " ");
		}
	}
}
