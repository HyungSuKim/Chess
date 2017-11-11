package com.bfpaul.chess.chessman;

import java.awt.Image;

import com.bfpaul.chess.Images;

public class ChessmanImage {
	private ChessmanImage() {
	}

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
