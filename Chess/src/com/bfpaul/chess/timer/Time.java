package com.bfpaul.chess.timer;

import java.time.LocalTime;

// TimerPanel에서 Timer로써의 역할을 하며 TimerPanel의 timerLabel에 시간을 보여주기위해 작성하였다. 
class Time extends Thread {
	// 초기값을 30분으로 설정하였다.
	private LocalTime time = LocalTime.of(0, 30, 0);
	// TimerPanel을 가져와 timerLabel에 접근하기 위함.
	private TimerView timerView;
	// Timer가 작동하고있는지 아닌지를 나타낸다
	private boolean timerOperate;
	// Thread가 한번 실행되었는지 나타낸다
	boolean once;

	Time(TimerView timerPanel) {
		this.timerView = timerPanel;
		if (timerPanel.getIsWhite()) {
			once = true;
			timerOperate = true;
			start();	
		}
	}

	// 분과 초를 가져와서 String으로 반환하는데 초가 1자리 수일 경우 앞에 0을 붙여서 반환한다.
	private String getTime() {
		if (time.getSecond() < 10) {
			return time.getMinute() + " : 0" + time.getSecond();
		} else {
			return time.getMinute() + " : " + time.getSecond();
		}
	}

	// 시간을 가져와서 타이머 패널의 타이머라벨에 셋팅해준다.
	void setOnTimerLabel() {
		timerView.setTimeOnTimerLabel(getTime());
	}

	// 1초의 흐름을 구현하였다.
	private void minusSecond() {

		time = time.minusSeconds(1);
	}

	// GameTimerView에서 버튼이벤트 발생시 타이머의 작동을 스위치해서 하나의 타이머를 멈추고 다른 하나를 동작시켜준다.
	void timerOperateSwitch() {
		timerOperate = !timerOperate;

		if (timerOperate) {
			if (!once) {
				once = true;
				start();
				// System.out.println("블랙시작" + getState());
			} else {
				interrupt();
				// System.out.println(getState());
			}
		} else {
			// System.out.println("중지"+getState());
		}
	}

	// 타이머가 작동했을때 1초가 흐를때마다 타이머 패널의 라벨에 적용시켜준다.
	void timerOperation() {
		while (timerOperate) {
			System.out.println("쓰레드구동중");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			minusSecond();
			setOnTimerLabel();
		}
	}

	@Override
	public void run() {
		while (once) {
			timerOperation();
		}
	}
}
