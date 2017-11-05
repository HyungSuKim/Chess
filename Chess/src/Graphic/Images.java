package Graphic;

import java.awt.Image;
import java.awt.Toolkit;

/*images use in program*/
class Images {
	/*Image for Intro and Icons*/
	private static final Image INTRO_IMAGE =  Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\Intro\\Intro.png");
	private static final Image ICON_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\Icon\\Icon.png");
	
	/*Black Chessman*/
	private static final Image BLACK_KING_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\BlackChessman\\Black_King.png");
	private static final Image BLACK_QUEEN_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\BlackChessman\\Black_Queen.png");
	private static final Image BLACK_BISHOP_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\BlackChessman\\Black_Bishop.png");
	private static final Image BLACK_KNIGHT_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\BlackChessman\\Black_Knight.png");
	private static final Image BLACK_ROOK_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\BlackChessman\\Black_Rook.png");
	private static final Image BLACK_PAWN_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\BlackChessman\\Black_Pawn.png");
	
	/*White Chessman*/
	private static final Image WHITE_KING_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\WhiteChessman\\White_King.png");
	private static final Image WHITE_QUEEN_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\WhiteChessman\\White_Queen.png");
	private static final Image WHITE_BISHOP_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\WhiteChessman\\White_Bishop.png");
	private static final Image WHITE_KNIGHT_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\WhiteChessman\\White_Knight.png");
	private static final Image WHITE_ROOK_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\WhiteChessman\\White_Rook.png");
	private static final Image WHITE_PAWN_IMAGE = Toolkit.getDefaultToolkit().getImage("..\\Chess\\lib\\Image\\WhiteChessman\\White_Pawn.png");
	
	Image getIntroImage() {
		return INTRO_IMAGE;
	}
	
	Image getIconImage() {
		return ICON_IMAGE;
	}
	
	/*Black Chessman*/
	Image getBlackKingImage() {
		return BLACK_KING_IMAGE;
	}
	
	Image getBlackQueenImage() {
		return BLACK_QUEEN_IMAGE;
	}
	
	Image getBlackBishopImage() {
		return BLACK_BISHOP_IMAGE;
	}
	
	Image getBlackKnightImage() {
		return BLACK_KNIGHT_IMAGE;
	}
	
	Image getBlackRookImage() {
		return BLACK_ROOK_IMAGE;
	}
	
	Image getBlackPawnImage() {
		return BLACK_PAWN_IMAGE;
	}
	
	/*White Chessman*/
	Image getWhiteKingImage() {
		return WHITE_KING_IMAGE;
	}
	
	Image getWhiteQueenImage() {
		return WHITE_QUEEN_IMAGE;
	}
	
	Image getWhiteBishopImage() {
		return WHITE_BISHOP_IMAGE;
	}
	
	Image getWhiteKnightImage() {
		return WHITE_KNIGHT_IMAGE;
	}
	
	Image getWhiteRookImage() {
		return WHITE_ROOK_IMAGE;
	}
	
	Image getWhitePawnImage() {
		return WHITE_PAWN_IMAGE;
	}
}
