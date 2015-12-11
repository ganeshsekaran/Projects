package com.ganesh.bowling;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		Game game = Game.getInstance();
		Thread.sleep(1000);
		main.start(game);
	}

	private void start(Game game) {
		List<GameStatus> statusList = new ArrayList<GameStatus>();
		for (int i = 0; i < 1;i++) {
			GameData gameData = new GameData(i+1, null, 2);

			Player player1 = new Player("Ganesh");
			//Player player2 = new Player("Sekaran");
			gameData.addPlayer(player1);
			//gameData.addPlayer(player2);

			GameStatus status = game.startGame(gameData);
			status.printGameFlow();
			statusList.add(status);
		}
		
/*		for(GameStatus status : statusList){
			GameResultData data = status.waitForcomplete();
			System.out.println("Game : "+status.getGameData().getGameId() + " status "+ data);
		}
*/	}
}
