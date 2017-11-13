package com.bfpaul.chess.timer;

import java.time.LocalTime;

class Timer extends Thread {
	private LocalTime time = LocalTime.of(0, 30, 0);

	private TimerPanel timerPanel;

	boolean timerOperate;

	Timer(TimerPanel timerPanel) {
		this.timerPanel = timerPanel;
		setTimerOnTimerPanel();

		if (timerPanel.getIsWhite()) {
			timerOperateSwitch();
		}

	}

	private String getTime() {
		if (time.getSecond() < 10) {
			return time.getMinute() + " : 0" + time.getSecond();
		} else {
			return time.getMinute() + " : " + time.getSecond();
		}
	}

	private void setTimerOnTimerPanel() {
		timerPanel.getTimerLabel().setText(getTime());
	}

	private void minusSecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		time = time.minusSeconds(1);
	}

	void timerOperateSwitch() {
		timerOperate = !timerOperate;

		if (timerOperate) {
			this.start();
		} else {
			this.interrupt();
		}

	}

	void timerOperation() {
		while (timerOperate) {
			minusSecond();
			setTimerOnTimerPanel();
		}
	}

	@Override
	public void run() {
		
		timerOperation();
		
	}
}
