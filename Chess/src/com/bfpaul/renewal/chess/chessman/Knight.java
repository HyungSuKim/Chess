package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Knight의 특성을 가진 체스말로써 역할 하기위해서 만들어졌다.   
 * Knight의 표현을 위한 이미지와 이동을 위한 정보를 제공한다.
 * 
 * Direction[] getDirection() { return Direction.values(); }
 * -> Knight의 이동은 UP, DOWN, RIGHT, LEFT로 2칸 이동 후 UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT로 1칸 이동한다고 생각하였다.
 * 
 *  * 	public Direction[] getDirection() {	return new Direction[] { Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT }; }
 * 수정사항 경우 1 -> Knight의 이동은 UP, DOWN, RIGHT, LEFT로 2칸 이동후
 * 				UP/DOWN의 경우 LEFT나 RIGHT로 LEFT/RIGHT의 경우 UP이나 DOWN으로 1칸 이동한다
 * 				<위키백과의 설명은 경우 1과 동일하게 설명하고있다 2칸 직진 후 좌우 1칸이동>
 * 수정사항 경우 2 -> Knight의 이동은 UP, DOWN, RIGHT, LEFT로 1칸 이동후
 * 				UP의 경우 UP_LEFT, UP_RIGHT로 / DOWN의 경우 DOWN_LEFT, DOWN_RIGHT로
 * 				LEFT의 경우 UP_LEFT, DOWN_LEFT로 / RIGHT의 경우 UP_RIGHT로 DOWN_RIGHT로 1칸 이동한다.
 */
public class Knight extends Chessman {
	
	private final boolean IS_WHITE;
	
	public Knight(boolean iswhite) {
		IS_WHITE = iswhite;
	}

	@Override
	public Direction[] getDirection() {
		return new Direction[] { Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT };
	}

	@Override
	public boolean isWhite() {
		return IS_WHITE;
	}

	@Override
	public int getMoveableSquareCount() {
		return 3;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.KNIGHT);
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.KNIGHT;
	}
	

}
