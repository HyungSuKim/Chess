package com.bfpaul.renewal.chess.timer;

import java.time.LocalTime;

// TimerView에서 시간속성을 가지고 Timer로써의 역할을 하며 TimerView의 timerLabel에 시간을 보여주기위해 작성하였다.
class Timer extends Thread {
	// 초기값을 30분으로 설정하였다.
	private LocalTime remainTime = LocalTime.of(0, 30, 0);
	// TimerView의 가져와 timerLabel과 isWhite속성의 데이터를 받아오고 넘겨주기 위함.
	private TimerView timerView;
	// Timer가 작동하고있는지 아닌지를 나타낸다
	private boolean isTimerOperating;

	Timer(TimerView timerView) {
		this.timerView = timerView;
		
		if (timerView.getIsWhite()) {
			isTimerOperating = true;
		}
		
		start();
	}

	// 남은시간을 반환한다.
	private String getRemainTime() {
		if (remainTime.getSecond() < 10) {
			return remainTime.getMinute() + " : 0" + remainTime.getSecond();
		} else {
			return remainTime.getMinute() + " : " + remainTime.getSecond();
		}
	}

	// 남은시간을 타이머 뷰의 타이머라벨에 셋팅해준다.
	void setOnTimerView() {
		timerView.setTimeOnTimerLabel(getRemainTime());
	}

	// 남은시간에서 1초씩 흐르게해준다.
	private void minusSecond() {
		remainTime = remainTime.minusSeconds(1);
	}

	// GameTimerView에서 버튼이벤트 발생시 타이머의 작동을 스위치해서 하나의 타이머를 멈추고 다른 하나를 동작시켜준다.
	void timerOperateSwitch() {
		isTimerOperating = !isTimerOperating;
		interrupt();
	}
	
	// 타이머의 작동
	@Override
	public void run() {
		synchronized (this) {
			
			checkTimerIsOperating();

			while (true) {
				timerOperation();
			}
			
		}
	}
	
	// 타이머가 작동했을때 현재 남은 시간에서 1초씩 흐르게해주고 이를 타이머뷰에 적용시킨다.
	private void timerOperation() {
		try {
			Thread.sleep(1000);
			minusSecond();
			setOnTimerView();
		} catch (InterruptedException e) {
			
			checkTimerIsOperating();
			
		}
	}
	// 타이머가 작동중인지 확인하고 작동중이 아니라면 일시정지 상태로 바꾼다.	
	private void checkTimerIsOperating() {
		if(!isTimerOperating) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
}
