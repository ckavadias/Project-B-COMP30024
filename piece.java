/**
 * Name: Constantinos Kavadias (LoginID: ckavadias 664790)
 * Name: Ricky Tanudjaja (LoginID: rtanudjaja 773597)
 */

package rtanudjaja.slider;

public abstract class piece {
	
	private int xLoc;
	private int yLoc;
	
	/* Constructor */
	public piece(int horizontalLocation, int verticalLocation) {
		this.xLoc = horizontalLocation;
		this.yLoc = verticalLocation;
	}
	
	public abstract boolean move(Move movement, Board board);

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
	
	public abstract boolean isH();

}
