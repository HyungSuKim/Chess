package com.bfpaul.chess.chessman.queen;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman 패키지 내에서 Queen을 ImagePanel로써 생성하고 퀸의 고유 속성을 가지고있는 클래스
@SuppressWarnings("serial")
public class Queen extends Chessman {
	
//	퀸의 생성자로써 흰색이면 흰색 퀸 이미지를 가진 퀸을 생성하고 아니면 검정 퀸 이미지를 가진 퀸을 생성한다.
	public Queen(boolean isWhite) {
//		부모클래스 Chessman의 생성자 호출을 통한 색상정보 저장 퀸은 칸을 직선 혹은 대각선으로 이동할수있는데
//		이때 퀸이 최대로 이동가능한 칸수가 7이라 판단해서 조상의 MOVEABLE_SQUARE을 7로 초기화해주었다.
		super(isWhite, 7);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_QUEEN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_QUEEN, ImageOption.MATCH_PARENT);
		}
	}
}
