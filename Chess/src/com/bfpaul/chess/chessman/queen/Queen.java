package com.bfpaul.chess.chessman.queen;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
//chessman 패키지 내에서 Queen을 ImagePanel로써 생성하고 퀸의 고유 속성을 가지고있는 클래스
@SuppressWarnings("serial")
public class Queen extends FlatImagePanel {
//	퀸은 칸을 직선 혹은 대각선으로 이동할수있는데 이때 퀸이 최대로 이동가능한 칸수가 7이라 판단해서 상수로써 7을 선언해줬다.
	private final int MOVEABLE_SQUARE = 7;
//	말이 흰색인지 아닌지 정보를 가지고있는 변수
	private boolean isWhite;
	
//	퀸의 생성자로써 흰색이면 흰색 퀸 이미지를 가진 퀸을 생성하고 아니면 검정 퀸 이미지를 가진 퀸을 생성한다.
	public Queen(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_QUEEN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_QUEEN, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
//	움직일 수 있는 칸 수를 반환하는 메서드
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
//	흰색인지 아닌지 반환하는 메서드
	public boolean getIsWhite() {
		return isWhite;
	}
}
