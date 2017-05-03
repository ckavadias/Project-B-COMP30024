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


//return heuristic value for thisPiece, heuristic is absolute distance from goal plus number of
//blocks on the path
private int heuristic(Board board, piece thisPiece){
	int x = thisPiece.getxLoc, y = thisPiece.getyLoc, result = 0, i, j;
	
	if(thisPiece.isH()){
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
	
	//iterate through myPieces find lowest heuristic and record as thePiece
	for( piece thisPiece : myPieces ){
		current = heuristic(board, thisPiece);
		if(current < minimum){
			minimum = current;
			thePiece = thisPiece
		}
	}
	
	//perform minimax on thePiece returning move 
	return find_min(board, enemies, myPieces, ply, <Move.Direction.UP,0>, thePiece).getValue1;
}

//find minimum of maximums, returns a tuple of the minimum and the move that gives it
private Pair<Move, int> find_min(Board board, ArrayList enemies, ArrayList myPieces, 
		int ply,Pair<Move, int> maximum, piece thePiece){
	
	Pair<Move, int> returnValue;
	int current, minimum = board.getN()*board.getN();
	Move chosen = new Move(0,0, Move.Direction.UP);
	Move move = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(), Move.Direction.UP);
	
	if (ply == maxPly){
		
		//return heuristics of next moves don't proceed to further depth
		//for possible moves, if legal, update board, determine heuristic, undo update
		
		for(Move.Direction d : Move.Direction.values()){
			
			if(thisPiece.move(move, board){
				//update board
				SliderBot1.change_place(move, board, thePiece);
				
				//heuristic
				current = heuristic(board, thePiece);
				
				//Pruning condition
				if(current < maximum.getValue1()){
					return maximum;
				}
				if(current < minimum){
					minimum = current;
					chosen.i = move.i;
					chosen.j = move.j;
					chosen.d = move.d;
				}
				//reverse update
				move.d = find_opposite(move.d);
				SliderBot1.change_place(move, board, thePiece)
				
			}
			
		}
	}
	
	else{
		//proceed to next depth
		current = board.getN()*board.getN;
		for(Move.Direction d : Move.Direction.values()){
			
			if(thisPiece.move(move, board){
				//update board
				SliderBot1.change_place(move, board, thePiece);
				//find minimum of the enemy heuristics given this move
				current = find_max(myPieces, enemies, ply + 1, board, current);
				
				//Pruning condition
				if(current < maximum.getValue1()){
					return maximum;
				}
				
				if(current < minimum){
					minimum = current;
					chosen.i = move.i;
					chosen.j = move.j;
					chosen.d = move.d;
				}
				//reverse update
				move.d = find_opposite(move.d);
				SliderBot1.change_place(move, board, thePiece)
				
			}
			
		}
	}
	
	returnValue = returnValue.setAt0(chosen);
	returnValue = returnValue.setAt1(minimum);
	return returnValue;
}

//find maximum of minimums, return maximum
private int find_max(ArrayList enemies, ArrayList myPieces, int ply, Board board, int minimum){
	int current = 0, maximum = 0;
	
	if(ply == maxPly){
		//return heuristics only
		for(piece thisPiece : myPieces){
			
			current = heuristic(board, thisPiece);
			if(current > minimum){
				//pruning condition
				return minimum;
			}
			
			else if (current > maximum){
				maximum = current;
			}
		}
	}
	
	else{
		//go deeper
		for(piece thisPiece : myPieces){
			current = find_min(Board, myPieces, enemies, ply + 1,
					<Move.Direction.UP, current> , thisPiece).getValue1();
			
			//Pruning condition
			if(current > minimum.getValue1()){
				return minimum;
			}
			
			else if(current > maximum){
				maximum = current;
			}
		}
	}
	
	return maximum;
}
//find out reverse of direction d
private Move.Direction find_opposite(Move.Direction d){
	
	switch (d):
		case Move.Direction.UP:
			return Move.Direction.DOWN;
		case Move.Direction.DOWN:
			return Move.Direction.UP;
		case Move.Direction.LEFT:
			return Move.Direction.RIGHT;
		case Move.Direction.Right:
			return Move.Direction.LEFT;
}



