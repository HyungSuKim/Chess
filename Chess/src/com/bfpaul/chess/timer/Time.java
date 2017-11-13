package com.bfpaul.chess.timer;

import java.time.LocalTime;

// TimerPanel���� Timer�ν��� ������ �ϸ� TimerPanel�� timerLabel�� �ð��� �����ֱ����� �ۼ��Ͽ���. 
class Time extends Thread {
	// �ʱⰪ�� 30������ �����Ͽ���.
	private LocalTime time = LocalTime.of(0, 30, 0);
	// TimerPanel�� ������ timerLabel�� �����ϱ� ����.
	private TimerView timerView;
	// Timer�� �۵��ϰ��ִ��� �ƴ����� ��Ÿ����
	private boolean timerOperate;
	// Thread�� �ѹ� ����Ǿ����� ��Ÿ����
	boolean once;

	Time(TimerView timerPanel) {
		this.timerView = timerPanel;
		if (timerPanel.getIsWhite()) {
			once = true;
			timerOperate = true;
			start();	
		}
	}

	// �а� �ʸ� �����ͼ� String���� ��ȯ�ϴµ� �ʰ� 1�ڸ� ���� ��� �տ� 0�� �ٿ��� ��ȯ�Ѵ�.
	private String getTime() {
		if (time.getSecond() < 10) {
			return time.getMinute() + " : 0" + time.getSecond();
		} else {
			return time.getMinute() + " : " + time.getSecond();
		}
	}

	// �ð��� �����ͼ� Ÿ�̸� �г��� Ÿ�̸Ӷ󺧿� �������ش�.
	void setOnTimerLabel() {
		timerView.setTimeOnTimerLabel(getTime());
	}

	// 1���� �帧�� �����Ͽ���.
	private void minusSecond() {

		time = time.minusSeconds(1);
	}

	// GameTimerView���� ��ư�̺�Ʈ �߻��� Ÿ�̸��� �۵��� ����ġ�ؼ� �ϳ��� Ÿ�̸Ӹ� ���߰� �ٸ� �ϳ��� ���۽����ش�.
	void timerOperateSwitch() {
		timerOperate = !timerOperate;

		if (timerOperate) {
			if (!once) {
				once = true;
				start();
				// System.out.println("������" + getState());
			} else {
				interrupt();
				// System.out.println(getState());
			}
		} else {
			// System.out.println("����"+getState());
		}
	}

	// Ÿ�̸Ӱ� �۵������� 1�ʰ� �带������ Ÿ�̸� �г��� �󺧿� ��������ش�.
	void timerOperation() {
		while (timerOperate) {
			System.out.println("�����屸����");
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
