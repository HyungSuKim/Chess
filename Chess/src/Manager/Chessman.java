package Manager;

public class Chessman extends Coordinate {
	String type;
	boolean isWhite;
	
	public Chessman(int x, int y, String type, boolean isWhite, Game game) {
		super(x, y);
		this.type = type;
		this.isWhite = isWhite;
		setOnGame(game);
	}
	
	void setOnGame(Game game) {
		if(isWhite) {
			game.addChessman(isWhite, this);
		} else {
			game.addChessman(isWhite, this);
		}
	}
	
	String getType() {
		return type;
	}
	
	public void vaildateMove() {
		switch(type) {
		case "King" : System.out.println("ŷ�Դϴ�"+ super.toString());
			break;
		case "Queen" : System.out.println("���Դϴ�"+ super.toString());
			break;
		case "Bishop" : System.out.println("����Դϴ�"+ super.toString());
			break;
		case "Knight" : System.out.println("����Ʈ�Դϴ�"+ super.toString());
			break;
		case "Rook" : System.out.println("���Դϴ�"+ super.toString());
		break;
		case "Pawn" : System.out.println("���Դϴ�"+ super.toString());
			break;
		}
	}
	
//	@Override
//	boolean isValidate(int x, int y) {
//		return super.isValidate(x, y) && 
//	}
}

