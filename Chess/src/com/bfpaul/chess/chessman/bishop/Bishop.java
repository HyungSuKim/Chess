package com.bfpaul.chess.chessman.bishop;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman(체스말) 패키지에 포함된 bishop을 해주고 그 bishop의 속성을 가지고있는 클래스이다.
@SuppressWarnings("serial")
public class Bishop extends Chessman {
//	비숍의 움직일 수 있는 칸수를 나타내는 상수로써 static으로 선언해준 이유는 인스턴스 생성 시점 이전에 해당 변수를 가지고 있게 하기위함이다.
	private static final int MOVEABLE_SQUARE = 7;
//	비숍의 생성자인데 흰색이면(true) 검정잭이면 (false)를 받아서 이를 이용해서 흰색 또는 검정색 비숍이미지를 가져와서 적용시켜준다.
	public Bishop(boolean isWhite) {
//	부모클래스 Chessman의 생성자 호출을 통한 색상정보 및 비숍이 이동가능한 square의 갯수를 저장한다.
//	비숍은 대각선으로 이동하기는 하지만 최대로 무조건 7칸을 움직일 수 있다는 생각에서 7을 조상의 생성자에 파라미터로 전달하였다.
//  그리고 생성하고자 하는 ChessmanType을 전달함으로써 부모 클래스의 생성자가 타입에 맞는 이미지를 가져 올 수 있도록 해준다.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.BISHOP);
	}
}
