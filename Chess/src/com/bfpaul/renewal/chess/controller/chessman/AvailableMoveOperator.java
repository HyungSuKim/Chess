package com.bfpaul.renewal.chess.controller.chessman;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.Direction;
import com.bfpaul.renewal.chess.controller.Coordinate;

class AvailableMoveOperator {
	private AvailableMoveOperator() {
	}

	public static ArrayList<Coordinate> availableMoveCoordinate(Chessman chessman, int x, int y) {
		System.out.println(chessman.getChessmanType());
		ArrayList<Coordinate> availMove = new ArrayList<>();
		for (Direction direction : chessman.getDirection()) {
			System.out.println(direction);
			switch (direction) {
			case UP:
				availMove.add(availableMoveUp(chessman, x, y));
				break;
			case DOWN:
				availMove.add(availableMoveDown(chessman, x, y));
				break;
			case LEFT:
				availMove.add(availableMoveLeft(chessman, x, y));
				break;
			case RIGHT:
				availMove.add(availableMoveRight(chessman, x, y));
				break;
			case UP_LEFT:
				availMove.add(availableMoveUpLeft(chessman, x, y));
				break;
			case UP_RIGHT:
				availMove.add(availableMoveUpRight(chessman, x, y));
				break;
			case DOWN_LEFT:
				availMove.add(availableMoveDownLeft(chessman, x, y));
				break;
			case DOWN_RIGHT:
				availMove.add(availableMoveDownRight(chessman, x, y));
				break;
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
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveDown(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate(x, (y - chessman.getMoveableSquareCount()))) {
			return new Coordinate(x, (y - chessman.getMoveableSquareCount()));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveRight(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate((x + chessman.getMoveableSquareCount()), y)) {
			return new Coordinate((x + chessman.getMoveableSquareCount()), y);
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveLeft(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate((x - chessman.getMoveableSquareCount()), y)) {
			return new Coordinate((x - chessman.getMoveableSquareCount()), y);
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveUpLeft(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate((x - chessman.getMoveableSquareCount()), (y + chessman.getMoveableSquareCount()))) {
			return new Coordinate((x - chessman.getMoveableSquareCount()), (y + chessman.getMoveableSquareCount()));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveUpRight(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate((x + chessman.getMoveableSquareCount()), (y + chessman.getMoveableSquareCount()))) {
			return new Coordinate((x + chessman.getMoveableSquareCount()), (y + chessman.getMoveableSquareCount()));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveDownLeft(Chessman chessman, int x, int y) {
		System.out.println(Coordinate.isValidate((x - chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount())));
		if (Coordinate.isValidate((x - chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount()))) {
			return new Coordinate((x - chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount()));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveDownRight(Chessman chessman, int x, int y) {
		if (Coordinate.isValidate((x + chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount()))) {
			return new Coordinate((x + chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount()));
		} else {
			return new Coordinate(x, y);
		}
	}

	// public static ArrayList<Coordinate> kingAvailableMoveCoordinate(Chessman
	// chessman, int x, int y) {
	//
	// }
	//
	// public static ArrayList<Coordinate> queenAvailableMoveCoordinate(Chessman
	// chessman, int x, int y) {
	//
	// }
	//
	// public static ArrayList<Coordinate> bishopAvailableMoveCoordinate(Chessman
	// chessman, int x, int y) {
	//
	// }
	//
	// public static ArrayList<Coordinate> knightAvailableMoveCoordinate(Chessman
	// chessman, int x, int y) {
	//
	// }
	//
	// public static ArrayList<Coordinate> rookAvailableMoveCoordinate(Chessman
	// chessman, int x, int y) {
	//
	// }

	public static ArrayList<Coordinate> pawnAvailableMoveCoordinate(Chessman chessman, int x, int y) {
		return pawnAvailableMoveCoordinate(chessman, x, y);
	}

}
