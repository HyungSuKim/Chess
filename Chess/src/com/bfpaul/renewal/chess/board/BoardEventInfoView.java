package com.bfpaul.renewal.chess.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.JDialog;

import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

/**
 * BoardPanel 위에서 특수한 움직임/상태(EnPassant, Castling, Check)를 시각적으로 표현해주기위한 뷰이다.
 * 이 클래스는 또다른 하나의 Frame으로써의 역할 보다는 BoardPanel위에 보여지는 Dialog 적 성격을 가지고있다.
 * 이 클래스는 생성시점에 상태에대한 이미지를 받아 해당 이미지를 갖는 투명한 Dialog를 제공한다. 
 * 
 * 이 다이얼로그를 보이지않게(닫는) 방법으로 2가지를 생각했는데
 * 1. 다이얼로그를 클릭하면 닫는다
 * 2. 다이얼로그가 일정시간이 지나면 자동으로 닫힌다
 * 
 * 2번 기능을 구현하는데 아직 조금 더 고민이 필요할 것 같다.
 * @author 김형수
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
