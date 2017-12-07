package com.bfpaul.renewal.chess.controller;

import com.bfpaul.renewal.chess.chessman.Direction;

public class MoveableRoute {
	Direction direction;
	Coordinate[] coordinates;
	
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