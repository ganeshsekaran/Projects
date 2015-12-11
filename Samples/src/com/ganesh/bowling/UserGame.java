package com.ganesh.bowling;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class UserGame {

	private static final int CHANCE_COUNT = 10;

	private final GameStatus gameStatus;
	private final GameData gameData;
	private final ConcurrentHashMap map;
	
	private final IPointsStrategy strategy;
	private final int noOfRounds;

	UserGame(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
		this.gameData = gameStatus.getGameData();
		this.strategy = gameData.getPointStrategy();
		this.noOfRounds = 2;
		this.map = new ConcurrentHashMap();
	}

	void start() {

		List<Player> players = gameData.getPlayers();
		int playersCount = players.size();

		for (int i = 0; i < CHANCE_COUNT; i++) {
			for (int j = 0; j < playersCount; j++) {
				Player player = players.get(j);
				Chance chance = new Chance(strategy, i+1, noOfRounds);
				int[] points = chance.bowl();
				player.addPoints(chance, points);
			}
		}

		gameStatus.setStatus(GameStatus.Status.COMPLETED);
		synchronized (gameStatus) {
			gameStatus.notify();
		}
	}
}
