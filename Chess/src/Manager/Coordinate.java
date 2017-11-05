package Manager;

class Coordinate {
	private static final int MINIMUM_X = 0;
	private static final int MAXIMUM_X = 63;
	private static final int MINIMUM_Y = 0;
	private static final int MAXIMUM_Y = 63;
	private int x, y;
	
	Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	boolean isValidate(int x, int y) {
		return (MINIMUM_X <= x && x <= MAXIMUM_X) && (MINIMUM_Y <= y && y <= MAXIMUM_Y);
	}
	
	void move(int x, int y) {
		if(isValidate(x, y)) {
			this.x = x;
			this.y = y;
		}
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
