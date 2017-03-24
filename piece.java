package projectA;

public abstract class piece {
	
	private int xLoc;
	private int yLoc;
	
	public piece(int horizontalLocation, int verticalLocation) {
		this.xLoc = horizontalLocation;
		this.yLoc = verticalLocation;
	}
	
	public abstract boolean move(int horizontal, int vertical, Board board);

	public int getxLoc() {
		return xLoc;
	}

	public void setxLoc(int xLoc) {
		this.xLoc = xLoc;
	}

	public int getyLoc() {
		return yLoc;
	}

	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}

}
