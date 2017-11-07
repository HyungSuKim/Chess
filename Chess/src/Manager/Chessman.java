package Manager;

public class Chessman extends Coordinate {
	Type type;
	boolean isWhite;

	public enum Type {
		BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK
	};

	public Chessman(int x, int y, Type type, boolean isWhite, Game game) {
		super(x, y);
		this.type = type;
		this.isWhite = isWhite;
		setOnGame(game);
	}

	void setOnGame(Game game) {
		if (isWhite) {
			game.addChessman(isWhite, this);
		} else {
			game.addChessman(isWhite, this);
		}
	}

	Type getType() {
		return type;
	}

	public void vaildateMove() {
		switch (type) {
		case KING:
			System.out.println("킹입니다" + super.toString());
			break;
		case QUEEN:
			System.out.println("퀸입니다" + super.toString());
			break;
		case BISHOP:
			System.out.println("비숍입니다" + super.toString());
			break;
		case KNIGHT:
			System.out.println("나이트입니다" + super.toString());
			break;
		case ROOK:
			System.out.println("룩입니다" + super.toString());
			break;
		case PAWN:
			System.out.println("폰입니다" + super.toString());
			break;
		}
	}

	// @Override
	// boolean isValidate(int x, int y) {
	// return super.isValidate(x, y) &&
	// }
}
