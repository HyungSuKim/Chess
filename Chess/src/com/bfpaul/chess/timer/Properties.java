package com.bfpaul.chess.timer;

import java.time.LocalTime;

class Properties {
	LocalTime timer;
//	boolean timerRemain;
	Properties() {
		timer = LocalTime.of(00, 30, 00);
		
	}
	
	public void timeGoes() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer = timer.minusSeconds(1);
	}
	
	public String toString() {
		return timer.getMinute() + ":" + timer.getSecond();
	}
}
