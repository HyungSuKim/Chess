package com.bfpaul.renewal.chess.controller.chessman;

import java.util.HashMap;
import java.util.Map;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.Direction;
import com.bfpaul.renewal.chess.controller.Coordinate;

public class MoveableRouteCalculator {
	private MoveableRouteCalculator() {
	}

	public static Map<Direction, Coordinate[]> selectChessman(Chessman chessman, int x, int y) {
		Map<Direction, Coordinate[]> moveableRoute = new HashMap<>();
		int moveableSquareCount = chessman.getMoveableSquareCount();
		for (Direction direction : chessman.getDirection()) {
			switch (direction) {
			case UP:
				moveableRoute.put(Direction.UP, upRouteCalculator(moveableSquareCount, x, y));
				break;
			case DOWN:
				moveableRoute.put(Direction.DOWN, downRouteCalculator(moveableSquareCount, x, y));
				break;
			case LEFT:
				moveableRoute.put(Direction.LEFT, leftRouteCalculator(moveableSquareCount, x, y));
				break;
			case RIGHT:
				moveableRoute.put(Direction.RIGHT, rightRouteCalculator(moveableSquareCount, x, y));
				break;
			case UP_LEFT:
				moveableRoute.put(Direction.UP_LEFT, upLeftRouteCalculator(moveableSquareCount, x, y));
				break;
			case UP_RIGHT:
				moveableRoute.put(Direction.UP_RIGHT, upRightRouteCalculator(moveableSquareCount, x, y));
				break;
			case DOWN_LEFT:
				moveableRoute.put(Direction.DOWN_LEFT, downLeftRouteCalculator(moveableSquareCount, x, y));
				break;
			case DOWN_RIGHT:
				moveableRoute.put(Direction.DOWN_RIGHT, downRightRouteCalculator(moveableSquareCount, x, y));
				break;
			}
		}
		return moveableRoute;
	}
	
	private static int upSquareCountConverter(int moveableSquareCount, int y) {
		if (moveableSquareCount == -1 || moveableSquareCount > Coordinate.getMaximumY() - y) {
			moveableSquareCount = Coordinate.getMaximumY() - y;
		}
		return moveableSquareCount;
	}
	
	private static int downSquareCountConverter(int moveableSquareCount, int y) {
		if (moveableSquareCount == -1 || moveableSquareCount > y - Coordinate.getMinimumY()) {
			moveableSquareCount = y - Coordinate.getMinimumY();
		}
		return moveableSquareCount;
	}

	private static Coordinate[] upRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate(x, (y + count));
		}
		return availCoordinate;
	}
	
	private static Coordinate[] downRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate(x, (y - count));
		}
		
		return availCoordinate;
	}
	
	private static Coordinate[] leftRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1 || moveableSquareCount > x - Coordinate.getMinimumX()) {
			moveableSquareCount = x - Coordinate.getMinimumX();
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate((x - count), y);
		}
		
		return availCoordinate;
	}
	
	private static Coordinate[] rightRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1 || moveableSquareCount > Coordinate.getMaximumX() - x) {
			moveableSquareCount = Coordinate.getMaximumX() - x;
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate((x + count), y);
		}
		return availCoordinate;
	}
	
	private static Coordinate[] upLeftRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			if(Coordinate.isValidate((x - count), (y + count))) {
				availCoordinate[count-1] = new Coordinate((x - count), (y + count));
			} else {
				availCoordinate[count-1] = null;
			}
		}
		return availCoordinate;
	}
	
	private static Coordinate[] upRightRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			if(Coordinate.isValidate((x + count), (y + count))) {
				availCoordinate[count-1] = new Coordinate((x + count), (y + count));
			} else {
				availCoordinate[count-1] = null;
			}
		}
		return availCoordinate;
	}
	
	private static Coordinate[] downLeftRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			if(Coordinate.isValidate((x - count), (y - count))) {
				availCoordinate[count-1] = new Coordinate((x - count), (y - count));
			} else {
				availCoordinate[count-1] = null;
			}
		}
		return availCoordinate;
	}
	
	private static Coordinate[] downRightRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			if(Coordinate.isValidate((x + count), (y - count))) {
				availCoordinate[count-1] = new Coordinate((x + count), (y - count));
			} else {
				availCoordinate[count-1] = null;
			}
		}
		return availCoordinate;
	}
}
