package com.test.cci;

public class RotateMatrix {

	public static void main(String[] args) {

		int[][] arr = new int[][] { { 1, 2, 3 }, { 4, 5, 0 }, { 7, 8, 9 } };
		int[] top = new int[3];
		int[] bottom = new int[3];
		int[] left = new int[3];
		int[] right = new int[3];
		int[] temp = new int[3];
		int m = 0;
		int n = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (arr[i][j] == 0) {
					System.out.println("(" + i + "," + j + ")");
					m = i;
					n = j;
				}
			}

		}
		System.out.println("Zeros will be present in:");
		for (int i = 0; i < 3; i++) {
			System.out.println("(" + m + "," + i + ")");

		}
		for (int i = 0; i < 3; i++) {
			System.out.println("(" + i + "," + n + ")");

		}

	}

}
