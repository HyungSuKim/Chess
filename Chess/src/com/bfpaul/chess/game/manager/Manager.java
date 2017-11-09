package com.bfpaul.chess.game.manager;

import java.util.ArrayList;

import com.bfpaul.chess.board.Board;
import com.bfpaul.chess.timer.Timer;
import com.mommoo.flat.image.FlatImagePanel;

public class Manager {
	
//	public Board board = new Board();
	public Timer timer = new Timer();
	
//	CoordinateBoard corrdinateBoard = new CoordinateBoard();
	
	ArrayList<FlatImagePanel> whiteChessman = new ArrayList<>();
	ArrayList<FlatImagePanel> blackChessman = new ArrayList<>();
	
	public Manager() {
		
	}
}


