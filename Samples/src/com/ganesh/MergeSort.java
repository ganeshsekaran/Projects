package com.ganesh;

public class MergeSort {

	public static void main(String[] args) {
		MergeSort main = new MergeSort();
		int[] unsortedArray = new int[] { 13, 9, 2, 5, 11, 6, 1, 9, 7, 3, 4,
				15, 12, 14, 10, 13, 14, 8 };
		main.mergeSort(unsortedArray);
		for (int no : unsortedArray) {
			System.out.print(no + ",");
		}
	}

	public void mergeSort(int[] unsortedArray) {
		int[] tmpArray = new int[unsortedArray.length];
		splitSortMerge(unsortedArray, tmpArray, 0, unsortedArray.length - 1);
	}

	private void splitSortMerge(int[] unsortedArray, int[] tmpArray,
			int lowIndex, int highIndex) {

		if (lowIndex < highIndex) {
			int mid = (lowIndex + highIndex) / 2;
			splitSortMerge(unsortedArray, tmpArray, lowIndex, mid);
			splitSortMerge(unsortedArray, tmpArray, mid + 1, highIndex);
			sortMerge(unsortedArray, tmpArray, lowIndex, mid, highIndex);
		}
	}

	private void sortMerge(int[] unsortedArray, int[] tmpArray, int lowIndex,
			int mid, int highIndex) {
		for (int i = lowIndex; i <= highIndex; i++) {
			tmpArray[i] = unsortedArray[i];
		}

		int i = lowIndex;
		int j = mid + 1;
		int k = lowIndex;
		while (i <= mid && j <= highIndex) {
			if (tmpArray[i] <= tmpArray[j]) {
				unsortedArray[k++] = tmpArray[i++];
			} else if (tmpArray[i] > tmpArray[j]) {
				unsortedArray[k++] = tmpArray[j++];
			}
		}
		while (i <= mid) {
			unsortedArray[k++] = tmpArray[i++];
		}
	}
}
