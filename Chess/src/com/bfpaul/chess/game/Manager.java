package com.bfpaul.chess.game;

import com.bfpaul.chess.board.Board;
import com.bfpaul.chess.chessman.pawn.Pawn;
import com.bfpaul.chess.timer.Timer;

class Manager {
	
	Board board = new Board();
	Timer timer = new Timer();
	
	Manager() {
		board.setChessman(new Pawn(true), 0, 0);
	}
}
