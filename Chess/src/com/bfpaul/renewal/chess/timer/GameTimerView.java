package com.bfpaul.renewal.chess.timer;

import java.awt.Component;

import javax.swing.border.EmptyBorder;

import com.bfpaul.chess.Theme;
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
// ���� ���۽� �÷��̾�(����� ������)�� Ÿ�̸Ӹ� �����ְ� ��ư�� �̿��ؼ� ���� �Ѱ��ְ��� �����Ͽ���.
@SuppressWarnings("serial")
public class GameTimerView extends FlatPanel {
	
	public GameTimerView() {
		setLayout(new LinearLayout(10));
		setBackground(Theme.DARK_BLUE_COLOR);
		setOpaque(true);
		
		add(new TimerView(true), createMatchParentConstraints(3));
		add(new TimerView(false), createMatchParentConstraints(3));
		add(createPhaseEndButtonPanel(), createMatchParentConstraints(1));
	}
	
//	������ ���Ը�ŭ�� ������ �����ϰ� �θ��� ������ ������ �����ִ� ���������� ��ȯ�Ѵ�. 
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	������ ��ư�� �г��� ���� ��ư�� ���� ����� ��ư�� �ϳ��� �гη� �����ַ��� �����Ͽ���.
	private FlatPanel createPhaseEndButtonPanel() {
		FlatPanel phaseEndButtonPanel = new FlatPanel(new LinearLayout(Orientation.VERTICAL, 0));
		phaseEndButtonPanel.add(createPhaseEndButtonInfo(), createMatchParentConstraints(8));
		phaseEndButtonPanel.add(createPhaseEndButton(), createMatchParentConstraints(15));
		return phaseEndButtonPanel;
	}
	
//	������ ��ư�� ���� ������ �󺧷ν� ������ش�
	private FlatLabel createPhaseEndButtonInfo() {
		FlatLabel phaseEndButtonInfo = new FlatLabel("����");
		phaseEndButtonInfo.setOpaque(false);
		phaseEndButtonInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		phaseEndButtonInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		phaseEndButtonInfo.setFont(Theme.BOLD_FONT_15PT);
		return phaseEndButtonInfo;
	}
	
//	������ ��ư�� ������ش�
	private FlatButton createPhaseEndButton() {
		FlatButton phaseEndButton = new FlatButton("����");
		phaseEndButton.setBackground(Theme.YELLOW_COLOR);
		phaseEndButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		phaseEndButton.setOnClickListener(new OnClickListener() {
//	������ ��ư�� Ŭ���ϸ� Ÿ�̸��� �۵� ����ġ�� ���¸� �ٲ��־ �ð��� �帣�� ���帣�� �Ϸ��� �ߴ�.
			@Override
			public void onClick(Component component) {
				((TimerView)getComponent(0)).timerOperateSwitch();
				((TimerView)getComponent(1)).timerOperateSwitch();
			}
			
		});
		return phaseEndButton;
	}
}
