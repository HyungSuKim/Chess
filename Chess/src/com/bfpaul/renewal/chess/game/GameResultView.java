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
 * GameResultView�� ������ ����� ��ν� ǥ�����ֱ����� Ŭ�����̴�.
 * GameResultType���� ����ߵ��� ������ ����� ��Ÿ�� �� �ִ� ������ ����  6�����̴�.
 * �� Ŭ������ ���� ������ �������̴� ������� ��������(���/������)�� GameResultType�� �޾�
 * ���������� �ʿ��� ������ ��ģ�� ����ڿ��� �º��� ����� ��ν� �����ش�.
 * 
 * ���� : ����� ���̾�α׸� �̿��ؼ� �ؽ�Ʈ�� ����ϵ��� �ڵ��Ǿ��ִ�. ������ ������ ������ �����ϴ� ������ �̹����� �ٲٷ��� �������̴�.
 * @author ������
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
		FlatButton okButton = new FlatButton("Ȯ��");
		
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
		
		winner = isWhite ? "��� �÷��̾�" : "������ �÷��̾�";
		loser = !isWhite ? "��� �÷��̾�" : "������ �÷��̾�";
		
		return winner + " �¸�!" + System.lineSeparator()
		+ loser + " �й�!" + System.lineSeparator()
		 + System.lineSeparator() + "Ȯ�� �� ������ �����մϴ�.";
	}
	
	private String getDrawContent() {
		return "���º��Դϴ�." + System.lineSeparator()
		 + System.lineSeparator() + "Ȯ�� �� ������ �����մϴ�.";
	}
}
