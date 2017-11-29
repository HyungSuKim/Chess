package com.bfpaul.renewal.chess.board;

import java.awt.Color;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.King;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;

// 체스판의 하나의 네모칸으로써 체스말의 이동경로나 체스말을 놓거나 치우거나 하는 이벤트가 표현되는 가장 작은단위의 칸이다.
@SuppressWarnings("serial")
public class BoardSquare extends FlatImagePanel {
	private final Color originalColor;
	private Chessman chessman;
	private boolean isContain;

	BoardSquare(Color originalColor) {
		this.originalColor = originalColor;
		setEnableClickEvent(false);
		setLayout(new LinearLayout(0));
		setOpaque(true);
		setBackground(originalColor);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	// 가지고있는 체스말이 없으면 체스말을 네모칸위에 올려준다.
	public void setChessmanOnSquare(Chessman chessman) {
		if(getBackground().equals(Color.ORANGE) && chessman instanceof King) {
			System.out.println("체크메이트 된 칸으로 옮길수 없어요");
		} else {
			this.chessman = chessman;
			isContain = true;
			setEnableClickEvent(true);
			setImage(chessman.getChessmanImage(), ImageOption.MATCH_PARENT);
		}
	}

	void setSquareOriginalColor() {
		setBackground(originalColor);
		setAlpha(1.0f);
		if (isContain) {
			setEnableClickEvent(true);
		} else {
			setEnableClickEvent(false);
		}
	}

	void setSquareEventColor() {
		if(getBackground().equals(Color.ORANGE)) {
			setBackground(Color.ORANGE);
			setEnableClickEvent(true);
		} else {
			setBackground(Theme.LIGHT_BLUE_COLOR);
			setEnableClickEvent(true);
			setAlpha(0.6f);
		}
	}

	void setSquareAttackableColor() {
		setBackground(Color.RED);
		setEnableClickEvent(true);
		setAlpha(0.6f);
	}
	
	void setSquareCheckmateColor() {
		setBackground(Color.ORANGE);
		setEnableClickEvent(false);
		setAlpha(0.6f);
	}
	
	void setSquareCastlingColor() {
		setBackground(Color.YELLOW);
		setEnableClickEvent(true);
		setAlpha(0.6f);
	}

	// 체스말을 가지고있으면 체스말을 네모칸위에서 제거한다.
	void removeChessmanFromSquare() {
		chessman = null;
		isContain = false;
		setImage(null);
	}

	boolean isContain() {
		return isContain;
	}

	void setEnableClickEvent(boolean enable) {
		setEnabled(enable);
	}

	public Chessman getChessman() {
		return chessman;
	}
}
