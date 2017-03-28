package projectA;

public class vPiece extends piece {
	
	public vPiece(int horizontalLocation, int verticalLocation) {
		super(horizontalLocation, verticalLocation);
	}
	
	@Override
	public boolean move(int horizontal, int vertical, Board board) {
		char cell;
		
		// can't move down only check remaining three directions
		if (vertical < 0 || horizontal + this.getxLoc() == board.getN()){
			return false;
		}
		
		//need to have provision for leaving the board
		if(vertical + this.getyLoc() == board.getN() && horizontal + this.getxLoc() < board.getN() ){
			return true;
		}
		
		//check that specified cell is empty
		cell = board.getChar(getxLoc() + horizontal, getyLoc() + vertical);
		
		if (cell == '+'){
			return true;
		}
		
		else{
			return false;
		}
	}
}
