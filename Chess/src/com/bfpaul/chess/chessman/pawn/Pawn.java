package com.bfpaul.chess.chessman.pawn;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
// chessman 패키지 내에서 pawn을 ImagePanel로써 생성하고 폰의 고유 속성을 가지고있는 클래스
@SuppressWarnings("serial")
public class Pawn extends FlatImagePanel {
//	첫 움직임을 수행하고 나서 움직일수 있는 칸수를 나타내는 상수, 이는 공격도 대각선 1칸 이동으로 생각해서 1로 선언해주었다. 
	private final int MOVEABLE_SQUARE = 1;
//	첫 움직임에서 움직일수 있는 칸수는 2칸이므로 이를 나타내기위하여 선언한 상수
	private final int FIRST_MOVEABLE_SQUARE = 2;
	
//	말이 흰색인지 아닌지 정보를 가지고있는 변수
	private boolean isWhite;
//	말이 움직였는지 아닌지 정보를 가지고있는 변수
	private boolean isMoved;
	
//	폰의 생성자로써 흰색이면 흰색 폰 이미지를 가진 폰을 생성하고 아니면 검정 폰 이미지를 가진 폰을 생성한다.
	public Pawn(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_PAWN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_PAWN, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	폰이 움직일 수 있는 square의 수를 반환하는 메서드로써 만약에 움직임이 없었으면 첫움직임에서 움직일수 있는 칸수를 반환하고
//	첫 움직임이 있었다면 그 이후에 움직일수있는 칸수를 반환한다.
	public int getMoveableSquare() {
		if(!isMoved) {
			return FIRST_MOVEABLE_SQUARE;
		} else {
			return MOVEABLE_SQUARE;
		}
	}
	
//	흰색인지 아닌지 반환하는 메서드
	public boolean getIsWhite() {
		return isWhite;
	}
	
//	첫 움직임 이후 움직였음을 설정해주기위한 메서드
	public void setIsMoved() {
		isMoved = true;
	}
	
//	isMoved를 반환받아서 움직임이 있었는지 없엇는지 확인하기 위한 메서드
	public boolean getIsMoved() {
		return isMoved;
	}
}