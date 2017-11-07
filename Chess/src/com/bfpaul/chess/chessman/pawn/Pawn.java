package com.bfpaul.chess.chessman.pawn;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

@SuppressWarnings("serial")
public class Pawn extends FlatImagePanel {
	
	private Properties properties;
	
	public Pawn(boolean isWhite) {
		
		properties = new Properties(isWhite);
		
		if(properties.getIsWhite()) {
			setImage(Images.WHITE_PAWN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_PAWN, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
	public Properties getProperties() {
		return properties;
	}
}