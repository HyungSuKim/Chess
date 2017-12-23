package com.bfpaul.renewal.chess.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.JDialog;

import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

/**
 * BoardPanel ������ Ư���� ������/����(EnPassant, Castling, Check)�� �ð������� ǥ�����ֱ����� ���̴�.
 * �� Ŭ������ �Ǵٸ� �ϳ��� Frame���ν��� ���� ���ٴ� BoardPanel���� �������� Dialog �� ������ �������ִ�.
 * �� Ŭ������ ���������� ���¿����� �̹����� �޾� �ش� �̹����� ���� ������ Dialog�� �����Ѵ�. 
 * 
 * �� ���̾�α׸� �������ʰ�(�ݴ�) ������� 2������ �����ߴµ�
 * 1. ���̾�α׸� Ŭ���ϸ� �ݴ´�
 * 2. ���̾�αװ� �����ð��� ������ �ڵ����� ������
 * 
 * 2�� ����� �����ϴµ� ���� ���� �� ����� �ʿ��� �� ����.
 * @author ������
 */

@SuppressWarnings("serial")
class BoardEventInfoView extends JDialog {
	BoardEventInfoView(Image image) {
		FlatImagePanel imagePanel = new FlatImagePanel(image, ImageOption.MATCH_PARENT);
		
		setSize(400, 200);
		setUndecorated(true);
		
		setBackground(new Color(0, 255, 0, 0));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(imagePanel, BorderLayout.CENTER);
		
		imagePanel.setOnClickListener( component -> { setVisible(false); } );
		
		setVisible(true);
		
//		this.run();
	}
	
//	@SuppressWarnings("static-access")
//	void run() {
//		try {
//			Thread timerThread = new Thread();
//			timerThread.sleep(500);
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			setVisible(false);
//		}
//	}
}
