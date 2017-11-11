package com.bfpaul.chess.chessman.queen;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman 패키지 내에서 Queen을 ImagePanel로써 생성하고 퀸의 고유 속성을 가지고있는 클래스
@SuppressWarnings("serial")
public class Queen extends Chessman {
//	퀸의 움직일 수 있는 칸수를 나타내는 상수로써 static으로 선언해준 이유는 인스턴스 생성 시점 이전에 해당 변수를 가지고 있게 하기위함이다.
	private static final int MOVEABLE_SQUARE = 7;
//	퀸의 생성자로써 흰색이면 흰색 퀸 이미지를 가진 퀸을 생성하고 아니면 검정 퀸 이미지를 가진 퀸을 생성한다.
	public Queen(boolean isWhite) {
//		부모클래스 Chessman의 생성자 호출을 통한 색상정보 저장 퀸은 칸을 직선 혹은 대각선으로 이동할수있는데
//		이때 퀸이 최대로 이동가능한 칸수가 7이라 판단해서 조상의 MOVEABLE_SQUARE을 7로 초기화해주었다.
//  그리고 생성하고자 하는 ChessmanType을 전달함으로써 부모 클래스의 생성자가 타입에 맞는 이미지를 가져 올 수 있도록 해준다.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.QUEEN);
	}
}
