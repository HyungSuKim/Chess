package com.bfpaul.chess.chessman.rook;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman 패키지 내에서 Rook을 ImagePanel로써 생성하고 룩의 고유 속성을 가지고있는 클래스
@SuppressWarnings("serial")
public class Rook extends Chessman {
//	룩은 어떻게 움직여도(상하좌우) 최대 7칸을 움직일수 있다는 생각에서 선언된 상수
	final int MOVEABLE_SQUARE = 7;
//	rook의 캐슬링(룩 킹 둘다 움직인 적이 없어야됨)을 구현하기 위해 가지고 있어야 될 속성이라 판단되어 선언한 변수(기본이 false)
	private boolean isMoved;
//	룩의 생성자인데 흰색이면(true) 검정잭이면 (false)를 받아서 이를 이용해서 흰색 또는 검정색 룩이미지를 가져와서 적용시켜준다.
	public Rook(boolean isWhite) {
//		부모클래스 Chessman의 생성자 호출을 통한 색상정보 저장
		super(isWhite);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_ROOK, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_ROOK, ImageOption.MATCH_PARENT);
		}
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
//	움직일수 있는 칸의 수를 가져와주는 get메서드
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
//	움직임이 발생했을때 움직였음을 표시하기위한 메서드
	public void setIsMoved() {
		isMoved = true;
	}
//	움직였는지 아닌지 반환하는 메서드
	public boolean getIsMoved() {
		return isMoved;
	}

}
