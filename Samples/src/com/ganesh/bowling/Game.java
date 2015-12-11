package com.ganesh.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

	private static volatile Game instance;
	private final ConcurrentHashMap<Integer, GameData> gameDataMap;
	private final ThreadPool threadPool;

	private Game() {
		gameDataMap = new ConcurrentHashMap<Integer, GameData>();
		this.threadPool = new ThreadPool(3);
	}

	public static Game getInstance() {
		if (instance == null) {
			synchronized (Game.class) {
				if (instance == null) {
					instance = new Game();
				}
			}
		}

		return instance;
	}

	public GameStatus startGame(GameData gameData) {

		GameStatus gameStatus = new GameStatus(gameData);
		int gameId = gameData.getGameId();
		if (gameDataMap.contains(gameData)) {
			gameStatus.setStatus(GameStatus.Status.ERROR);
		} else {
			gameStatus.setStatus(GameStatus.Status.WAITING_TO_START);
			gameDataMap.put(gameId, gameData);
			threadPool.addGame(gameStatus);
		}
		return gameStatus;
	}

	class ThreadPool {
		final int poolSize;
		final List<GameStatus> queue;
		final Thread[] threads;

		ThreadPool(int poolSize) {
			this.poolSize = poolSize;
			queue = new ArrayList<GameStatus>();
			threads = new Thread[poolSize];
			initThreads();
		}

		private void initThreads() {
			for (int i = 0; i < poolSize; i++) {
				Runnable runnable = new ThreadRunners(queue);
				Thread t = new Thread(runnable);
				threads[i] = t;
				t.start();
			}
		}

		void addGame(GameStatus gameStatus) {
			gameStatus.setStatus(GameStatus.Status.WAITING_TO_START);
			synchronized (queue) {
				queue.add(gameStatus);
				queue.notify();
			}
		}
	}

	class ThreadRunners implements Runnable {
		private final List<GameStatus> queue;
		private Thread t;

		ThreadRunners(List<GameStatus> queue) {
			this.queue = queue;
		}

		public void run() {
			t = Thread.currentThread();
			while (true) {
				if (!isInterrupted()) {
					runThread();
				}
			}
		}

		private void interrupt() {
			t.interrupt();
		}

		private boolean isInterrupted() {
			return t.isInterrupted();
		}

		private void runThread() {
			GameStatus gameStatus = null;
			synchronized (queue) {
				while (queue.size() == 0 && !isInterrupted()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						System.out.println("interrupt");
					}
				}

				if (!isInterrupted()) {
					gameStatus = queue.remove(0);
				}
			}

			if (gameStatus != null) {
				process(gameStatus);
			}
		}

		private void process(GameStatus gameStatus) {
			gameStatus.setStatus(GameStatus.Status.IN_PROGRESS);
			UserGame userGame = new UserGame(gameStatus);
			System.out.println("Game : " + gameStatus.getGameData().getGameId()
					+ " is processed by " + t.getName());
			userGame.start();
		}
	}
}
