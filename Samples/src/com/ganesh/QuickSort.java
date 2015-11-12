package com.ganesh;

import java.util.Random;

public class QuickSort {

	public static void main(String[] args) {
		QuickSort main = new QuickSort();
		int[] unsortedArray = new int[] { 2, 5, 6, 1, 9, 45, 56, 32, 78, 34,
				90, 1, 4, 5, 7, 8, 9, 0, 22, 11, 33, 44, 87, 43, 90, 45, 3, 78,
				56, 30, 26, 7, 3, 4, 15, 12, 14, 13 };
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
		// int pivot = (lowIndex + highIndex) / 2;
		// int pivot = highIndex;
		int pivot = getRandomBetween(i, j);
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

	private int getRandomBetween(int low, int high) {
		Random r = new Random();
		int rand = r.nextInt(high - low) + low;
		return rand;
	}
}
