package com.ganesh;

public class FindingTheMaxSumInArray {

	public static void main(String[] args) {
		FindingTheMaxSumInArray main = new FindingTheMaxSumInArray();

		int[] array = new int[] { 1, 2, -5, 4, 7, 6, -10, 7, 5 };
		System.out.println(main.findMaxSum(array));

		array = new int[] { -1, -8, -9, -6, -10, -1,-3, -2 };
		System.out.println(main.findMaxSum(array));
	}

	public int findMaxSum(int[] input) {
		if(input== null || input.length == 0){
			return -1;
		}
		
		int currentSum = 0;
		int maxSum = input[0];

		for (int number : input) {
			currentSum = currentSum + number;
			if (currentSum > maxSum) {
				maxSum = currentSum;
			}

			if (currentSum < 0) {
				currentSum = 0;
			}
		}
		return maxSum;
	}
}
