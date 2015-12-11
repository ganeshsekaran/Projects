package com.ganesh.bowling;

import java.util.ArrayList;
import java.util.List;

public class GameData {

	private final List<Player> players;
	private final int gameId;
	private IPointsStrategy pointStrategy;
	private final int noOfRounds;

	public GameData(int gameId, IPointsStrategy pointStrategy, int roundCount) {
		this.gameId = gameId;
		this.pointStrategy = pointStrategy;
		this.players = new ArrayList<Player>();
		this.noOfRounds = roundCount;
	}

	public int getGameId() {
		return gameId;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public List<Player> getPlayers() {
		return clonePlayers();
	}

	public IPointsStrategy getPointStrategy() {
		return pointStrategy;
	}

	private List<Player> clonePlayers() {
		List<Player> copy = new ArrayList<Player>();
		for (Player player : players) {
			copy.add(player);
		}
		return copy;
	}
}
