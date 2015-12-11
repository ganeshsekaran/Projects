package com.ganesh.bowling;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private final String playerName;
	private final List<Chance> chanceList;

	public Player(String playerName) {
		this.playerName = playerName;
		this.chanceList = new ArrayList<Chance>();
	}

	public String getName() {
		return playerName;
	}

	public int getTotalPoints() {
		return 0;
	}

	void addChance(Chance chance) {
		chanceList.add(chance);
	}

	public void printPoints() {

		int i = 1;
		for (Chance chance : chanceList) {
			int points = 0;
			for (int po : chance.getPoints()) {
				points += po;
			}
			System.out.println("Chance : " + i + " Points : " + points);
			i++;
		}
	}
}
