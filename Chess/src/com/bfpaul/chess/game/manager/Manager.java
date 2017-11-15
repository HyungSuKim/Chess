package com.bfpaul.chess.game.manager;

import com.bfpaul.chess.board.BoardView;
import com.bfpaul.chess.chessman.ChessmanSetter;

public class Manager {

	public BoardView board = new BoardView();

	public Manager() {
		new ChessmanSetter(board);
	}
}
