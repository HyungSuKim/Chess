package com.bfpaul.renewal.chess.controller.chessman;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.Direction;
import com.bfpaul.renewal.chess.controller.Coordinate;

public class MoveableRouteCalculator {
	private MoveableRouteCalculator() {
	}

	public static ArrayList<Coordinate> selectChessman(Chessman chessman, int x, int y) {
		ArrayList<Coordinate> moveableRoute = new ArrayList<>();
		int moveableSquare = chessman.getMoveableSquareCount();
		for (Direction direction : chessman.getDirection()) {
			switch (direction) {
			case UP:
				for (Coordinate availCoordinate : upRouteCalculator(moveableSquare, x, y)) {
					moveableRoute.add(availCoordinate);
				}
				break;
			case DOWN:
				for (Coordinate availCoordinate : downRouteCalculator(moveableSquare, x, y)) {
					moveableRoute.add(availCoordinate);
				}
				break;
			case LEFT:
				for (Coordinate availCoordinate : leftRouteCalculator(moveableSquare, x, y)) {
					moveableRoute.add(availCoordinate);
				}
				break;
			case RIGHT:
				for (Coordinate availCoordinate : rightRouteCalculator(moveableSquare, x, y)) {
					moveableRoute.add(availCoordinate);
				}
				break;
			case UP_LEFT:
				for (Coordinate availCoordinate : upLeftRouteCalculator(moveableSquare, x, y)) {
					moveableRoute.add(availCoordinate);
				}
				break;
			case UP_RIGHT:
				for (Coordinate availCoordinate : upRightRouteCalculator(moveableSquare, x, y)) {
					moveableRoute.add(availCoordinate);
				}
				break;
			case DOWN_LEFT:
				for (Coordinate availCoordinate : downLeftRouteCalculator(moveableSquare, x, y)) {
					moveableRoute.add(availCoordinate);
				}
				break;
			case DOWN_RIGHT:
				for (Coordinate availCoordinate : downRightRouteCalculator(moveableSquare, x, y)) {
					moveableRoute.add(availCoordinate);
				}
				break;
			}
		}
		return moveableRoute;
	}

	private static Coordinate[] upRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1) {
			moveableSquareCount = Coordinate.getMaximumY() - y;
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate(x, (y + count));
		}
		return availCoordinate;
	}
	
	private static Coordinate[] downRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1) {
			moveableSquareCount = y - Coordinate.getMinimumY();
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate(x, (y - count));
		}
		
		return availCoordinate;
	}
	
	private static Coordinate[] leftRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1) {
			moveableSquareCount = x - Coordinate.getMinimumX();
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate((x - count), y);
		}
		
		return availCoordinate;
	}
	
	private static Coordinate[] rightRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1) {
			moveableSquareCount = Coordinate.getMaximumX() - x;
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate((x + count), y);
		}
		return availCoordinate;
	}
	
	private static Coordinate[] upLeftRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1) {
			moveableSquareCount = Coordinate.getMaximumY() - y;
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate((x - count), (y + count));
		}
		return availCoordinate;
	}
	
	private static Coordinate[] upRightRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1) {
			moveableSquareCount = Coordinate.getMaximumY() - y;
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate((x + count), (y + count));
		}
		return availCoordinate;
	}
	
	private static Coordinate[] downLeftRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1) {
			moveableSquareCount = y - Coordinate.getMinimumY();
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate((x - count), (y - count));
		}
		return availCoordinate;
	}
	
	private static Coordinate[] downRightRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == -1) {
			moveableSquareCount = y - Coordinate.getMinimumY();
		}
		
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			availCoordinate[count-1] = new Coordinate((x + count), (y - count));
		}
		return availCoordinate;
	}
}
