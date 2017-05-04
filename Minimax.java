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
	int current, minimum = board.getN()*board.getN(), i = 0;
	Move chosen, move;
	Move[] possibles = new Move[4];
	
	//create array of possible moves to iterate through
	for(Move.direction d : Move.Direction.values()){
		possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(),d);
		i++;
	}
	
	if (ply == maxPly){
		
		//return heuristics of next moves don't proceed to further depth
		//for possible moves, if legal, update board, determine heuristic, undo update
		
		for(Move thisMove : possibles){
			
			if(thisPiece.move(thisMove, board){
				//update board
				SliderBot1.change_place(thisMove, board, thePiece);
				
				//heuristic
				current = heuristic(board, thePiece);
				
				//Pruning condition
				if(current < maximum.getValue1()){
					return maximum;
				}
				if(current < minimum){
					minimum = current;
					chosen = thisMove;
				}
				//reverse update
				move = find_opposite(thisMove);
				SliderBot1.change_place(move, board, thePiece)
				
			}
			
		}
	}
	
	else{
		//proceed to next depth
		current = board.getN()*board.getN;
		for(Move thisMove : possibles){
			
			if(thisPiece.move(thisMove, board){
				//update board
				SliderBot1.change_place(thisMovemove, board, thePiece);
				//find minimum of the enemy heuristics given this move
				current = find_max(myPieces, enemies, ply + 1, board, current);
				
				//Pruning condition
				if(current < maximum.getValue1()){
					return maximum;
				}
				
				if(current < minimum){
					minimum = current;
					chosen = thisMove;
				}
				//reverse update
				move = find_opposite(move);
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
private Move find_opposite(Move theMove){
	
	switch (theMove.d):
		case Move.Direction.UP:
			return new Move(theMove.i - 1, theMove.j, Move.Direction.DOWN);
		case Move.Direction.DOWN:
			return new Move(theMove.i + 1, theMove.j,Move.Direction.UP);
		case Move.Direction.LEFT:
			return new Move(theMove.i, theMove.j + 1, Move.Direction.RIGHT);
		case Move.Direction.Right:
			return new Move(theMove.i, theMove.j - 1 ,Move.Direction.LEFT);
}



