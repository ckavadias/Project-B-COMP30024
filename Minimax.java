/**
 This class contains methods to implement minimax algorithm for a slider game
 initially for 2ply 
 */
package CKRTsliderbot;

import java.util.ArrayList;
import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

public final class Minimax {

private static int maxPly = 2;


//return heuristic value for thisPiece, heuristic is absolute distance from goal plus number of
//blocks on the path
private static int heuristic(Board board, piece thisPiece){
	int x = thisPiece.getxLoc(), y = thisPiece.getyLoc(), result = 0, i, j;
	
	if(thisPiece.isH()){
		result += board.getN() - x;
		
		//iterate horizontally and record blocks
		for( i = x + 1; i < board.getN(); i++){
			if(board.getChar(i, y) != Global.BLANK){
				result++;
			}
		}
		
	}
	else{
		result += board.getN() - y;
		
		//iterate vertically and record blocks
				for( j = y + 1; j < board.getN(); j++){
					if(board.getChar(x, j) != Global.BLANK){
						result++;
					}
				}
	}
	
	return result;
}

//choose piece to minimax and return chosen move
public static Move choose_move(Board board, ArrayList<hPiece> hPieces, ArrayList<vPiece> vPieces, char player){
	int minimum = board.getN()*board.getN(), current, ply = 1;
	
	//iterate through myPieces find lowest heuristic and record as thePiece
	
	if(player == Global.H_CELL){
		hPiece thePiece = new hPiece(0,0);
		
		for( hPiece thisPiece : hPieces ){
			current = heuristic(board, thisPiece);
			if(current < minimum){
				minimum = current;
				thePiece = thisPiece;
			}
		}
		//perform minimax on thePiece returning move 
		return findH_min(board,hPieces,vPieces,ply,0, thePiece);
	}
	
	else{
		vPiece thePiece = new vPiece(0,0);
		
		for( vPiece thisPiece : vPieces ){
			current = heuristic(board, thisPiece);
			if(current < minimum){
				minimum = current;
				thePiece = thisPiece;
			}
		}
		//perform minimax on thePiece returning move 
		return findV_min(board,hPieces, vPieces,ply,0, thePiece);
	}
	
}

//find minimum of maximums, returns a tuple of the minimum and the move that gives it
private static Move findH_min(Board board, ArrayList<hPiece> hPieces, ArrayList<vPiece> vPieces, 
		int ply,int maximum, hPiece thisPiece){
	
	int current, minimum = board.getN()*board.getN(), i = 0;
	char player = Global.H_CELL;
	Move chosen = new Move(0,0,Move.Direction.UP), move = new Move(0,0,Move.Direction.UP);
	Move[] possibles = new Move[4];
	
	
	//create array of possible moves to iterate through
	for(Move.Direction d : Move.Direction.values()){
		possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(),d);
		i++;
	}
	
	if (ply == maxPly){
		
		//return heuristics of next moves don't proceed to further depth
		//for possible moves, if legal, update board, determine heuristic, undo update
		
		for(Move thisMove : possibles){
			
			if(thisPiece.move(thisMove, board)){
				//update board
				SliderBot1.change_place(thisMove, board, thisPiece);
				
				//heuristic
				current = heuristic(board, thisPiece);
				
				//Pruning condition
				if(current < maximum){
					return new Move(0,0, Move.Direction.UP);
				}
				if(current < minimum){
					minimum = current;
					chosen = thisMove;
				}
				//reverse update
				move = find_opposite(thisMove);
				SliderBot1.change_place(move, board, thisPiece);
				
			}
			
		}
	}
	
	else{
		//proceed to next depth
		current = board.getN()*board.getN();
		for(Move thisMove : possibles){
			
			if(thisPiece.move(thisMove, board)){
				//update board
				SliderBot1.change_place(thisMove, board, thisPiece);
				//find minimum of the enemy heuristics given this move
				current = find_max(hPieces, vPieces, ply + 1, board, current, player);
				
				//Pruning condition
				if(current < maximum){
					return new Move(0,0, Move.Direction.UP);
				}
				
				if(current < minimum){
					minimum = current;
					chosen = thisMove;
				}
				//reverse update
				move = find_opposite(move);
				SliderBot1.change_place(move, board, thisPiece);
				
			}
			
		}
	}
	
	return chosen;
}

private static Move findV_min(Board board, ArrayList<hPiece> hPieces, ArrayList<vPiece> vPieces, 
		int ply,int maximum, vPiece thisPiece){
	
	int current, minimum = board.getN()*board.getN(), i = 0;
	char player = Global.V_CELL;
	Move chosen = new Move(0,0,Move.Direction.UP), move = new Move(0,0,Move.Direction.UP);
	Move[] possibles = new Move[4];
	
	
	//create array of possible moves to iterate through
	for(Move.Direction d : Move.Direction.values()){
		possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(),d);
		i++;
	}
	
	if (ply == maxPly){
		
		//return heuristics of next moves don't proceed to further depth
		//for possible moves, if legal, update board, determine heuristic, undo update
		
		for(Move thisMove : possibles){
			
			if(thisPiece.move(thisMove, board)){
				//update board
				SliderBot1.change_place(thisMove, board, thisPiece);
				
				//heuristic
				current = heuristic(board, thisPiece);
				
				//Pruning condition
				if(current < maximum){
					return new Move(0,0, Move.Direction.UP);
				}
				if(current < minimum){
					minimum = current;
					chosen = thisMove;
				}
				//reverse update
				move = find_opposite(thisMove);
				SliderBot1.change_place(move, board, thisPiece);
				
			}
			
		}
	}
	
	else{
		//proceed to next depth
		current = board.getN()*board.getN();
		for(Move thisMove : possibles){
			
			if(thisPiece.move(thisMove, board)){
				//update board
				SliderBot1.change_place(thisMove, board, thisPiece);
				//find minimum of the enemy heuristics given this move
				current = find_max(hPieces, vPieces, ply + 1, board, current, player);
				
				//Pruning condition
				if(current < maximum){
					return new Move(0,0, Move.Direction.UP);
				}
				
				if(current < minimum){
					minimum = current;
					chosen = thisMove;
				}
				//reverse update
				move = find_opposite(move);
				SliderBot1.change_place(move, board, thisPiece);
				
			}
			
		}
	}
	
	return chosen;
}

//find maximum of minimums, return maximum
private static int find_max(ArrayList<hPiece> hPieces, ArrayList<vPiece> vPieces, int ply, Board board, 
		int minimum, char player){
	int current = 0, maximum = 0;
	
	if(ply == maxPly){
		
		if(player == Global.H_CELL){
			//return heuristics only
			for(vPiece thisPiece : vPieces){
			
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
			//return heuristics only
			for(hPiece thisPiece : hPieces){
			
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
	}
	
	//for ply extension, which is unlikely, causing issues, not implementing
	/*else{
		//go deeper
		if(player == Global.H_CELL){
			for(vPiece thisPiece : vPieces){
				current = find_min(board, hPieces, vPieces, ply + 1, current, thisPiece);
			
				//Pruning condition
				if(current > minimum){
					return minimum;
				}
			
				else if(current > maximum){
					maximum = current;
				}
			}
		}
		
		else{
			for(hPiece thisPiece : hPieces){
				current = find_min(board, hPieces, vPieces ply + 1, current, thisPiece);
			
				//Pruning condition
				if(current > minimum){
					return minimum;
				}
			
				else if(current > maximum){
					maximum = current;
				}
			}
		}
	}*/
	
	return maximum;
}
//find out reverse of direction d
private static Move find_opposite(Move theMove){
	
	switch (theMove.d){
		case UP:
			return new Move(theMove.i - 1, theMove.j, Move.Direction.DOWN);
		case DOWN:
			return new Move(theMove.i + 1, theMove.j,Move.Direction.UP);
		case LEFT:
			return new Move(theMove.i, theMove.j + 1, Move.Direction.RIGHT);
		case RIGHT:
			return new Move(theMove.i, theMove.j - 1 ,Move.Direction.LEFT);
	}
	
	return theMove;
}
}


