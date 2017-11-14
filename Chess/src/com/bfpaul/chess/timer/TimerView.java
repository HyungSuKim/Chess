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
// 하나의 타이머를 보여주며 하나의 타이머의 시간정보를 가지고있다.
@SuppressWarnings("serial")
class TimerView extends FlatPanel {
//  타이머가 흰색의 타이머인지 아닌지
	private boolean isWhite;
//	타이머에 시간정보를 갖게 하기위해서 선언했다
	private Timer timer;

	TimerView(boolean isWhite) {
		this.isWhite = isWhite;
		timer = new Timer(this);
		
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		
		add(createTimerInfo(), createMatchParentConstraints(8));
		add(createTimerLabel(), createMatchParentConstraints(15));
		timer.setOnTimerView();

	}
//	정해진 무게만큼의 영역을 차지하고 부모의 영역에 영역을 맞춰주는 제약조건을 반환한다.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	타이머의 정보를 흰색인지 아닌지 파악해서 다르게 만들어준다
	private FlatLabel createTimerInfo() {
		FlatLabel timerInfo = new FlatLabel();
		
		if(isWhite) {
			timerInfo.setText("화이트(분:초)");
		} else {
			timerInfo.setText("블랙(분:초)");
		}
		
		timerInfo.setOpaque(false);
		timerInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		timerInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		timerInfo.setFont(Theme.BOLD_FONT_15PT);
		return timerInfo;
	}
//	시간이 보여질 타이머라벨을 만들어준다
	private FlatLabel createTimerLabel() {
		FlatLabel timerLabel = new FlatLabel();
		timerLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		timerLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		timerLabel.setBackground(Theme.LIGHT_BLUE_COLOR);
		timerLabel.setFont(Theme.BOLD_FONT_30PT);
		return timerLabel;
	}
	
//	흰색의 타이머인지 아닌지를 반환한다.
	boolean getIsWhite() {
		return isWhite;
	}
//	타이머의 스위치를 작동시킨다.
	void timerOperateSwitch() {
		timer.timerOperateSwitch();
	}
//	시간을 타이머라벨에 설정해준다
	void setTimeOnTimerLabel(String time) {
		((FlatLabel)getComponent(1)).setText(time);
	}
}
