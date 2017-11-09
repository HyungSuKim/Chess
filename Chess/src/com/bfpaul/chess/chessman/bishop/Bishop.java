package com.bfpaul.chess.chessman.bishop;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

@SuppressWarnings("serial")
public class Bishop extends FlatImagePanel {
	
	private final int MOVEABLE = 7;
	
	private boolean isWhite;
	
	public Bishop(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		if(isWhite) {
			setImage(Images.WHITE_BISHOP, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_BISHOP, ImageOption.MATCH_PARENT);
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
