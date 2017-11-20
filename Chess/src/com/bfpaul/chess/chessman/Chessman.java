package com.bfpaul.chess.chessman;

import java.util.ArrayList;

import javax.swing.BorderFactory;

import com.bfpaul.chess.board.Coordinate;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

//체스말의 속성을 가지고 있다.
//흰색인지 아닌지(색상) 그리고 체스말이 움직일 수 있는 칸 수를 가지고 있으며 체스말의 이미지 중 하나로 셋팅된다.
@SuppressWarnings("serial")
public class Chessman extends FlatImagePanel {
	// 체스말이 흰색인지 아닌지 = 체스말은 흰색 아니면 검정색
	private boolean isWhite;
	// 체스말의 이동가능한 square의 갯수
	private final int MOVEABLE_SQUARE_COUNT;
	// 체스말이 움직일 수 있는 square의 좌표들로써 체스말에 따라 움직일 수 있는 좌표를 표현하고자 했다
	private ArrayList<Coordinate> moveableSquareCoordinate = new ArrayList<>();

	// 체스말을 생성한다.
	// 체스말은 흰색인지 아닌지, 이동가능한 square의 갯수에 대한 정보를 갖고
	// 흰색인지 아닌지 그리고 체스맨타입 정보를 이용해서 그에 맞는 이미지를 갖는다.
	protected Chessman(boolean isWhite, int moveableSquareCount, ChessmanType chessmanType) {
		this.isWhite = isWhite;
		MOVEABLE_SQUARE_COUNT = moveableSquareCount;

		setImage(ChessmanImage.getChessmanImage(isWhite, chessmanType), ImageOption.MATCH_PARENT);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}

	// 체스말이 흰색인지 아닌지를 반환한다.
	public boolean getIsWhite() {
		return isWhite;
	}

	// 체스말의 이동가능한 square의 갯수를 반환한다
	public int getMoveableSquareCount() {
		return MOVEABLE_SQUARE_COUNT;
	}
	
	// 체스말의 이동가능한 네모칸 좌표들을 설정해준다
	public void setMoveableSquareCoordinate(int x, int y) {
		moveableSquareCoordinate.add(new Coordinate(x, y));
	}
	// 체스말의 이동가능한 네모칸 좌표들을 초기화한다.
	public void refreshMoveableSquareCoordinate() {
		moveableSquareCoordinate.clear();
	}
	public ArrayList<Coordinate> getMoveableSquareCoordinate() {
		return moveableSquareCoordinate;
	}
}
