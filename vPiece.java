/**
 * Name: Constantinos Kavadias (LoginID: ckavadias 664790)
 * Name: Ricky Tanudjaja (LoginID: rtanudjaja 773597)
 */

package CKRTsliderbot;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

public class vPiece extends piece {
	
	public vPiece(int horizontalLocation, int verticalLocation) {
		super(horizontalLocation, verticalLocation);
	}
	
	@Override
	public boolean move(Move movement, Board board) {
		char cell ;
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
		// can't move down and go outside the board in horizontal direction
		if (vertical < 0 || horizontal + this.getxLoc() == board.getN()){
			return false;
		}
		
		//need to have provision for leaving the board
		if(vertical + this.getyLoc() == board.getN() && horizontal + this.getxLoc() < board.getN() ){
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
	
	public boolean isH(){
		return false;
	}
}
