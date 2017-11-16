package com.bfpaul.chess.game.manager;

import com.bfpaul.chess.board.BoardPanel;
import com.bfpaul.chess.chessman.ChessmanSetter;

public class Manager {

	public BoardPanel board = new BoardPanel();

	public Manager() {
		new ChessmanSetter(board);
	}
}
