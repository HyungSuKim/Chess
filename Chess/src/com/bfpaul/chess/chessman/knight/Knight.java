package com.bfpaul.chess.chessman.knight;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

@SuppressWarnings("serial")
public class Knight extends FlatImagePanel {
	
	private final int MOVEABLE = 3;
	
	private boolean isWhite;
	
	public Knight(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_KNIGHT, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_KNIGHT, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
	public int getMoveable() {
		return MOVEABLE;
	}
	
	public boolean getIsWhite() {
		return isWhite;
	}
}
