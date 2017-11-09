package com.bfpaul.chess.game.manager;

import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.image.FlatImagePanel;

public class CoordinateBoard extends Coordinate{
	
	private FlatPanel onSquare[][] = new FlatPanel[super.MAXIMUM_Y][super.MAXIMUM_X];
	
	public CoordinateBoard(FlatImagePanel chessman, int x, int y) {
		super(x, y);
		if(super.isValidate(x, y)) {
			onSquare[y][x].add(chessman);
		}
	}
	
//	FlatImagePanel getOnSquare(int x, int y) {
//		
//	}

}
