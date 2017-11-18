package com.bfpaul.chess.board;

public class Coordinate {
	final int MINIMUM_X = 0;
	final int MAXIMUM_X = 7;
	final int MINIMUM_Y = 0;
	final int MAXIMUM_Y = 7;
	private int x, y;
	
	public Coordinate(int x, int y){
		if(isValidate(x, y)) {
			this.x = x;
			this.y = y;
		}
	}
	
	public boolean isValidate(int x, int y) {
		return (MINIMUM_X <= x && x <= MAXIMUM_X) && (MINIMUM_Y <= y && y <= MAXIMUM_Y);
	}
	
	public void move(int x, int y) {
		if(isValidate(x, y)) {
			this.x = x;
			this.y = y;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
