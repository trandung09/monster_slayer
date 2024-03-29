package org.game;

import java.util.Scanner;

public class test {

	private static int n, m;
	private static int[] array;

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		m = sc.nextInt();
		array = new int[4 * n];
		for (int i = 1; i <= n; i++) {
			int value = sc.nextInt();
			add(1, n, i, i, value, 1);
		}
		for (int i = 0; i < m; i++) {
			int L = sc.nextInt();
			int R = sc.nextInt();
			System.out.println(get(1, n, L, R, 1));
		}
		sc.close();
	}

	private static void add(int left, int right, int L, int R, int value, int index) {
		if (R < left || L > right) {
			return;
		}
		if (left == right) {
			array[index] = value;
			return;
		}
		int center = (left + right) / 2;
		add(left, center, L, R, value, index * 2);
		add(center + 1, right, L, R, value, index * 2 + 1);
		array[index] = Math.max(array[index * 2], array[index * 2 + 1]);
	}
	
	private static int get(int left, int right, int L, int R, int index) {
		if (R < left || L > right) {
			return Integer.MIN_VALUE;
		}
		if (left >= L && right <= R) {
			return array[index];
		}
		int center = (left + right) / 2;
		return Math.max(get(left, center, L, R, index * 2), get(center + 1, right, L, R, index * 2 + 1));
	}

}