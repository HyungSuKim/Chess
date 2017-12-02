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
		
//		Graphics graphics = originalFrame.getContainer().getComponent(0).getGraphics();
//		Graphics2D graphics2D = (Graphics2D)graphics;
//		graphics2D.rotate(Math.toRadians(180));
//
//		FlatFrame rotateFrame = new FlatFrame();
//		FlatPanel rotatePanel = new FlatPanel();
//		rotateFrame.setSize(800, 800);
//		rotateFrame.getContainer().setLayout(new BorderLayout());
//		
//		rotateFrame.getContainer().add(rotatePanel);
//		rotatePanel.paint((Graphics)graphics2D);
//		System.out.println(rotatePanel.isPaintingForPrint());
//		rotatePanel.repaint();
//		rotateFrame.show();
	}
}
