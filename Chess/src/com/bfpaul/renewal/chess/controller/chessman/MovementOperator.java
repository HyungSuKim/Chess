package com.bfpaul.renewal.chess.controller.chessman;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.Direction;
import com.bfpaul.renewal.chess.controller.Coordinate;

class MovementOperator {
	private MovementOperator() {}
	
	public static ArrayList<Coordinate> pawnAvailableMove(Chessman chessman, int x, int y) {
		ArrayList<Coordinate> availMove = new ArrayList<>();
		if(chessman.isWhite()) {
			
		}
		for(Direction direction : chessman.getDirection()) {
			switch(direction) {
			case UP : 
				if(Coordinate.isValidate(x, (y + chessman.getMoveableSquareCount()))){
					availMove.add(new Coordinate(x, (y + chessman.getMoveableSquareCount())));
					break;
				}
				break;
			case LEFT :
				if(Coordinate.isValidate((x - chessman.getMoveableSquareCount()), y)){
					availMove.add(new Coordinate((x - chessman.getMoveableSquareCount()), y));
					break;
				}
				break;
			case RIGHT :
				if(Coordinate.isValidate((x + chessman.getMoveableSquareCount()), y)){
					availMove.add(new Coordinate((x + chessman.getMoveableSquareCount()), y));
					break;
				}
				break;
				default : break;
			}
		}
		return availMove;
	}
}
