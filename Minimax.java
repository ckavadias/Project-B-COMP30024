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
private static int heuristicH(Board board, hPiece thisPiece){
	int x = thisPiece.getxLoc(), y = thisPiece.getyLoc(), result = 0, i;
	
	result += board.getN() - x;
		//iterate horizontally and record blocks
		for( i = x + 1; i < board.getN(); i++){
			if(board.getChar(i, y) != Global.BLANK){
				result++;
			}
		}
	
	return result;
}

private static int heuristicV(Board board, vPiece thisPiece){
	int x = thisPiece.getxLoc(), y = thisPiece.getyLoc(), result = 0, j;
	
	result += board.getN() - y;
	
	//iterate vertically and record blocks
			for( j = y + 1; j < board.getN(); j++){
				if(board.getChar(x, j) != Global.BLANK){
					result++;
				}
			}
			
	return result;
}

//choose piece to minimax and return chosen move
public static Move choose_move(Board board, ArrayList<hPiece> hPieces, ArrayList<vPiece> vPieces, char player){
	int minimum = board.getN()*board.getN(), current, ply = 1;
	
	//iterate through myPieces find lowest heuristic and record as thePiece
	
	if(player == Global.H_CELL){
		//perform minimax on thePiece returning move 
		return findH_min(board,hPieces,vPieces,ply,minimum);
	}
	
	else{
		//perform minimax on thePiece returning move 
		return findV_min(board,hPieces, vPieces,ply,minimum);
	}
	
}

//find minimum of maximums, returns a tuple of the minimum and the move that gives it
private static Move findH_min(Board board, ArrayList<hPiece> hPieces, ArrayList<vPiece> vPieces, 
		int ply,int maximum){
	
	int current, minimum = 0, i = 0;
	char player = Global.H_CELL;
	Move chosen = null, move = null;
	Move[] possibles = new Move[3];
	
	
	//create array of possible moves to iterate through
	
	if (ply == maxPly){
		
		//return heuristics of next moves don't proceed to further depth
		//for possible moves, if legal, update board, determine heuristic, undo update	
		for(hPiece thisPiece : hPieces){
			i = 0;
			for(Move.Direction d : Move.Direction.values()){
				if(d != Move.Direction.LEFT){
					possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(),d);
					i++;
				}
			}
			
			for(Move thisMove : possibles){
				
			if(thisPiece.move(thisMove, board)){
				//update board
				SliderBot1.change_place(thisMove, board, thisPiece);
				
				//heuristic
				current = heuristicH(board, thisPiece);
				
				//Pruning condition
				if(current < maximum){
					return null;
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
	}
	
	else{
		//proceed to next depth
		current = 0;
		for(hPiece thisPiece : hPieces){
			i=0;
			for(Move.Direction d : Move.Direction.values()){
				if(d != Move.Direction.LEFT){
					possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(),d);
					i++;
				}
			}
			
			for(Move thisMove : possibles){
				
			if(thisPiece.move(thisMove, board)){
				//update board
				SliderBot1.change_place(thisMove, board, thisPiece);
				//find minimum of the enemy heuristics given this move
				current = find_max(hPieces, vPieces, ply + 1, board, current, player);
				
				//Pruning condition
				if(current > maximum){
					return null;
				}
				
				if(current > minimum){
					minimum = current;
					chosen = thisMove;
				}
				//reverse update
				move = find_opposite(thisMove);
				SliderBot1.change_place(move, board, thisPiece);
				
			}
			}
		}
	}
	
	return chosen;
}

private static Move findV_min(Board board, ArrayList<hPiece> hPieces, ArrayList<vPiece> vPieces, 
		int ply,int maximum){
	
	int current, minimum = 0, i = 0;
	char player = Global.V_CELL;
	Move chosen = null, move = null;
	Move[] possibles = new Move[3];
	
	
	//create array of possible moves to iterate through
	
	if (ply == maxPly){
	
		//return heuristics of next moves don't proceed to further depth
		//for possible moves, if legal, update board, determine heuristic, undo update
		
		for(vPiece thisPiece : vPieces){
			 i = 0;
			for(Move.Direction d : Move.Direction.values()){
					if(d != Move.Direction.DOWN){
						possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(),d);
						i++;
					}
				}
			for(Move thisMove : possibles){
				
			if(thisPiece.move(thisMove, board)){
				//update board
				SliderBot1.change_place(thisMove, board, thisPiece);
				
				//heuristic
				current = heuristicV(board, thisPiece);
				
				//Pruning condition
				if(current < maximum){
					return null;
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
	}
	
	else{
		
		//proceed to next depth
		current = 0;
		for(vPiece thisPiece : vPieces){
			i = 0;
			for(Move.Direction d : Move.Direction.values()){
				if(d != Move.Direction.DOWN){
					possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(),d);
					i++;
				}
			}
			for(Move thisMove : possibles){
				
			if(thisPiece.move(thisMove, board)){
			
				//update board
				SliderBot1.change_place(thisMove, board, thisPiece);
				//find minimum of the enemy heuristics given this move
				current = find_max(hPieces, vPieces, ply + 1, board, current, player);

				//Pruning condition
				if(current > maximum){
				
					return null;
				}
				
				if(current > minimum){
		
					minimum = current;
					chosen = thisMove;
				}
				//reverse update
				move = find_opposite(thisMove);
				SliderBot1.change_place(move, board, thisPiece);
				
			}
			
		}
		}
	}
	
	return chosen;
}

//find maximum of minimums, return maximum
private static int find_max(ArrayList<hPiece> hPieces, ArrayList<vPiece> vPieces, int ply, Board board, 
		int minimum, char player){
	int current = 0, maximum = board.getN()*board.getN();
	
	if(ply == maxPly){
		
		if(player == Global.H_CELL){
			//return heuristics only
			for(vPiece thisPiece : vPieces){
			
				current = heuristicV(board, thisPiece);
				if(current < minimum){
					//pruning condition
					return minimum;
				}
				
				else if (current < maximum){
					maximum = current;
				}
			}
		}
		
		else{
			//return heuristics only
			for(hPiece thisPiece : hPieces){
			
				current = heuristicH(board, thisPiece);
				
				if(current < minimum){
					//pruning condition
					return minimum;
				}
				
				else if (current < maximum){
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
			return new Move(theMove.i, theMove.j + 1, Move.Direction.DOWN);
		case DOWN:
			return new Move(theMove.i, theMove.j - 1,Move.Direction.UP);
		case LEFT:
			return new Move(theMove.i - 1, theMove.j, Move.Direction.RIGHT);
		case RIGHT:
			return new Move(theMove.i + 1, theMove.j ,Move.Direction.LEFT);
	}
	
	return theMove;
}
}


