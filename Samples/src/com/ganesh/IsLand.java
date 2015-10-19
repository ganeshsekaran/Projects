package com.ganesh;

public class IsLand {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IsLand obj = new IsLand();
		int[][] matrix = { { 1, 1, 1, 0, 1 }, { 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 1, 1 }, { 1, 0, 0, 1, 0 } };
		System.out.println("row length" + matrix.length);
		System.out.println("column length" + matrix[0].length);
		int numberOfIsland = obj.solutionUsingDFS(matrix);
		System.out.println("Number of Islands: " + numberOfIsland);

	}

	public int solutionUsingDFS(int[][] matrix) {
		boolean[][] visited = new boolean[matrix.length][matrix[0].length];
		int count = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 1 && !visited[i][j]) {
					doDFS(matrix, visited, i, j);
					count++;
				}
			}
		}
		return count;
	}

	public void doDFS(int[][] matrix, boolean[][] visited, int i, int j) {
		if (!isSafe(matrix, visited, i, j)) {
			return;
		}
		visited[i][j] = true;
		doDFS(matrix, visited, i, j - 1);
		doDFS(matrix, visited, i, j + 1);
		doDFS(matrix, visited, i - 1, j);
		doDFS(matrix, visited, i + 1, j);
	}

	public boolean isSafe(int[][] matrix, boolean[][] visited, int i, int j) {
		if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length
				|| visited[i][j] || matrix[i][j] == 0)
			return false;
		else
			return true;
	}

}
