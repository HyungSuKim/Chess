package com.bfpaul.renewal.chess.board;

import java.awt.BorderLayout;

import com.bfpaul.renewal.chess.game.CurrentChessmanView;
import com.bfpaul.renewal.chess.game.GameHelper;
import com.mommoo.flat.frame.FlatFrame;

public class BoardTest {
	public static void main(String[] args) {
		FlatFrame originalFrame = new FlatFrame();

		BoardPanel boardPanel = new BoardPanel(new GameHelper(new CurrentChessmanView()), true);
		
		
		originalFrame.setSize(800, 800);
		originalFrame.getContainer().setLayout(new BorderLayout());
		originalFrame.getContainer().add(boardPanel);
		originalFrame.show();
		
	}
}
