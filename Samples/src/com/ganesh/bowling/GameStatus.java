package com.ganesh.bowling;

public class GameStatus {

	private volatile Status status;
	private final GameData gameData;
	private volatile GameResultData resultData;

	public GameStatus(GameData gameData) {
		this.gameData = gameData;
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
	
	public GameResultData waitForcomplete(){
		synchronized (this) {
			if(status == null || Status.IN_PROGRESS.equals(status) || Status.WAITING_TO_START.equals(status)){
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
}
