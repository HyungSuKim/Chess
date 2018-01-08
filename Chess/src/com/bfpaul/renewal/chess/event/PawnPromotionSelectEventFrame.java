package com.bfpaul.renewal.chess.event;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.board.BoardSquare;
import com.bfpaul.renewal.chess.chessman.data.Bishop;
import com.bfpaul.renewal.chess.chessman.data.Chessman;
import com.bfpaul.renewal.chess.chessman.data.ChessmanImage;
import com.bfpaul.renewal.chess.chessman.data.ChessmanType;
import com.bfpaul.renewal.chess.chessman.data.Knight;
import com.bfpaul.renewal.chess.chessman.data.Queen;
import com.bfpaul.renewal.chess.chessman.data.Rook;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.frame.FlatDialog;
import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

/**
 * BoardPanel 위에서 Pawn이 지정된 y좌표에 도착했을 때(프로모션 할 수 있는 좌표) 생성되는 Frame이다.
 * 생성 시 폰이 위치하고있는 BoardSquare를 받아와서 폰이 프로모션 할 수 있도록 도와준다.
 * 우선 BoardSquare의 폰의 색상정보를 이용해서 프로모션 할 수있는 말들(퀸/비숍/나이트/룩)을 Frame에 이미지로써 표현해준다.
 * 사용자가 표현된 이미지를 선택함으로써 클래스는 BoardSquare에 있는 폰을 선택된 이미지의 말로 변경해서 셋팅해준다. 
 * 셋팅이 종료되면 이 Frame은 종료된다.
 * @author 김형수
 */
/* 클래스 작성 간 점검요소
 * 1. 올바른 클래스 이름인가? : 폰이 프로모션 하기위한 내용(이미지들)을 담고있고 BoardSquare에 프로모션 한 정보를 셋팅해주므로 View & Controller를 고민했다.
 * 하지만 이 클래스의 역할은 우선 프로모션을 하는 Frame을 띄우는 것이라 생각해서 Frame으로 이름을 선택했다. 
 * 2. 멤버변수의 존재 여부 : 2개
 * 		frame -> 사실 굳이 필요하지는 않다... 이미지를 선택했을때 창이 닫히게 하고싶어서 작성한 멤버변수 인데 없애고 싶다.
 * 		callBack -> 
 * 3. 클래스의 역할(Data/Controller/View) : View + Controller
 * 4. Data 공정과 공통로직의 분리 : private OnClickListener getListenerToPromotion 참고
 * 5. 접근제어자/한정자/자료형 : 
 * 6. inner class / abstract / interface : 아직은 해당사항 없음
 * 7. 프로그램의 구조 : 해당사항 없음.
 * 8. class간의 연관관계 및 필요조건 : 이 클래스는 BoardPanel과 연관되어있다. 하지만 이 클래스의 위치를 잡아주는데 GameFrame의 Width/Height에
 * 대하여 위치를 잡아주고 싶은데 해당부분은 아직 코딩을 하지 못하였고 고민중이다.
 * 9. 시점 : BoardPanel에서 폰이 정해진 좌표에 도달하면 생성되고 프로모션 할 말을 선택하면 사라진다.
 * 10.중복 동일 구조가 반복되지 않는가 : 해당없음
 * */
public class PawnPromotionSelectEventFrame {
	public interface OnPromotionSelectedListener {
		public void onSelected(ChessmanType chessmanType);
	}
	private FlatFrame frame = createFrame();
	
	private OnPromotionSelectedListener onPromotionSelectedListener = chessmanType -> {};
	
	public PawnPromotionSelectEventFrame(boolean isWhite) {
		
		JPanel container = frame.getContainer(); 

		container.add(createImagePanel(isWhite, ChessmanType.QUEEN), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		container.add(createImagePanel(isWhite, ChessmanType.BISHOP), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		container.add(createImagePanel(isWhite, ChessmanType.KNIGHT), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		container.add(createImagePanel(isWhite, ChessmanType.ROOK), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		
		frame.show();
	}
	
	public void setOnPromotionSelectedListener(OnPromotionSelectedListener onPromotionSelectedListener) {
		this.onPromotionSelectedListener = onPromotionSelectedListener;
	}
	
	private static FlatFrame createFrame() {
		FlatFrame frame = new FlatFrame();
		frame.setTitle("폰 프로모션");
		frame.setSize(400, 200);
		frame.setIconImage(Images.ICON);
		frame.setProcessIconImage(Images.ICON);
		frame.setTitleBarColor(Theme.TITLE_BAR_COLOR);
		frame.getContainer().setBackground(Theme.DARK_BLUE_COLOR);
		frame.getContainer().setLayout(new LinearLayout(10));
		frame.setLocationOnScreenCenter();
		return frame;
	}
	
	private FlatImagePanel createImagePanel(boolean isWhite, ChessmanType type) {
		FlatImagePanel imagePanel
		= new FlatImagePanel(ChessmanImage.getChessmanImage(isWhite, type), ImageOption.MATCH_PARENT);
		imagePanel.setBackground(Theme.LIGHT_BLUE_COLOR);
		imagePanel.setOpaque(true);
		imagePanel.setOnClickListener(comp -> {
			onPromotionSelectedListener.onSelected(type);
			
			new FlatDialog.Builder()
			.setLocationRelativeTo(frame.getContainer())
			.setTitle("알림")
			.setContent(type.name() + "프로모션 성공")
			.build().show();
			
			frame.hide();
		});
		return imagePanel;
	}
}
