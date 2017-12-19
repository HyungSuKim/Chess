package com.bfpaul.renewal.chess.timer;

import java.time.LocalTime;

// TimerView���� �ð��Ӽ��� ������ Timer�ν��� ������ �ϸ� TimerView�� timerLabel�� �ð��� �����ֱ����� �ۼ��Ͽ���.
class Timer extends Thread {
	// �ʱⰪ�� 30������ �����Ͽ���.
	private LocalTime remainTime = LocalTime.of(0, 30, 0);
	// TimerView�� ������ timerLabel�� isWhite�Ӽ��� �����͸� �޾ƿ��� �Ѱ��ֱ� ����.
	private TimerView timerView;
	// Timer�� �۵��ϰ��ִ��� �ƴ����� ��Ÿ����
	private boolean isTimerOperating;

	Timer(TimerView timerView) {
		this.timerView = timerView;
		
		if (timerView.getIsWhite()) {
			isTimerOperating = true;
		}
		
		start();
	}

	// �����ð��� ��ȯ�Ѵ�.
	private String getRemainTime() {
		if (remainTime.getSecond() < 10) {
			return remainTime.getMinute() + " : 0" + remainTime.getSecond();
		} else {
			return remainTime.getMinute() + " : " + remainTime.getSecond();
		}
	}

	// �����ð��� Ÿ�̸� ���� Ÿ�̸Ӷ󺧿� �������ش�.
	void setOnTimerView() {
		timerView.setTimeOnTimerLabel(getRemainTime());
	}

	// �����ð����� 1�ʾ� �帣�����ش�.
	private void minusSecond() {
		remainTime = remainTime.minusSeconds(1);
	}

	// GameTimerView���� ��ư�̺�Ʈ �߻��� Ÿ�̸��� �۵��� ����ġ�ؼ� �ϳ��� Ÿ�̸Ӹ� ���߰� �ٸ� �ϳ��� ���۽����ش�.
	void timerOperateSwitch() {
		isTimerOperating = !isTimerOperating;
		interrupt();
	}
	
	// Ÿ�̸��� �۵�
	@Override
	public void run() {
		synchronized (this) {
			
			checkTimerIsOperating();

			while (true) {
				timerOperation();
			}
			
		}
	}
	
	// Ÿ�̸Ӱ� �۵������� ���� ���� �ð����� 1�ʾ� �帣�����ְ� �̸� Ÿ�̸Ӻ信 �����Ų��.
	private void timerOperation() {
		try {
			Thread.sleep(1000);
			minusSecond();
			setOnTimerView();
		} catch (InterruptedException e) {
			
			checkTimerIsOperating();
			
		}
	}
	// Ÿ�̸Ӱ� �۵������� Ȯ���ϰ� �۵����� �ƴ϶�� �Ͻ����� ���·� �ٲ۴�.	
	private void checkTimerIsOperating() {
		if(!isTimerOperating) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
}
