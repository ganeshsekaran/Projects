package com.ganesh.bowling;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameStatus {

	interface StatusUpdate {
		void onUpdate(Player player, Chance chance);
	}

	private volatile Status status;
	private final GameData gameData;
	private final GameResultData resultData;

	public GameStatus(GameData gameData) {
		this.gameData = gameData;
		resultData = new GameResultData();
	}

	public enum Status {
		ERROR, IN_PROGRESS, WAITING_TO_START, COMPLETED
	}

	public Status getStatus() {
		return status;
	}

	public GameResultData getResultData() {
		return resultData;
	}

	public void printGameFlow() {
		printGameFlows();

		if (status == null || Status.IN_PROGRESS.equals(status)
				|| Status.WAITING_TO_START.equals(status)) {
			StatusUpdate listener = new StatusUpdateImpl();
			resultData.addListener(listener);
		}
	}

	public GameResultData waitForcomplete() {
		synchronized (this) {
			if (status == null || Status.IN_PROGRESS.equals(status)
					|| Status.WAITING_TO_START.equals(status)) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultData;
	}

	void setStatus(Status status) {
		this.status = status;
	}

	GameData getGameData() {
		return gameData;
	}

	private void printGameFlows() {
		if (resultData != null) {
			Map<Player, List<Chance>> map = resultData.getResultMap();
			Set<Player> players = map.keySet();

			Iterator<Player> itr = players.iterator();
			while (itr.hasNext()) {
				Player player = itr.next();

				System.out.println("Player : " + player.getName());
				List<Chance> chances = map.get(player);
				for (Chance chance : chances) {
					System.out.print("Chance : " + chance.getChanceCount()
							+ "   ");
					int[] points = chance.getPoints();
					for (int pot : points) {
						System.out.print(pot + " / ");
					}
					System.out.println();
				}
			}
		}
	}

	class StatusUpdateImpl implements StatusUpdate {

		public void onUpdate(Player player, Chance chance) {
			System.out.println("Player : " + player.getName());
			System.out.print("Chance : " + chance.getChanceCount() + "   ");
			int[] points = chance.getPoints();
			for (int pot : points) {
				System.out.print(pot + " / ");
			}
			System.out.println();

			if (status == Status.COMPLETED) {
				resultData.addListener(null);
			}
		}
	}
}
