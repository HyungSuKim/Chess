package Manager;

import java.util.ArrayList;

public class Game {
	private ArrayList<Chessman> whiteChessman;
	private ArrayList<Chessman> blackChessman;
	
	private Player whitePlayer;
	private Player blackPlayer;
	
	public Game() {
		whiteChessman = new ArrayList<Chessman>();
		blackChessman = new ArrayList<Chessman>();
		
		whitePlayer = new Player(true);
		blackPlayer = new Player(false);
	}
	
	public void addChessman(boolean isWhite, Chessman chessman) {
		if(isWhite) {
			whiteChessman.add(chessman);
		} else {
			blackChessman.add(chessman);			
		}
	}
	
	public int getBlackChessmanCount(String type) {
		int count = 0;
		int blackChessmanSize = blackChessman.size();
		
		for(int index = 0; index < blackChessmanSize; index++) {
			if(blackChessman.get(index).getType().equals(type)) {
				count++;
			}
		}
		
		return count;
	}
	
	public int getWhiteChessmanCount(String type) {
		int count = 0;
		int whiteChessmanSize = whiteChessman.size();
		
		for(int index = 0; index < whiteChessmanSize; index++) {
			if(whiteChessman.get(index).getType().equals(type)) {
				count++;
			}
		}
		return count;
	}
	
	public Player getWhitePlayer() {
		return whitePlayer;
	}
	
	public Player getBlackPlayer() {
		return blackPlayer;
	}
	
	public void phaseEnd() {
		whitePlayer.switchPhase();
		blackPlayer.switchPhase();
	}
}
