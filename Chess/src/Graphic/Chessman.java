package Graphic;

import java.awt.Component;

import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

import Manager.Game;

class Chessman {
	private static Images images = new Images();
	
	FlatImagePanel getBlackKing(int x, int y, Game game) {
		FlatImagePanel blackKing = new FlatImagePanel(images.getBlackKingImage(), ImageOption.MATCH_PARENT);
		Manager.King king = new Manager.King(x, y, "King", false, game);
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
		FlatImagePanel blackQueen = new FlatImagePanel(images.getBlackQueenImage(), ImageOption.MATCH_PARENT);
		Manager.Queen queen = new Manager.Queen(x, y, "Queen", false, game);
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
		FlatImagePanel blackBishop = new FlatImagePanel(images.getBlackBishopImage(), ImageOption.MATCH_PARENT);
		Manager.Bishop bishop = new Manager.Bishop(x, y, "Bishop", false, game);
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
		FlatImagePanel blackKnight = new FlatImagePanel(images.getBlackKnightImage(), ImageOption.MATCH_PARENT);
		Manager.Knight knight = new Manager.Knight(x, y, "Knight", false, game);
		blackKnight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				knight.vaildateMove();
			}
			
		});
		return blackKnight;
	}
	
	FlatImagePanel getBlackRook(int x, int y, Game game) {
		FlatImagePanel blackRook = new FlatImagePanel(images.getBlackRookImage(), ImageOption.MATCH_PARENT);
		Manager.Rook rook = new Manager.Rook(x, y, "Rook", false, game);
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
		FlatImagePanel blackPawn = new FlatImagePanel(images.getBlackPawnImage(), ImageOption.MATCH_PARENT);
		Manager.Pawn pawn = new Manager.Pawn(x, y, "Pawn", false, game);
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
		FlatImagePanel whiteKing = new FlatImagePanel(images.getWhiteKingImage(), ImageOption.MATCH_PARENT);
		Manager.King king = new Manager.King(x, y, "King", true, game);
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
		FlatImagePanel whiteQueen = new FlatImagePanel(images.getWhiteQueenImage(), ImageOption.MATCH_PARENT);
		Manager.Queen queen = new Manager.Queen(x, y, "Queen", true, game);
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
		FlatImagePanel whiteBishop = new FlatImagePanel(images.getWhiteBishopImage(), ImageOption.MATCH_PARENT);
		Manager.Bishop bishop = new Manager.Bishop(x, y, "Bishop", true, game);
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
		FlatImagePanel whiteKnight = new FlatImagePanel(images.getWhiteKnightImage(), ImageOption.MATCH_PARENT);
		Manager.Knight knight = new Manager.Knight(x, y, "Knight", true, game);
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
		FlatImagePanel whiteRook = new FlatImagePanel(images.getWhiteRookImage(), ImageOption.MATCH_PARENT);
		Manager.Rook rook = new Manager.Rook(x, y, "Rook", true, game);
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
		FlatImagePanel whitePawn = new FlatImagePanel(images.getWhitePawnImage(), ImageOption.MATCH_PARENT);
		Manager.Pawn pawn = new Manager.Pawn(x, y, "Pawn", true, game);
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
