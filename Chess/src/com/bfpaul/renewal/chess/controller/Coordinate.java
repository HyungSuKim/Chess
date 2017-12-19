package com.bfpaul.renewal.chess.controller;

public class Coordinate {
	final static int MINIMUM_X = 0;
	final static int MAXIMUM_X = 7;
	final static int MINIMUM_Y = 0;
	final static int MAXIMUM_Y = 7;

	private int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static boolean isValidate(int x, int y) {
		return (MINIMUM_X <= x && x <= MAXIMUM_X) && (MINIMUM_Y <= y && y <= MAXIMUM_Y);
	}

	public static int getMaximumX() {
		return MAXIMUM_X;
	}

	public static int getMaximumY() {
		return MAXIMUM_Y;
	}

	public static int getMinimumX() {
		return MINIMUM_X;
	}

	public static int getMinimumY() {
		return MINIMUM_Y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
