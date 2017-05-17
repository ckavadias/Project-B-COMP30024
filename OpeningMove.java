package CKRTsliderbot;

import java.util.ArrayList;

import aiproj.slider.Move;

public class OpeningMove {
	
	//in the intial stage of the game, try to move as close as possible to goal
	public static Move chooseOpeningMove(Board board, ArrayList<hPiece> hPieces, 
			ArrayList<vPiece> vPieces,char player) {
		
		Move chosen = null;
		if( player == Global.H_CELL){
			
			//iterate through all pieces
			for(hPiece thisPiece: hPieces) {
				Move thisMove = new Move(thisPiece.getxLoc(),thisPiece.getyLoc(),Move.Direction.RIGHT);
				if(thisPiece.move(thisMove, board)) {
					chosen = thisMove;
				}
			}
			
		} else {
			
			//iterate through all pieces
			for(vPiece thisPiece: vPieces) {
				Move thisMove = new Move(thisPiece.getxLoc(),thisPiece.getyLoc(),Move.Direction.UP);
				if(thisPiece.move(thisMove, board)) {
					chosen = thisMove;
				}
			}
		}
		
		return chosen;
	}
	
	//if all pieces are blocked from moving to goal, start implementing Minimax
	public static boolean checkOpeningMove(Board board, ArrayList<hPiece> hPieces, 
			ArrayList<vPiece> vPieces,char player) {
		
		boolean stillCanMoveToGoal = false;
		
		if( player == Global.H_CELL){
			
			//iterate through all pieces
			for(hPiece thisPiece: hPieces) {
				Move thisMove = new Move(thisPiece.getxLoc(),thisPiece.getyLoc(),Move.Direction.RIGHT);
				if(thisPiece.move(thisMove, board)) {
					stillCanMoveToGoal = true;
					break;
				}
			}
			
		} else {
			
			//iterate through all pieces
			for(vPiece thisPiece: vPieces) {
				Move thisMove = new Move(thisPiece.getxLoc(),thisPiece.getyLoc(),Move.Direction.UP);
				if(thisPiece.move(thisMove, board)) {
					stillCanMoveToGoal = true;
					break;
				}
			}
		}
		
		return stillCanMoveToGoal;
	}
}
