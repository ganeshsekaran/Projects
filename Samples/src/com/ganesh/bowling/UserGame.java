package com.ganesh.bowling;

import java.util.List;

class UserGame {

	private static final int CHANCE_COUNT = 10;

	private final GameStatus gameStatus;
	private final GameData gameData;

	private final IPointsStrategy strategy;
	private final int noOfRounds;

	UserGame(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
		this.gameData = gameStatus.getGameData();
		this.strategy = gameData.getPointStrategy();
		this.noOfRounds = 2;
	}

	void start() {
		GameResultData resultData = gameStatus.getResultData();
		List<Player> players = gameData.getPlayers();
		int playersCount = players.size();

		for (int i = 0; i < CHANCE_COUNT; i++) {
			for (int j = 0; j < playersCount; j++) {
				Player player = players.get(j);
				Chance chance = new Chance(strategy, i + 1, noOfRounds);
				chance.bowl();
				player.addChance(chance);
				resultData.add(player, chance);
			}
		}

		gameStatus.setStatus(GameStatus.Status.COMPLETED);
		synchronized (gameStatus) {
			gameStatus.notify();
		}
	}
}
