package com.ganesh.bowling;

import java.util.Random;

public class Chance {
	
	private static final int NO_OF_WOODS = 10;

	private final int noOfRounds;
	private final int chanceCount;
	private final IPointsStrategy strategy;

	private volatile int roundCound;
	private volatile int point;

	private volatile int[] pointArray;

	Chance(IPointsStrategy strategy, int chanceCount, int noOfRounds) {
		this.strategy = strategy;
		this.chanceCount = chanceCount;
		this.noOfRounds = noOfRounds;
		this.pointArray = new int[noOfRounds];
	}

	int[] bowl() {
		int totalPoints = 0;
		for (int i = 0; i < noOfRounds && totalPoints < NO_OF_WOODS; i++) {
			Random random = new Random();
			
			int nextNum = 0;
			for(int po : pointArray){
				nextNum += po; 
			}
			nextNum = NO_OF_WOODS - nextNum;
			int point = random.nextInt(nextNum);
			totalPoints += point;
			pointArray[roundCound++] = point;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pointArray;
	}
	
	
}
