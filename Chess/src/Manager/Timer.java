package Manager;

import java.time.LocalTime;

public class Timer {
//	LocalTime time = LocalTime.now();
	LocalTime timer;
//	boolean timerRemain;
	Timer() {
		timer = LocalTime.of(00, 30, 00);
//		timerRemain = true;
		
		System.out.println(timer.getMinute()+":"+timer.getSecond());
	}
	
	public void timeGoes() {
		timer = timer.minusSeconds(1);
	}
	
	public String toString() {
		return timer.getMinute() + ":" + timer.getSecond();
	}
}
