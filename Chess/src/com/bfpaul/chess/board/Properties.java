package com.bfpaul.chess.board;

class Properties {
	
	private boolean onSquare[][] = new boolean[8][8];
	
	public void setOnSquare(int x, int y) {
		onSquare[y][x] = true;
	}
	
	public boolean getOnSquare(int x, int y) {
		return onSquare[y][x];
	}
}
