package com.bfpaul.renewal.chess.board;

import java.awt.BorderLayout;

import com.mommoo.flat.frame.FlatFrame;

public class BoardTest {
	public static void main(String[] args) {
		FlatFrame frame = new FlatFrame();
		frame.setSize(800, 800);
		frame.getContainer().setLayout(new BorderLayout());
		frame.getContainer().add(new BoardPanel());
		frame.show();
	}
}
