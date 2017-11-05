package Manager;

public class Player {
	private boolean isWhite;
	private boolean phase;
	private boolean result;
	
	private Timer timer;
	
	public Player(boolean isWhite) {
		this.isWhite = isWhite;
		
		if(isWhite) {
			phase = true;
		}
		
		timer = new Timer();
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	void switchPhase() {
		phase = !phase;
	}
	
	public boolean getPhase() {
		return phase;
	}
}
