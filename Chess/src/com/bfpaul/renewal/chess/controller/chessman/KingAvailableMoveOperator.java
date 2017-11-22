package com.bfpaul.renewal.chess.controller.chessman;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.Direction;
import com.bfpaul.renewal.chess.controller.Coordinate;

public class KingAvailableMoveOperator {

	private KingAvailableMoveOperator() {
	}

	public static ArrayList<Coordinate> availableMoveCoordinate(Chessman chessman, int x, int y) {
		ArrayList<Coordinate> availMove = new ArrayList<>();
		for (Direction direction : chessman.getDirection()) {
			switch (direction) {
			case UP:
				availMove.add(availableMoveUp(chessman, x, y));
				break;
			case LEFT:
				availMove.add(availableMoveLeft(chessman, x, y));
				break;
			case RIGHT:
				availMove.add(availableMoveRight(chessman, x, y));
				break;
			case DOWN:
			case UP_RIGHT:
			case UP_LEFT:
			case DOWN_RIGHT:
			case DOWN_LEFT:
			default:
				break;
			}
		}
		return availMove;
	}

	static Coordinate availableMoveUp(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate(x, (y + chessman.getMoveableSquareCount()))) {
			return new Coordinate(x, (y + chessman.getMoveableSquareCount()));
		} else {
			return null;
		}
	}

	static Coordinate availableMoveRight(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate((x + chessman.getMoveableSquareCount()), y)) {
			return new Coordinate((x + chessman.getMoveableSquareCount()), y);
		} else {
			return null;
		}
	}

	static Coordinate availableMoveLeft(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate((x - chessman.getMoveableSquareCount()), y)) {
			return new Coordinate((x - chessman.getMoveableSquareCount()), y);
		} else {
			return null;
		}
	}
}
