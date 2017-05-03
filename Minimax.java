/**
 This class contains methods to implement minimax algorithm for a slider game
 initially for 2ply 
 */
package CKRTsliderbot;

//library downloaded from www.javatuples.org
import org.javatuples.*;

public final class Minimax {

}

private int maxPly = 2;
private Pair<int, int>[] possibleMoves = {new Pair<int, int>(0,1), new Pair<int, int>(1,0), 
		new Pair<int,int>(-1,0), new Pair<int, int>(0,-1)};


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
		
		//return heuristics of next moves don't proceed to further depth
		//for possible moves, if legal, update board, determine heuristic, undo update
		for(Pair<int,int> thisMove : moves){
			if(thisPiece.move(thisMove.getValue0(), thisMove.getValue1(), board){
				//update board
				//heuristic
				//reverse update
			}
			
		}
	}
	
	else{
		//proceed to next depth
	}
}

