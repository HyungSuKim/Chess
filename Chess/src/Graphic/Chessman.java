package Graphic;

import java.awt.Component;

import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

import Manager.Chessman.Type;
import Manager.Game;

class Chessman {

	FlatImagePanel getBlackKing(int x, int y, Game game) {
		FlatImagePanel blackKing = new FlatImagePanel(Images.BLACK_KING, ImageOption.MATCH_PARENT);
		Manager.King king = new Manager.King(x, y, false, game);
		blackKing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				king.vaildateMove();
			}

		});
		return blackKing;
	}

	FlatImagePanel getBlackQueen(int x, int y, Game game) {
		FlatImagePanel blackQueen = new FlatImagePanel(Images.BLACK_QUEEN, ImageOption.MATCH_PARENT);
		Manager.Queen queen = new Manager.Queen(x, y, Type.QUEEN, false, game);
		blackQueen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				queen.vaildateMove();
			}

		});
		return blackQueen;
	}

	FlatImagePanel getBlackBishop(int x, int y, Game game) {
		FlatImagePanel blackBishop = new FlatImagePanel(Images.BLACK_BISHOP, ImageOption.MATCH_PARENT);
		Manager.Bishop bishop = new Manager.Bishop(x, y, false, game);
		blackBishop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				bishop.vaildateMove();
			}

		});
		return blackBishop;
	}

	FlatImagePanel getBlackKnight(int x, int y, Game game) {
		FlatImagePanel blackKnight = new FlatImagePanel(Images.BLACK_KNIGHT, ImageOption.MATCH_PARENT);
		Manager.Knight knight = new Manager.Knight(x, y, false, game);
		blackKnight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				knight.vaildateMove();
			}

		});
		return blackKnight;
	}

	FlatImagePanel getBlackRook(int col, int row, Game game) {
		FlatImagePanel blackRook = new FlatImagePanel(Images.BLACK_ROOK, ImageOption.MATCH_PARENT);
		Manager.Rook rook = new Manager.Rook(col, row, false, game);
		blackRook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				rook.vaildateMove();
			}
		});
		return blackRook;
	}

	FlatImagePanel getBlackPawn(int x, int y, Game game) {
		FlatImagePanel blackPawn = new FlatImagePanel(Images.BLACK_PAWN, ImageOption.MATCH_PARENT);
		Manager.Pawn pawn = new Manager.Pawn(x, y, false, game);
		blackPawn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				pawn.vaildateMove();
			}
		});
		return blackPawn;
	}

	FlatImagePanel getWhiteKing(int x, int y, Game game) {
		FlatImagePanel whiteKing = new FlatImagePanel(Images.WHITE_KING, ImageOption.MATCH_PARENT);
		Manager.King king = new Manager.King(x, y, true, game);
		whiteKing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				king.vaildateMove();
			}
		});
		return whiteKing;
	}

	FlatImagePanel getWhiteQueen(int x, int y, Game game) {
		FlatImagePanel whiteQueen = new FlatImagePanel(Images.WHITE_QUEEN, ImageOption.MATCH_PARENT);
		Manager.Queen queen = new Manager.Queen(x, y, Type.QUEEN, true, game);
		whiteQueen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				queen.vaildateMove();
			}
		});
		return whiteQueen;
	}

	FlatImagePanel getWhiteBishop(int x, int y, Game game) {
		FlatImagePanel whiteBishop = new FlatImagePanel(Images.WHITE_BISHOP, ImageOption.MATCH_PARENT);
		Manager.Bishop bishop = new Manager.Bishop(x, y, true, game);
		whiteBishop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				bishop.vaildateMove();
			}
		});
		return whiteBishop;
	}

	FlatImagePanel getWhiteKnight(int x, int y, Game game) {
		FlatImagePanel whiteKnight = new FlatImagePanel(Images.WHITE_KNIGHT, ImageOption.MATCH_PARENT);
		Manager.Knight knight = new Manager.Knight(x, y, true, game);
		whiteKnight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				knight.vaildateMove();
			}
		});
		return whiteKnight;
	}

	FlatImagePanel getWhiteRook(int x, int y, Game game) {
		FlatImagePanel whiteRook = new FlatImagePanel(Images.WHITE_ROOK, ImageOption.MATCH_PARENT);
		Manager.Rook rook = new Manager.Rook(x, y, true, game);
		whiteRook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				rook.vaildateMove();
			}
		});
		return whiteRook;
	}

	FlatImagePanel getWhitePawn(int x, int y, Game game) {
		FlatImagePanel whitePawn = new FlatImagePanel(Images.WHITE_PAWN, ImageOption.MATCH_PARENT);
		Manager.Pawn pawn = new Manager.Pawn(x, y, true, game);
		whitePawn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				pawn.move();
			}
		});
		return whitePawn;
	}
}
