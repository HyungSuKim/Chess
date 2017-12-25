package com.bfpaul.renewal.chess.route;

public class MoveableRoute {
	private Direction direction;
	private Coordinate[] coordinates;
	
	public MoveableRoute(Direction direction, Coordinate[] coordinates) {
		this.direction = direction;
		this.coordinates = coordinates;
	}
	
	public Coordinate[] getCoordinates() {
		return coordinates;
	}
	
	public Direction getDirection() {
		return direction;
	}
}
