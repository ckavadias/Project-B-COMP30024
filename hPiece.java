/**
 * Name: Constantinos Kavadias (LoginID: ckavadias 664790)
 * Name: Ricky Tanudjaja (LoginID: rtanudjaja 773597)
 */

package rtanudjaja.slider;

public class hPiece extends piece {

	public hPiece(int horizontalLocation, int verticalLocation) {
		super(horizontalLocation, verticalLocation);
	}
	
	@Override
	public boolean move(int horizontal, int vertical, Board board) {
		char cell;
		
		// can't move to the left and go outside the board in vertical direction
		if (horizontal < 0 || vertical + this.getyLoc() == board.getN()){
			return false;
		}
		
		//need to have provision for leaving the board
		if((horizontal + this.getxLoc() == board.getN()) && (vertical + this.getyLoc() < board.getN())){
			return true;
		}
		
		//check that specified cell is empty
		try {
			cell = board.getChar(getxLoc() + horizontal, getyLoc() + vertical);
			if (cell == '+'){
				return true;
			}
			
			else{
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}