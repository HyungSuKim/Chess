package com.bfpaul.chess.timer;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.flat.text.textarea.alignment.FlatHorizontalAlignment;
import com.mommoo.flat.text.textarea.alignment.FlatVerticalAlignment;
// �ϳ��� Ÿ�̸Ӹ� �����ָ� �ϳ��� Ÿ�̸��� �ð������� �������ִ�.
@SuppressWarnings("serial")
class TimerView extends FlatPanel {
//  Ÿ�̸Ӱ� ����� Ÿ�̸����� �ƴ���
	private boolean isWhite;
//	Ÿ�̸ӿ� �ð������� ���� �ϱ����ؼ� �����ߴ�
	private Timer timer;

	TimerView(boolean isWhite) {
		this.isWhite = isWhite;
		timer = new Timer(this);
		
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		
		add(createTimerInfo(), createMatchParentConstraints(8));
		add(createTimerLabel(), createMatchParentConstraints(15));
		timer.setOnTimerView();

	}
//	������ ���Ը�ŭ�� ������ �����ϰ� �θ��� ������ ������ �����ִ� ���������� ��ȯ�Ѵ�.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	Ÿ�̸��� ������ ������� �ƴ��� �ľ��ؼ� �ٸ��� ������ش�
	private FlatLabel createTimerInfo() {
		FlatLabel timerInfo = new FlatLabel();
		
		if(isWhite) {
			timerInfo.setText("ȭ��Ʈ(��:��)");
		} else {
			timerInfo.setText("��(��:��)");
		}
		
		timerInfo.setOpaque(false);
		timerInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		timerInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		timerInfo.setFont(Theme.BOLD_FONT_15PT);
		return timerInfo;
	}
//	�ð��� ������ Ÿ�̸Ӷ��� ������ش�
	private FlatLabel createTimerLabel() {
		FlatLabel timerLabel = new FlatLabel();
		timerLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		timerLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		timerLabel.setBackground(Theme.LIGHT_BLUE_COLOR);
		timerLabel.setFont(Theme.BOLD_FONT_30PT);
		return timerLabel;
	}
	
//	����� Ÿ�̸����� �ƴ����� ��ȯ�Ѵ�.
	boolean getIsWhite() {
		return isWhite;
	}
//	Ÿ�̸��� ����ġ�� �۵���Ų��.
	void timerOperateSwitch() {
		timer.timerOperateSwitch();
	}
//	�ð��� Ÿ�̸Ӷ󺧿� �������ش�
	void setTimeOnTimerLabel(String time) {
		((FlatLabel)getComponent(1)).setText(time);
	}
}
