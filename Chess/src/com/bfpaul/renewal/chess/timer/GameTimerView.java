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
// 게임 시작시 플레이어(흰색과 검정색)의 타이머를 보여주고 버튼을 이용해서 턴을 넘겨주고자 구현하였다.
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
	
//	정해진 무게만큼의 영역을 차지하고 부모의 영역에 영역을 맞춰주는 제약조건을 반환한다. 
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	턴종료 버튼의 패널을 만들어서 버튼에 대한 설명과 버튼을 하나의 패널로 묶어주려고 구현하였다.
	private FlatPanel createPhaseEndButtonPanel() {
		FlatPanel phaseEndButtonPanel = new FlatPanel(new LinearLayout(Orientation.VERTICAL, 0));
		phaseEndButtonPanel.add(createPhaseEndButtonInfo(), createMatchParentConstraints(8));
		phaseEndButtonPanel.add(createPhaseEndButton(), createMatchParentConstraints(15));
		return phaseEndButtonPanel;
	}
	
//	턴종료 버튼에 대한 설명을 라벨로써 만들어준다
	private FlatLabel createPhaseEndButtonInfo() {
		FlatLabel phaseEndButtonInfo = new FlatLabel("차례");
		phaseEndButtonInfo.setOpaque(false);
		phaseEndButtonInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		phaseEndButtonInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		phaseEndButtonInfo.setFont(Theme.BOLD_FONT_15PT);
		return phaseEndButtonInfo;
	}
	
//	턴종료 버튼을 만들어준다
	private FlatButton createPhaseEndButton() {
		FlatButton phaseEndButton = new FlatButton("종료");
		phaseEndButton.setBackground(Theme.YELLOW_COLOR);
		phaseEndButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		phaseEndButton.setOnClickListener(new OnClickListener() {
//	턴종료 버튼을 클릭하면 타이머의 작동 스위치의 상태를 바꿔주어서 시간이 흐르고 안흐르게 하려고 했다.
			@Override
			public void onClick(Component component) {
				((TimerView)getComponent(0)).timerOperateSwitch();
				((TimerView)getComponent(1)).timerOperateSwitch();
			}
			
		});
		return phaseEndButton;
	}
}
