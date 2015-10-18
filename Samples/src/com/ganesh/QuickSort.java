package com.ganesh;

public class QuickSort {

	public static void main(String[] args) {
		QuickSort main = new QuickSort();
		int[] unsortedArray = new int[] { 2, 5, 6, 1, 9, 7, 3, 4, 15, 12, 14,
				13 };
		main.sort(unsortedArray);
		for (int no : unsortedArray) {
			System.out.print(no + ",");
		}
	}

	public void sort(int[] unsortedArray) {
		quickSort(unsortedArray, 0, unsortedArray.length - 1);
	}

	private void quickSort(int[] unsortedArray, int lowIndex, int highIndex) {
		int i = lowIndex;
		int j = highIndex;
		int pivot = (lowIndex + highIndex) / 2;
		while (i <= j) {
			while (unsortedArray[i] < unsortedArray[pivot]) {
				i++;
			}

			while (unsortedArray[j] > unsortedArray[pivot]) {
				j--;
			}

			if (i <= j) {
				int tmp = unsortedArray[i];
				unsortedArray[i] = unsortedArray[j];
				unsortedArray[j] = tmp;
				i++;
				j--;
			}
			if (i < highIndex) {
				quickSort(unsortedArray, i, highIndex);
			}
			if (lowIndex < j) {
				quickSort(unsortedArray, lowIndex, j);
			}

		}
	}

}
