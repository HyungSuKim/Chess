package com.bfpaul.chess.chessman.bishop;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman(체스말) 패키지에 포함된 bishop을 해주고 그 bishop의 속성을 가지고있는 클래스이다.
@SuppressWarnings("serial")
public class Bishop extends Chessman {
	
//	비숍의 생성자인데 흰색이면(true) 검정잭이면 (false)를 받아서 이를 이용해서 흰색 또는 검정색 비숍이미지를 가져와서 적용시켜준다.
	public Bishop(boolean isWhite) {
//	부모클래스 Chessman의 생성자 호출을 통한 색상정보 및 비숍이 이동가능한 square의 갯수를 저장한다.
//	비숍은 대각선으로 이동하기는 하지만 최대로 무조건 7칸을 움직일 수 있다는 생각에서 7을 조상의 생성자에 파라미터로 전달하였다.
		super(isWhite, 7);
		
		if(isWhite) {
			setImage(Images.WHITE_BISHOP, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_BISHOP, ImageOption.MATCH_PARENT);
		}
	}
}
