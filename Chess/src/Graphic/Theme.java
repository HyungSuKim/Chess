package Graphic;

import java.awt.Color;
import java.awt.Font;

import com.mommoo.util.FontManager;

/* Theme(Color and Fonts) use in program */
class Theme {
	
	private static final Color TITLE_BAR_COLOR = new Color(75, 106, 150);
	
	private static final Color DARK_BLUE_COLOR = new Color(95, 126, 170);
	private static final Color LIGHT_BLUE_COLOR = new Color(162, 189, 225);
	private static final Color YELLOW_COLOR = new Color(255, 182, 42);
	
	private static final Color BOARD_BORDER_COLOR = new Color(143, 112, 55);
	private static final Color BOARD_DARK_SQUARE_COLOR = new Color(102, 80, 39);
	private static final Color BOARD_LIGHT_SQUARE_COLOR = new Color(196, 169, 118);
	
	private static final Font BOLD_FONT_15PT = FontManager.getNanumGothicFont(Font.BOLD, 15);
//	255, 182, 42 (´©¸®³¢¸®ÇÑ»ö)
//	75, 106, 150 (ÁøÇÑ Çª¸£µùµù)
//	192, 219, 255 (¹àÀº Çª¸£µùµù)
//	private static final Color PROCESS_BAR_COLOR = new Color()
	
	Color getTitleBarColor() {
		return TITLE_BAR_COLOR;
	}
	
	Color getDarkBlueColor() {
		return DARK_BLUE_COLOR;
	}
	
	Color getLightBlueColor() {
		return LIGHT_BLUE_COLOR;
	}
	
	Color getYellowColor() {
		return YELLOW_COLOR; 
	}
	
	Color getBoardBorderColor() {
		return BOARD_BORDER_COLOR;
	}
	
	Color getBoardDarkSquareColor() {
		return BOARD_DARK_SQUARE_COLOR;
	}
	
	Color getBoardLightSquareColor() {
		return BOARD_LIGHT_SQUARE_COLOR;
	}
	
	Font getBoldFont15Pt() {
		return BOLD_FONT_15PT;
	}
}
