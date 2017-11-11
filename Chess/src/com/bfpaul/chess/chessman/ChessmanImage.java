package com.bfpaul.chess.chessman;

import java.awt.Image;

import com.bfpaul.chess.Images;

// 체스말의 이미지를 메서드를 이용해서 가져오기 위한 클래스로써 따라서 생성자는 막아놓고
// 메서드만 static으로 선언해서 타 클래스에서 인스턴스를 생성하지 않고 메서드를 사용해서 이미지를 가져올수있다.
public class ChessmanImage {
	private ChessmanImage() {
	}
// 흰색인지 아닌지 그리고 ChessmanType형태의 type을 받아서 두가지 조건에 부합하는 이미지를 반환해준다
	public static Image getChessmanImage(boolean isWhite, ChessmanType type) {
		if (isWhite) {
			switch (type) {
			case KING:
				return Images.WHITE_KING;
			case QUEEN:
				return Images.WHITE_QUEEN;
			case BISHOP:
				return Images.WHITE_BISHOP;
			case KNIGHT:
				return Images.WHITE_KNIGHT;
			case ROOK:
				return Images.WHITE_ROOK;
			case PAWN:
				return Images.WHITE_PAWN;
			default:
				return Images.WHITE_KING;
			}
		} else {
			switch (type) {
			case KING:
				return Images.BLACK_KING;
			case QUEEN:
				return Images.BLACK_QUEEN;
			case BISHOP:
				return Images.BLACK_BISHOP;
			case KNIGHT:
				return Images.BLACK_KNIGHT;
			case ROOK:
				return Images.BLACK_ROOK;
			case PAWN:
				return Images.BLACK_PAWN;
			default:
				return Images.BLACK_KING;
			}
		}
	}
}
