package com.bfpaul.chess.chessman.king;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman(체스말) 패키지에 포함된 King을 생성 해주고 그 King의 속성을 가지고있는 클래스이다.
@SuppressWarnings("serial")
public class King extends Chessman {

//	추후 king과 rook의 캐슬링(둘다 움직인 적이 없어야됨)을 구현하기 위해 가지고 있어야 될 속성이라 판단되어 선언한 변수(기본이 false)
	private boolean isMoved;
	
//	킹의 생성자인데 흰색이면(true) 검정잭이면 (false)를 받아서 이를 이용해서 흰색 또는 검정색 킹이미지를 가져와서 적용시켜준다.
	public King(boolean isWhite) {
//		부모클래스 Chessman의 생성자 호출을 통한 색상정보 및 왕은 어떻게 움직여도(상하좌우,대각선)
//		1칸만 움직일수 있다는 생각에서 조상의 MOVEABLE_SQUARE 상수를 1로 초기화해준다.
		super(isWhite, 1);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_KING, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_KING, ImageOption.MATCH_PARENT);
		}
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
