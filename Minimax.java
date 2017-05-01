/**
 This class contains methods to implement minimax algorithm for a slider game
 initially for 2ply 
 */
package CKRTsliderbot;

public final class Minimax {

}

private int maxPly = 2;

//return heuristic value for thisPiece, heuristic is absolute distance from goal plus number of
//blocks on the path
private int heuristic(Board board, piece thisPiece){
	int x = thisPiece.getxLoc, y = thisPiece.getyLoc, result = 0, i, j;
	
	if(thisPiece.is_h){
		result += board.getN - x;
		
		//iterate horizontally and record blocks
		for( i = x + 1; i < board.getN){
			if(board.getChar(i, y) != '+'){
				result++;
			}
		}
		
	}
	else{
		result += board.getN - y;
		
		//iterate vertically and record blocks
				for( j = y + 1; j < board.getN){
					if(board.getChar(x, j) != '+'){
						result++;
					}
				}
	}
	
	return result;
}

//choose piece to minimax and return chosen move
public static Move choose_move(Board board, ArrayList enemies, ArrayList myPieces){
	int minimum = board.getN*board.getN, current, ply = 1;
	piece thePiece;
	
	//iterate through myPieces find lowest heuristic adn record as thePiece
	for( piece thisPiece : myPieces ){
		current = heuristic(board, thisPiece);
		if(current < minimum){
			minimum = current;
			thePiece = thisPiece
		}
	}
	
	//perform minimax on thePiece returning move 
	return find_max(board, enemies, myPieces, ply, thePiece);
}

//find maximum of minimums
private Move find_max(Board board, ArrayList enemies, ArrayList myPieces, int ply, piece thePiece){
	
	if (ply == maxPly){
		//return heuristics don't proceed to further depth
	}
	
	else{
		//proceed to next depth
	}
}

