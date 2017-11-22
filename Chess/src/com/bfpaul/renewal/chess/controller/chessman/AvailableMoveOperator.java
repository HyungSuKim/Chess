package com.bfpaul.renewal.chess.controller.chessman;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.Direction;
import com.bfpaul.renewal.chess.controller.Coordinate;

class AvailableMoveOperator {
	private AvailableMoveOperator() {
	}

	public static ArrayList<Coordinate> availableMoveCoordinate(Chessman chessman, int x, int y) {
		ArrayList<Coordinate> availMove = new ArrayList<>();
		int moveableSquareCount = chessman.getMoveableSquareCount();
		
		if(moveableSquareCount == -1) {
			// ¹«ÇÑ´ë
		} else {
			for(int count = 0; count < moveableSquareCount; count++) {
				for (Direction direction : chessman.getDirection()) {
					System.out.println(direction);
					switch (direction) {
					case UP:
						availMove.add(availableMoveUp(chessman, x, y, count));
						break;
					case DOWN:
						availMove.add(availableMoveDown(chessman, x, y, count));
						break;
					case LEFT:
						availMove.add(availableMoveLeft(chessman, x, y, count));
						break;
					case RIGHT:
						availMove.add(availableMoveRight(chessman, x, y, count));
						break;
					case UP_LEFT:
						availMove.add(availableMoveUpLeft(chessman, x, y, count));
						break;
					case UP_RIGHT:
						availMove.add(availableMoveUpRight(chessman, x, y, count));
						break;
					case DOWN_LEFT:
						availMove.add(availableMoveDownLeft(chessman, x, y, count));
						break;
					case DOWN_RIGHT:
						availMove.add(availableMoveDownRight(chessman, x, y, count));
						break;
					default:
						break;
					}
				}
			}
		}
		return availMove;
	}

	static Coordinate availableMoveUp(Chessman chessman, int x, int y, int count) {
		if (Coordinate.isValidate(x, (y + count))) {
			return new Coordinate(x, (y + count));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveDown(Chessman chessman, int x, int y, int count) {
		if (Coordinate.isValidate(x, (y - count))) {
			return new Coordinate(x, (y - count));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveRight(Chessman chessman, int x, int y, int count) {
		if (Coordinate.isValidate((x + count), y)) {
			return new Coordinate((x + count), y);
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveLeft(Chessman chessman, int x, int y, int count) {
		if (Coordinate.isValidate((x - count), y)) {
			return new Coordinate((x - count), y);
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveUpLeft(Chessman chessman, int x, int y, int count) {
		if (Coordinate.isValidate((x - count), (y + count))) {
			return new Coordinate((x - count), (y + count));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveUpRight(Chessman chessman, int x, int y, int count) {
		if (Coordinate.isValidate((x + count), (y + count))) {
			return new Coordinate((x + count), (y + count));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveDownLeft(Chessman chessman, int x, int y, int count) {
		System.out.println(Coordinate.isValidate((x - chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount())));
		if (Coordinate.isValidate((x - chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount()))) {
			return new Coordinate((x - chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount()));
		} else {
			return new Coordinate(x, y);
		}
	}

	static Coordinate availableMoveDownRight(Chessman chessman, int x, int y, int count) {
		if (Coordinate.isValidate((x + chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount()))) {
			return new Coordinate((x + chessman.getMoveableSquareCount()), (y - chessman.getMoveableSquareCount()));
		} else {
			return new Coordinate(x, y);
		}
	}
}
