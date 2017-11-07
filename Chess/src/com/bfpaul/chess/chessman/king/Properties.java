package com.bfpaul.chess.chessman.king;

class Properties {
	final int MOVEABLE = 1;
	
	private boolean isWhite;
	private boolean isMoved;
	
	Properties(boolean isWhite) {
		this.isWhite = isWhite;
	}
	
	public int getMoveable() {
		return MOVEABLE;
	}
	
	public boolean getIsWhite() {
		return isWhite;
	}
	
	public void setIsMoved() {
		isMoved = true;
	}
	
	public boolean getIsMoved() {
		return isMoved;
	}
}
