package com.bfpaul.chess.chessman.rook;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

@SuppressWarnings("serial")
public class Rook extends FlatImagePanel {
	
	private Properties properties;
	
	public Rook(boolean isWhite) {
		
		properties = new Properties(isWhite);
		
		if(properties.getIsWhite()) {
			setImage(Images.WHITE_ROOK, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_ROOK, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
	public Properties getProperties() {
		return properties;
	}
}
