package com.bfpaul.chess.chessman.king;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

@SuppressWarnings("serial")
public class King extends FlatImagePanel {
	
	final int MOVEABLE = 1;
	private boolean isWhite;
	private boolean isMoved;
	
	public King(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_KING, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_KING, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
	public int getMoveable() {
		return MOVEABLE;
	}
	
	public boolean getIsWhite() {
		return isWhite;
	}
	
	public void setIsMoved() {
		isMoved = true;
	}
	
	public boolean getIsMoved() {
		return isMoved;
	}
}
