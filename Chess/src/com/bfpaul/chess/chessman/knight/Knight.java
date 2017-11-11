package com.bfpaul.chess.chessman.knight;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman(체스말) 패키지에 포함된 Knight을 생성 해주고 그 Knight의 속성을 가지고있는 클래스이다.
@SuppressWarnings("serial")
public class Knight extends Chessman {
//	나이트의 움직일 수 있는 칸수를 나타내는 상수로써 static으로 선언해준 이유는 인스턴스 생성 시점 이전에 해당 변수를 가지고 있게 하기위함이다.
	private static final int MOVEABLE_SQUARE = 3;
//	나이트의 생성자인데 흰색이면(true) 검정잭이면 (false)를 받아서 이를 이용해서 흰색 또는 검정색 나이트이미지를 가져와서 적용시켜준다.
	public Knight(boolean isWhite) {
//	부모클래스 Chessman의 생성자 호출을 통한 색상정보 저장 나이트의 경우 2칸 직선으로 움직이고 1칸 대각선으로 움직인다 라는
//	생각에서 3칸을 움직일수 있음을 조상클래스의 MOVEABLE_SQUARE을 3으로 초기화 해주었다.
//  그리고 생성하고자 하는 ChessmanType을 전달함으로써 부모 클래스의 생성자가 타입에 맞는 이미지를 가져 올 수 있도록 해준다.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.KNIGHT);
		
	}

}
