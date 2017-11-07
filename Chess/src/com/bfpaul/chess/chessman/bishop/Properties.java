package com.bfpaul.chess.chessman.bishop;

class Properties {
	private final int MOVEABLE = 7;
	
	private boolean isWhite;
	
	Properties(boolean isWhite) {
		this.isWhite = isWhite;
	}
	
	public int getMoveable() {
		return MOVEABLE;
	}
	
	public boolean getIsWhite() {
		return isWhite;
	}
}
