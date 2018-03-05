package com.bfpaul.renewal.chess.board;

enum SquareEventColorPriority {
	ATTACK(1),
	CASTLING(1),
	CHECK(2),
	MOVE(3),
	ORIGINAL(4);
	
	private final int PRIORITY;
	
	private SquareEventColorPriority(int priority) {
		this.PRIORITY = priority;
	}
	
	int getColorPriority() {
		return PRIORITY;
	}
}
