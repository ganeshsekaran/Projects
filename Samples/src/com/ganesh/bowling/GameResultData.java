package com.ganesh.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameResultData {

	private Map<Player, List<Chance>> map;
	private GameStatus.StatusUpdate listener;

	GameResultData() {
		map = new ConcurrentHashMap<Player, List<Chance>>();
	}

	Map<Player, List<Chance>> getResultMap() {
		return map;
	}

	void add(Player player, Chance chance) {
		List<Chance> chances = map.get(player);
		if (chances == null) {
			chances = new ArrayList<Chance>();
			map.put(player, chances);
		}
		chances.add(chance);

		if (listener != null) {
			listener.onUpdate(player, chance);
		}
	}

	void addListener(GameStatus.StatusUpdate listener) {
		this.listener = listener;
	}

}
