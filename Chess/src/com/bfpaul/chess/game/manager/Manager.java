package com.bfpaul.chess.game.manager;

import java.util.ArrayList;

import com.bfpaul.chess.board.BoardView;
import com.bfpaul.chess.timer.GameTimerPanel;
import com.mommoo.flat.image.FlatImagePanel;

public class Manager {
	
	public BoardView board = new BoardView();
	public GameTimerPanel gameTimer = new GameTimerPanel();
	
//	CoordinateBoard corrdinateBoard = new CoordinateBoard();
	
	ArrayList<FlatImagePanel> whiteChessman = new ArrayList<>();
	ArrayList<FlatImagePanel> blackChessman = new ArrayList<>();
	
	public Manager() {
		
	}
}


