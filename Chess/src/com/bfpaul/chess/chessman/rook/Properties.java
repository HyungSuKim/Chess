package com.bfpaul.chess.chessman.rook;

class Properties {
	final int MOVEABLE = 7;
	
	private boolean isWhite;
	private boolean isMoved;
	
	Properties(boolean isWhite) {
		this.isWhite = isWhite;
	}
	
	public boolean getIsWhite() {
		return isWhite;
	}
	
	public int getMoveable() {
		return MOVEABLE;
	}
	
	public void setIsMoved() {
		isMoved = true;
	}
	
	public boolean getIsMoved() {
		return isMoved;
	}
}
