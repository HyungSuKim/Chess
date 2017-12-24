package com.bfpaul.renewal.chess.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JDialog;

import com.bfpaul.renewal.chess.game.GameFrame;
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

/* 클래스 작성 간 점검요소
 * 1. 올바른 클래스 이름인가? : Board에서 발생하는 3개의 이벤트 정보를 이미지를 이용해서 보여주고자 작성한 클래스
 * 내부적으로 별도의 데이터 공정이 일어나지 않고 다른 클래스들의 데이터 또한 변경하지 않는다. 따라서 View로써 적합하다고 판단했다.
 * 2. 멤버변수의 존재 여부 : 없음.
 * 3. 클래스의 역할(Data/Controller/View) : View
 * 4. Data 공정과 공통로직의 분리 : 해당사항 없음.
 * 5. 접근제어자/한정자/자료형 : 해당 클래스는 동일패키지 (board) 내의 BoardPanel에서 생성 된다. 따라서 접근제어자는 default로 판단했다.
 * 멤버변수가 따로없으므로 한정자는 해당하지 않는다. 자료형은 View에 맞는 자료형을 사용하였다고 생각한다.
 * 6. inner class / abstract / interface : 아직은 해당사항 없음
 * 7. 프로그램의 구조 : 해당사항 없음. ( 고민중 )
 * 8. class간의 연관관계 및 필요조건 : 이 클래스는 BoardPanel과 연관되어있다. 하지만 이 클래스의 위치를 잡아주는데 GameFrame의 Width/Height에
 * 대하여 위치를 잡아주고 싶은데 해당부분은 아직 코딩을 하지 못하였고 고민중이다.
 * 9. 시점 : BoardPanel에서 처리해야할 이벤트들(EnPassant/Castling/Check)가 발생하면 생성되고 사라진다. (현재는 클릭시 사라지고 
 * 일정 시간 뒤 사라지는 것 추가예정)
 * 10.중복 동일 구조가 반복되지 않는가 : 해당없음
 * */


@SuppressWarnings("serial")
public class BoardEventInfoView extends JDialog {
	
	BoardEventInfoView(Image image) {
		FlatImagePanel imagePanel = new FlatImagePanel(image, ImageOption.MATCH_PARENT);
		
		setSize(400, 200);
		setUndecorated(true);
		
		setBackground(new Color(0, 0, 0, 0));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(imagePanel, BorderLayout.CENTER);
		
		imagePanel.setOnClickListener( component -> { setVisible(false); } );
		
		setVisible(true);
		
		System.out.println(getSize().toString());
		System.out.println(getLocation().toString());
		System.out.println(getBounds().toString());
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
