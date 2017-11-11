package com.bfpaul.chess.chessman;

import javax.swing.BorderFactory;

import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
//chessman(체스말) 패키지에 포함된 하위 클래스(King, Queen, Bishop, Knight, Rook, Pawn)의 인스턴스 생성 시
//체스말의 속성 중 흰색인지 아닌지(색상) 정보를 가지고 있는 부모 클래스이다.
//이 부모클래스는 이미지 패널을 상속받도록 구현되었는데 이유는 체스말을 생성했을때 하나의 이미지패널로써 역할하도록 하기 위함이다.
@SuppressWarnings("serial")
public class Chessman extends FlatImagePanel {
//	자손 클래스의 인스턴스 생성시 흰색인지 아닌지(색상) 정보를 받아서 저장하기 위한 멤버변수이다.	
	private boolean isWhite;
//	체스말의 이동가능한 square의 갯수를 나타낸 상수이다. 자손의 인스턴스가 생성될 때 입력된 값으로 초기화해준다.
	private final int MOVEABLE_SQUARE;
	
//	자손 클래스의 인스턴스 생성시 흰색인지 아닌지(색상) 정보를 받아서 멤버변수에 저장한다.
//	그리고 자손클래스에서 각 자손의 움직일수 있는 칸수를 받아 멤버변수인 상수 MOVEABLE_SQUARE을 초기화해준다.
//	또한 파라미터로써 체스맨타입을 받아서 이미지를 가져와 이미지를 이미지패널에 셋팅해준다.
	protected Chessman(boolean isWhite, int moveableSquare, ChessmanType type) {
		this.isWhite = isWhite;
		MOVEABLE_SQUARE = moveableSquare;
		
		setImage(ChessmanImage.getChessmanImage(isWhite, type), ImageOption.MATCH_PARENT);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	흰색인지 아닌지를 가져오는 메서드이다.
	public boolean getIsWhite() {
		return isWhite;
	}

//	체스말의 이동가능한 square의 갯수를 반환한다
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
}
