package com.ganesh.bowling;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Player {

	private final String playerName;
	private volatile int points;
	private final Map<Chance, int[]> pointMap;
	
	public Player(String playerName){
		this.playerName = playerName;
		this.pointMap = new LinkedHashMap<Chance, int[]>();
	}
	
	public String getName(){
		return playerName;
	}
	
	public int getPoint(){
		return points;
	}
	
	public void addPoints(Chance chance, int[] points){
		pointMap.put(chance, points);
	}
	
	public void printPoints(){
		Set<Chance> chances = pointMap.keySet();
		Iterator<Chance> itr = chances.iterator();
		
		int i=1;
		while(itr.hasNext()){
			Chance chance = itr.next();
			int points = 0;
			for(int po : pointMap.get(chance)){
				points += po;
			}
			System.out.println("Chance : " + i + " Points : " + points);
			i++;
		}
	}
	
	void setPoint(int points){
		this.points = points;
	}
}
