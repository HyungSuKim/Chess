package com.bfpaul.chess.chessman.king;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman(체스말) 패키지에 포함된 King을 생성 해주고 그 King의 속성을 가지고있는 클래스이다.
@SuppressWarnings("serial")
public class King extends Chessman {
//	킹이 움직일 수 있는 칸수를 나타내는 상수로써 static으로 선언해준 이유는 인스턴스 생성 시점 이전에 해당 변수를 가지고 있게 하기위함이다.
	private static final int MOVEABLE_SQUARE = 1;
//	추후 king과 rook의 캐슬링(둘다 움직인 적이 없어야됨)을 구현하기 위해 가지고 있어야 될 속성이라 판단되어 선언한 변수(기본이 false)
	private boolean isMoved;
	
//	킹의 생성자인데 흰색이면(true) 검정잭이면 (false)를 받아서 이를 이용해서 흰색 또는 검정색 킹이미지를 가져와서 적용시켜준다.
	public King(boolean isWhite) {
//		부모클래스 Chessman의 생성자 호출을 통한 색상정보 및 왕은 어떻게 움직여도(상하좌우,대각선)
//		1칸만 움직일수 있다는 생각에서 조상의 MOVEABLE_SQUARE 상수를 1로 초기화해준다.
//  그리고 생성하고자 하는 ChessmanType을 전달함으로써 부모 클래스의 생성자가 타입에 맞는 이미지를 가져 올 수 있도록 해준다.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.KING);
	}
	
//	움직임이 발생했을때 움직였음을 표시하기위한 메서드
	public void setIsMoved() {
		isMoved = true;
	}
	
//	움직였는지 아닌지 알수있는 메서드
	public boolean getIsMoved() {
		return isMoved;
	}
}
