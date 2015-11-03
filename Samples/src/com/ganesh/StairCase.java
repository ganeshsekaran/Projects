package com.ganesh;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StairCase {

	public static void main(String[] args) throws Exception {
		InputStreamReader ip = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader(ip);

		Integer count = Integer.parseInt(bf.readLine());
		int sum = 0;
		
		for(int i =0;i<count;i++){
			Integer in = Integer.parseInt(bf.readLine());
			sum += in;
		}
		
		
		

		for (int i = 0; i < count; i++) {

			int tmp = i;
			while (tmp < count - 1) {
				System.out.print(" ");
				tmp++;
			}

			for (int j = 1; j <= i + 1; j++) {

				System.out.print("#");
			}
			System.out.print("\n");
		}

	}

}
