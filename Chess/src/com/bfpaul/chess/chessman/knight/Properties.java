package com.bfpaul.chess.chessman.knight;

class Properties {
	private final int MOVEABLE = 3;
	
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
