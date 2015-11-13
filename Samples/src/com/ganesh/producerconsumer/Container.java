package com.ganesh.producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Container {
	
	private static Random random = new Random();

	List<Block> blocks;
	private int containerId;

	public Container() {
		blocks = new ArrayList<Block>();
		containerId = random.nextInt(Integer.MAX_VALUE);
	}

	public void addBlock(Block block) {
		blocks.add(block);
	}

	public int blockSize() {
		return blocks.size();
	}

	public Block getBlock(int index) {
		return blocks.remove(index);
	}

	public Block getBlock() {
		return getBlock(0);
	}
	
	public int getId(){
		return containerId;
	}
}
