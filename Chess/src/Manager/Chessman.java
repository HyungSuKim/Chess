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
			System.out.println("ŷ�Դϴ�" + super.toString());
			break;
		case QUEEN:
			System.out.println("���Դϴ�" + super.toString());
			break;
		case BISHOP:
			System.out.println("����Դϴ�" + super.toString());
			break;
		case KNIGHT:
			System.out.println("����Ʈ�Դϴ�" + super.toString());
			break;
		case ROOK:
			System.out.println("���Դϴ�" + super.toString());
			break;
		case PAWN:
			System.out.println("���Դϴ�" + super.toString());
			break;
		}
	}

	// @Override
	// boolean isValidate(int x, int y) {
	// return super.isValidate(x, y) &&
	// }
}
