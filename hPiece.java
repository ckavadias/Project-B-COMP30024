package projectA;

public class hPiece extends piece {

	public hPiece(int horizontalLocation, int verticalLocation) {
		super(horizontalLocation, verticalLocation);
	}
	
	@Override
	public boolean move(int horizontal, int vertical, Board board) {
		char cell;
		
		// can't move to the left only check remaining three directions
		if (horizontal + this.getxLoc() < 0){
			return false;
		}
		
		//need to have provision for leaving the board
		if(horizontal + this.getxLoc() == board.getN() && vertical + this.getyLoc() < board.getN() ){
			return true;
		}
		
		//check that specified cell is empty
		cell = board.getGameboard()[this.getxLoc()][this.getyLoc()];
		
		if (cell == '+'){
			return true;
		}
		
		else{
			return false;
		}
		
	}

}