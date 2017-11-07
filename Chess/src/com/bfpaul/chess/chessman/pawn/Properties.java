package com.bfpaul.chess.chessman.pawn;

class Properties {
	private final int MOVEABLE = 1;
	private final int FIRST_MOVEABLE = 2;
	
	private boolean isWhite;
	private boolean isMoved;
	
	Properties(boolean isWhite) {
		this.isWhite = isWhite;
	}
	
	public int getMoveable() {
		if(!isMoved) {
			return FIRST_MOVEABLE;
		} else {
			return MOVEABLE;
		}
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
