package com.bfpaul.chess.chessman.queen;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

@SuppressWarnings("serial")
public class Queen extends FlatImagePanel {
	private final int MOVEABLE = 7;
	
	private boolean isWhite;
	
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
	
	public int getMoveable() {
		return MOVEABLE;
	}
	
	public boolean getIsWhite() {
		return isWhite;
	}
}