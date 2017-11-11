package com.bfpaul.chess.chessman.knight;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman(체스말) 패키지에 포함된 Knight을 생성 해주고 그 Knight의 속성을 가지고있는 클래스이다.
@SuppressWarnings("serial")
public class Knight extends Chessman {
//	나이트의 경우 2칸 직선으로 움직이고 1칸 대각선으로 움직인다 라는 생각에서 3칸을 움직일수 있음을 상수로써 표현해주었다.
	private final int MOVEABLE_SQUARE = 3;
	
//	나이트의 생성자인데 흰색이면(true) 검정잭이면 (false)를 받아서 이를 이용해서 흰색 또는 검정색 나이트이미지를 가져와서 적용시켜준다.
	public Knight(boolean isWhite) {
//	부모클래스 Chessman의 생성자 호출을 통한 색상정보 저장
		super(isWhite);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_KNIGHT, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_KNIGHT, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
//	나이트가 움직일 수 있는 칸수를 반환하는 메서드
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
}
