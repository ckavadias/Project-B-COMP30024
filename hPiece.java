package projectA;

public class hPiece extends piece {

	public hPiece(int horizontalLocation, int verticalLocation) {
		super(horizontalLocation, verticalLocation);
	}
	
	@Override
	public boolean move(int horizontal, int vertical, Board board) {
		char cell;
		
		// can't move to the left only check remaining three directions
		if (horizontal < 0 || vertical + this.getyLoc() == board.getN()){
			//System.out.println("A Error for " + this.getxLoc() + "," + this.getyLoc());
			return false;
		}
		
		//need to have provision for leaving the board
		if((horizontal + this.getxLoc() == board.getN()) && (vertical + this.getyLoc() < board.getN())){
			//System.out.println("1 Counting for " + this.getxLoc() + "," + this.getyLoc());
			return true;
		}
		
		//check that specified cell is empty
		cell = board.getChar(getxLoc() + horizontal, getyLoc() + vertical);
		
		if (cell == '+'){
			//System.out.println("2 Counting for " + this.getxLoc() + "," + this.getyLoc());
			return true;
		}
		
		else{
			//System.out.println("B Error for " + this.getxLoc() + "," + this.getyLoc());
			return false;
		}
	}

}