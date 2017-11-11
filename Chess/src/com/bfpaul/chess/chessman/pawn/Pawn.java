package com.bfpaul.chess.chessman.pawn;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
// chessman 패키지 내에서 pawn을 ImagePanel로써 생성하고 폰의 고유 속성을 가지고있는 클래스
@SuppressWarnings("serial")
public class Pawn extends Chessman {
//	폰의 움직일 수 있는 칸수를 나타내는 상수로써 static으로 선언해준 이유는 인스턴스 생성 시점 이전에 해당 변수를 가지고 있게 하기위함이다.
	private static final int MOVEABLE_SQUARE = 1;
//	말이 움직였는지 아닌지 정보를 가지고있는 변수
	private boolean isMoved;
	
//	폰의 생성자로써 흰색이면 흰색 폰 이미지를 가진 폰을 생성하고 아니면 검정 폰 이미지를 가진 폰을 생성한다.
	public Pawn(boolean isWhite) {
//		부모클래스 Chessman의 생성자 호출을 통한 색상정보 저장
//		폰은 첫번째 움직임을 제외하고 1씩 움직일수 있으므로 조상클래스의 MOVEABLE_SQUARE을 1로 초기화해주었다.
//  그리고 생성하고자 하는 ChessmanType을 전달함으로써 부모 클래스의 생성자가 타입에 맞는 이미지를 가져 올 수 있도록 해준다.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.PAWN);
	}

//	폰이 움직일 수 있는 square의 수를 반환하는 메서드로써 조상클래스의 getMoveableSquare 메서드를 오버라이드해서 구현했다. 
//	만약에 움직임이 없었으면 첫움직임에서 움직일수 있는 칸수를 반환하고 첫 움직임이 있었다면 기본으로 움직일수 있는 1을 반환해준다.
	@Override
	public int getMoveableSquare() {
		if(!isMoved) {
			return super.getMoveableSquare() * 2;
		} else {
			return super.getMoveableSquare();
		}
	}
	
//	첫 움직임 이후 움직였음을 설정해주기위한 메서드
	public void setIsMoved() {
		isMoved = true;
	}
	
//	isMoved를 반환받아서 움직임이 있었는지 없엇는지 확인하기 위한 메서드
	public boolean getIsMoved() {
		return isMoved;
	}
}