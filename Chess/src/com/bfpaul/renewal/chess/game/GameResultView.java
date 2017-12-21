package com.bfpaul.renewal.chess.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JDialog;

import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.flat.text.textarea.alignment.FlatHorizontalAlignment;
import com.mommoo.flat.text.textarea.alignment.FlatVerticalAlignment;
import com.mommoo.util.FontManager;

/**
 * GameResultView는 게임의 결과를 뷰로써 표현해주기위한 클래스이다.
 * GameResultType에서 언급했듯이 게임의 결과로 나타날 수 있는 가지의 수는  6가지이다.
 * 이 클래스는 현재 게임을 진행중이던 사용자의 색상정보(흰색/검정색)와 GameResultType를 받아
 * 내부적으로 필요한 로직을 거친뒤 사용자에게 승부의 결과를 뷰로써 보여준다.
 * 
 * 참고 : 현재는 다이얼로그를 이용해서 텍스트를 출력하도록 코딩되어있다. 하지만 디자인 적으로 부족하다 느껴서 이미지로 바꾸려고 생각중이다.
 * @author 김형수
 */
@SuppressWarnings("serial")
public class GameResultView extends JDialog {
	
	private static final Font PLAIN_FONT_20PT = FontManager.getNanumGothicFont(Font.PLAIN, 20);
	private static final Font BOLD_FONT_30PT = FontManager.getNanumGothicFont(Font.BOLD, 30);
	
	public GameResultView(boolean isWhite, GameResultType resultType) {
		
		setSize(400, 300);
		setUndecorated(true);
		getContentPane().setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		getContentPane().setBackground(new Color(0, 255, 0, 0));
		
		getContentPane().add(createTitleLabel(resultType), getMatchParentConstraints(2));
		
		getContentPane().add(createContentLabel(isWhite, resultType), getMatchParentConstraints(4));
		
		getContentPane().add(createContainButtonPanel(), getMatchParentConstraints(1));
		setVisible(true);
	}
	
	private LinearConstraints getMatchParentConstraints(int weight) {
		return new LinearConstraints(weight, LinearSpace.MATCH_PARENT);
	}
	
	private FlatPanel createContainButtonPanel() {
		FlatPanel buttonPanel = new FlatPanel(new LinearLayout(0));
		FlatPanel emptyPanel = new FlatPanel();
		
		buttonPanel.setBackground(new Color(0, 255, 0, 0));
		emptyPanel.setBackground(new Color(0, 255, 0, 0));

		
		buttonPanel.add(emptyPanel, getMatchParentConstraints(2));
		buttonPanel.add(createOKButton(), getMatchParentConstraints(1));
		
		return buttonPanel;
	}
	
	private FlatButton createOKButton() {
		FlatButton okButton = new FlatButton("확인");
		
		okButton.setBackground(Color.BLUE);
		
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				setVisible(false);
			}
		});
		return okButton;
	}
	
	private FlatLabel createTitleLabel(GameResultType resultType) {
		FlatLabel resultTitle = new FlatLabel(resultType.getResult());
		
		resultTitle.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		resultTitle.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		resultTitle.setFont(BOLD_FONT_30PT);
		resultTitle.setBackground(Color.green);
		
		return resultTitle;
	}
	
	private FlatLabel createContentLabel(boolean isWhite, GameResultType resultType) {
		FlatLabel resultContent = new FlatLabel(getResultContent(isWhite, resultType));
		
		resultContent.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		resultContent.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		resultContent.setFont(PLAIN_FONT_20PT);
		
		return resultContent;
	}
	
	private String getResultContent(boolean isWhite, GameResultType resultType) {
		switch(resultType) {
		case CHECKMATE : return getWinContent(isWhite);
		case STALEMATE : return getDrawContent();
		case RESIGN : return getWinContent(isWhite);
		case FIFTY_COUNT : return getDrawContent();
		case THREE_FOLD_REPETITION : return getDrawContent();
		case TIMER_OUT : return getWinContent(isWhite);
		default : return new String();
		}
	}
	
	private String getWinContent(boolean isWhite) {
		String winner;
		String loser;
		
		winner = isWhite ? "흰색 플레이어" : "검은색 플레이어";
		loser = !isWhite ? "흰색 플레이어" : "검은색 플레이어";
		
		return winner + " 승리!" + System.lineSeparator()
		+ loser + " 패배!" + System.lineSeparator()
		 + System.lineSeparator() + "확인 시 게임을 종료합니다.";
	}
	
	private String getDrawContent() {
		return "무승부입니다." + System.lineSeparator()
		 + System.lineSeparator() + "확인 시 게임을 종료합니다.";
	}
}
