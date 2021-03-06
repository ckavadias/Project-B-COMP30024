/**
 * Name: Constantinos Kavadias (LoginID: ckavadias 664790)
 * Name: Ricky Tanudjaja (LoginID: rtanudjaja 773597)
 */

package CKRTsliderbot;

import aiproj.slider.Move;

public class hPiece extends piece {

	public hPiece(int horizontalLocation, int verticalLocation) {
		super(horizontalLocation, verticalLocation);
	}
	
	@Override
	public boolean move(Move movement, Board board) {
		char cell;
		int horizontal = 0, vertical = 0;
		
		switch (movement.d){
			case UP:
				vertical = 1;
				break;
			case DOWN:
				vertical = -1;
				break;
			case LEFT:
				horizontal = -1;
				break;
			case RIGHT:
				horizontal = 1;
				break;
		}
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
			cell = board.getChar(this.getxLoc() + horizontal, this.getyLoc() + vertical);
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
	
	public boolean isH(){
		return true;
	}

}