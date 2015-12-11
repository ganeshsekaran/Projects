package com.ganesh;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

	private static int test;
	
	static{
		System.out.println("test1");
	}
	
	static{
		System.out.println("test2");
		main(null);
	}
	
	public static void main(String[] args) {
		Test main = new Test();
		
		int[] a = {1,0,2,0,2,1,2};
		
		int index=0;
		for(int i : a){
			System.out.println(i);
			a[index] = 0;
			index++;
		}
		
		for(int i : a){
			System.out.print(i);
		}
		
		Executors.newFixedThreadPool(10);
		
		//int[] out = main.sort1(a);
		
		
		Lock lock = new ReentrantLock();
		lock.tryLock();
		
	}
	
	public int tsta(){
		return test;
	}
	
	static{
		System.out.println("test3");
	}
	
	private int[] sort(List<Integer> a){
		int i = 0;
		int j = a.size() - 1;
		int runIndex = 0;
		int[] out = new int[j+1];
		for(int index : a){
			if(index == 0){
				out[i] =index;
				i++;
			}else if(index == 2){
				out[j] = index;
				j--;
			}else{
				//out[runIndex] = i;
			}
			runIndex++;
		}
		return out;
	}
	
	private void sort3(int[] a){
		 int lo = 0;
	        int hi = a.length - 1;
	        int mid = 0,temp=0;
	        while (mid <= hi)
	        {
	            if(a[mid] == 0){
	                temp   =  a[lo];
	                a[lo]  = a[mid];
	                a[mid] = temp;
	                lo++;
	                mid++;
	                break;
	            }else if(a[mid] == 1){
	                mid++;
	            }else{
	                temp = a[mid];
	                a[mid] = a[hi];
	                a[hi] = temp;
	                hi--;
	                break;
	            }
	        }
	}
	
	
	private int[] sort1(int[] a){
		int i = 0;
		int j = a.length-1;
		int runIndex = 0;
		int[] out = new int[a.length];
		for(int index : a){
			if(index == 0){
				int tmp = a[i];
				a[i] =index;
				a[runIndex] = tmp;
				i++;
			}else if(index == 2){
				int tmp = a[j];
				if(tmp == 2){
					
				}else{
				a[j] = index;
				a[runIndex] = tmp;
				}
				j--;
			}else{
				//out[runIndex] = i;
			}
			runIndex++;
		}
		return out;
	}
	
	
	/*private Node insert(Node node, int data)
    {
        if (node == null)
            node = new Node(data);
        else
        {
            if (data <= node.getData())
                node.left = insert(node.left, data);
            else
                node.right = insert(node.right, data);
        }
        return node;
    }
		*/

}
