package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;

import com.bfpaul.chess.Images;

// 12개의 체스말의 이미지를 isWhite와 type을 이용해서 조건에 부합하는 이미지를 편리하게 가져오고자 작성된 클래스
public class ChessmanImage {
	private ChessmanImage() {
	}
// 두가지 조건에 부합하는 이미지를 반환해준다
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
