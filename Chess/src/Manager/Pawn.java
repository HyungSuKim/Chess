package Manager;

public class Pawn extends Chessman {
	
	final int FIRST_MOVE_Y = 2;
	final int MOVE_XY = 1;
	final int CATCHING_MOVE = 1;
	
	boolean isMoved;
	boolean isCaught;

	public Pawn(int x, int y, String type, boolean isWhite, Game game) {
		super(x, y, type, isWhite, game);
		// TODO Auto-generated constructor stub
	}
	
	public void move() {
		if(!isMoved) {
			System.out.println(super.getX()+","+(super.getY()+FIRST_MOVE_Y));
			isMoved = true;
		} else {
			
		}
	}
}
