package com.bfpaul.renewal.chess.board;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.frame.FlatFrame;

public class BoardTest {
	public static void main(String[] args) {
		FlatFrame originalFrame = new FlatFrame();
		BoardPanel boardPanel = new BoardPanel();
		originalFrame.setSize(800, 800);
		originalFrame.getContainer().setLayout(new BorderLayout());
		originalFrame.getContainer().add(boardPanel);
		originalFrame.show();
	}
}
