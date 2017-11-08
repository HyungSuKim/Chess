package com.bfpaul.chess.board;

import java.awt.BorderLayout;

import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.util.ScreenManager;

public class BoardTest {
	public static void main(String[] args) {
		FlatFrame frame = new FlatFrame();
		ScreenManager screenManager = ScreenManager.getInstance();
		Board board = new Board();
//		EventViewer eventViewer = new EventViewer();
		
		frame.setSize(screenManager.dip2px(500), screenManager.dip2px(500));
		frame.getContainer().setLayout(new BorderLayout());
		frame.getContainer().add(board);
//		board.add(eventViewer);
//		board.setLayout(new BorderLayout());
//		board.add(new EventViewer());
		
		frame.show();
		
		System.out.println("����" + board.getSize());
		
//		System.out.println("���" + eventViewer.getSize());
		
		System.out.println("������� ����" + board.getBoard().getSize());
		
	}
}
