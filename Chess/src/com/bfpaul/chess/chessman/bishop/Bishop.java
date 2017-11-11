package com.bfpaul.chess.chessman.bishop;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman(체스말) 패키지에 포함된 bishop을 해주고 그 bishop의 속성을 가지고있는 클래스이다.
@SuppressWarnings("serial")
public class Bishop extends Chessman {
//	비숍이 이동가능한 square의 갯수를 나타낸 상수이다. 비숍이 대각선으로 이동하는지 아닌지는
//	비숍 자체가 알아야 될 필요는 없다고 판단하였다. 단 어찌되었든 최대 7칸은 무조건 움직일 수 있다는 생각에서 작성한 변수이다.
	private final int MOVEABLE_SQUARE = 7;
	
//	비숍의 생성자인데 흰색이면(true) 검정잭이면 (false)를 받아서 이를 이용해서 흰색 또는 검정색 비숍이미지를 가져와서 적용시켜준다.
	public Bishop(boolean isWhite) {
//	부모클래스 Chessman의 생성자 호출을 통한 색상정보 저장
		super(isWhite);
		
		if(isWhite) {
			setImage(Images.WHITE_BISHOP, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_BISHOP, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	움직일 수 있는 칸 수를 가져오는 메서드이다.
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}	
}
