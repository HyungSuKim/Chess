package com.bfpaul.chess.chessman.rook;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

@SuppressWarnings("serial")
public class Rook extends FlatImagePanel {
	
	final int MOVEABLE = 7;
	
	private boolean isWhite;
	private boolean isMoved;
	
	public Rook(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_ROOK, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_ROOK, ImageOption.MATCH_PARENT);
		}
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
	public boolean getIsWhite() {
		return isWhite;
	}
	
	public int getMoveable() {
		return MOVEABLE;
	}
	
	public void setIsMoved() {
		isMoved = true;
	}
	
	public boolean getIsMoved() {
		return isMoved;
	}

}
