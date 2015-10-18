package com.ganesh;


public class MergingTwoSortedArrays {
	
	public static void main(String[] args) {
		MergingTwoSortedArrays main = new MergingTwoSortedArrays();
		int[] array1 = new int[]{1,3,5,7,9,11,13,15,16,18,20,20,23};
		int[] array2 = new int[]{0,2,4,6,8,10,12,14,17,19,21,22};
		
		int[] sorted = main.merge(array1, array2);
		for(int number : sorted){
		System.out.print(number + ",");
		}
	}
	
	public int[] merge(final int[] array1 , final int[] array2){
		int totalSize = array1.length + array2.length;
		int[] sortedArray = new int[totalSize];
		int pointer1 = 0;
		int pointer2 = 0;
		for(int i=0;i<totalSize;i++){
			int value1 = 0;
			int value2 = 0;
			if(pointer1<array1.length){
				value1 = array1[pointer1];
			}else{
				while(pointer2<array2.length){
					sortedArray[i] = array2[pointer2];
					pointer2++;
				}
				break;
			}
			if(pointer2<array2.length){
				value2 = array2[pointer2];
			}else{
				while(pointer1<array1.length){
					sortedArray[i] = array1[pointer1];
					pointer1++;
				}
				break;
			}
			
			if(value1 <= value2){
				sortedArray[i] = value1;
				pointer1++;
			}else{
				sortedArray[i] = value2;
				pointer2++;
				
			}
		}
		
		return sortedArray;
	}
	
	public int[] sortedArray(int a[], int b[]) {
	    int[] result = new int[a.length +b.length];
	    int i =0; int j = 0;int k = 0;
	    while(i<a.length && j <b.length) {
	        if(a[i]<b[j]) {
	            result[k++] = a[i];
	            i++;
	        } else {
	            result[k++] = b[j];
	            j++;
	        }
	    }
	    return result;
	}

}
