package com.bfpaul.chess.chessman.pawn;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

@SuppressWarnings("serial")
public class Pawn extends FlatImagePanel {
	private final int MOVEABLE = 1;
	private final int FIRST_MOVEABLE = 2;
	
	private boolean isWhite;
	private boolean isMoved;
	
	public Pawn(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_PAWN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_PAWN, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
	public int getMoveable() {
		if(!isMoved) {
			return FIRST_MOVEABLE;
		} else {
			return MOVEABLE;
		}
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