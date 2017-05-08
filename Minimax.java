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


//choose piece to minimax and return chosen move
public static Move choose_move(Board board, ArrayList<hPiece> hPieces, 
		ArrayList<vPiece> vPieces,char player){
	int ply = 1, i;
	double current, maximum = -100.00;
	Move chosen = null, move = null;
	Move[] possibles = new Move[3];
	
	//iterate through all moves player can make
	//call find max on all of them returning a double
	
	if( player == Global.H_CELL){
		//iterate through all pieces
		for(hPiece thisPiece: hPieces){
			i = 0;
			//create array of all possible moves for piece
			for(Move.Direction d : Move.Direction.values()){
				if(d != Move.Direction.LEFT){
					possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(), d);
					i++;
				}
			}
			
			for(Move thisMove : possibles){
				//check if move is legal and proceed to find maximum
				if(thisPiece.move(thisMove, board)){
					//change board to find new utility
					SliderBot1.change_place(thisMove, board, thisPiece);
					current = find_max(vPieces, hPieces, board, player, ply + 1);
				
					//check if this is the highest found
					if(Double.compare(current, maximum) > 0){
						maximum = current;
						chosen = thisMove;
					}
					//change board back
					move = find_opposite(thisMove);
					SliderBot1.change_place(move, board, thisPiece);
				}
			}
		}
		
	}
	else{
		//iterate through all pieces
		for(vPiece thisPiece: vPieces){
			i = 0;
			//create array of all possible moves for piece
			for(Move.Direction d : Move.Direction.values()){
				if(d != Move.Direction.DOWN){
					possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(), d);
					i++;
				}
			}
			
			for(Move thisMove : possibles){
				//check if move is legal and proceed to find maximum
				if(thisPiece.move(thisMove, board)){
					//change board to find new utility
					SliderBot1.change_place(thisMove, board, thisPiece);
					current = find_max(vPieces, hPieces, board, player, ply + 1);
				
					//check if this is the highest found
					if(Double.compare(current, maximum) > 0){
						maximum = current;
						chosen = thisMove;
					}
					//change board back
					move = find_opposite(thisMove);
					SliderBot1.change_place(move, board, thisPiece);
				}
			}
		
		}
	}
	
	return chosen;
}

private static double find_max(ArrayList<vPiece> vPieces, ArrayList<hPiece> hPieces, 
		Board board, char player, int ply){
	
	int i;
	double current, minimum = 100.00;
	Move chosen = null, move = null;
	Move[] possibles = new Move[3];
	
	//if ply reached return utility function(player perspective)
	if(ply == maxPly){
		//utility for current state
		return utility(vPieces, hPieces, board, player);
	}
	else{
		//otherwise call find min on all opponent moves
		if( player == Global.V_CELL){
			//iterate through all pieces
			for(hPiece thisPiece: hPieces){
				i = 0;
				//create array of all possible moves for piece
				for(Move.Direction d : Move.Direction.values()){
					if(d != Move.Direction.LEFT){
						possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(), d);
					}
				}
				
				for(Move thisMove : possibles){
					//check if move is legal and proceed to find minimum
					if(thisPiece.onBoard(board) && thisPiece.move(thisMove, board)){
						//change board to find new utility
						SliderBot1.change_place(thisMove, board, thisPiece);
						current = find_min(vPieces, hPieces, board, player, ply + 1);
					
						//check if this is the lowest found
						if(Double.compare(current, minimum) < 0){
							minimum = current;
						}
						//change board back
						move = find_opposite(thisMove);
						SliderBot1.change_place(move, board, thisPiece);
					}
				}
			}
			
		}
		else{
			//iterate through all pieces
			for(vPiece thisPiece: vPieces){
				i = 0;
				//create array of all possible moves for piece
				for(Move.Direction d : Move.Direction.values()){
					if(d != Move.Direction.DOWN){
						possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(), d);
						i++;
					}
				}
				
				for(Move thisMove : possibles){
					//check if move is legal and proceed to find minimum
					if(thisPiece.onBoard(board) && thisPiece.move(thisMove, board)){
						//change board to find new utility
						SliderBot1.change_place(thisMove, board, thisPiece);
						current = find_min(vPieces, hPieces, board, player, ply + 1);
					
						//check if this is the lowest found
						if(Double.compare(current, minimum) < 0){
							minimum = current;
						}
						//change board back
						move = find_opposite(thisMove);
						SliderBot1.change_place(move, board, thisPiece);
					}
				}
			
			}
		}
		
		return minimum;
	}
	
}

private static double find_min(ArrayList<vPiece> vPieces, ArrayList<hPiece> hPieces, 
		Board board, char player, int ply){
	
	int i = 0;
	double current, maximum = 100.00;
	Move[] possibles = new Move[3];
	Move move = null;
	
	//if ply reached return utility function(player perspective)
	if(ply == maxPly){
		//utility for current state
		return utility(vPieces, hPieces, board, player);
	}
	else{
		//otherwise call find max on all player moves
		if( player == Global.H_CELL){
			//iterate through all pieces
			for(hPiece thisPiece: hPieces){
				i = 0;
				//create array of all possible moves for piece
				for(Move.Direction d : Move.Direction.values()){
					if(d != Move.Direction.LEFT){
						possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(), d);
						i++;
					}
				}
				
				for(Move thisMove : possibles){
					//check if move is legal and proceed to find maximum
					if(thisPiece.onBoard(board) && thisPiece.move(thisMove, board)){
						//change board to find new utility
						SliderBot1.change_place(thisMove, board, thisPiece);
						current = find_max(vPieces, hPieces, board, player, ply);
					
						//check if this is the lowest found
						if(Double.compare(current, maximum) > 0){
							maximum = current;
						}
						//change board back
						move = find_opposite(thisMove);
						SliderBot1.change_place(move, board, thisPiece);
					}
				}
			}
			
		}
		else{
			//iterate through all pieces
			for(vPiece thisPiece: vPieces){
				i = 0;
				//create array of all possible moves for piece
				for(Move.Direction d : Move.Direction.values()){
					if(d != Move.Direction.DOWN){
						possibles[i] = new Move(thisPiece.getxLoc(), thisPiece.getyLoc(), d);
						i++;
					}
				}
				
				for(Move thisMove : possibles){
					//check if move is legal and proceed to find maximum
					if(thisPiece.onBoard(board) && thisPiece.move(thisMove, board)){
						//change board to find new utility
						SliderBot1.change_place(thisMove, board, thisPiece);
						current = find_max(vPieces, hPieces, board, player, ply);
					
						//check if this is the lowest found
						if(Double.compare(current, maximum) > 0){
							maximum = current;
						}
						//change board back
						move = find_opposite(thisMove);
						SliderBot1.change_place(move, board, thisPiece);
					}
				}
			
			}
		}
		
		return maximum;
	}
	
	
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


//utility function for minimax, features include number of blocked pieces, pieces in win postion
//and pieces remaining
private static double utility(ArrayList<vPiece> vPieces, ArrayList<hPiece> hPieces,
		Board board, char player){
	double blockedOpps =0.0, piecesWinning =0.0, blockedPieces = 0.0, oppLessPieces = 0.0;
	int i;
	
	if (player == Global.H_CELL){
		//determine number of blocked opponents
		for(vPiece thisPiece : vPieces){
			for(i = thisPiece.getyLoc(); i < board.getN();i++ ){
				if(board.getChar(thisPiece.getxLoc(), i) != Global.BLANK){
					blockedOpps++;
					break;
				}
			}
		}
		blockedOpps*=0.3;
		
		//determine no. pieces in final column
		for(hPiece thisPiece : hPieces){
			if(thisPiece.getyLoc() == board.getN() - 1){
				piecesWinning++;
			}
		}
		piecesWinning*=0.5;
		
		//determine no.pieces blocked
		for(hPiece thisPiece : hPieces){
			for( i = thisPiece.getxLoc(); i < board.getN(); i++){
				if(board.getChar(i, thisPiece.getyLoc()) != Global.BLANK){
					blockedPieces++;
					break;
				}
			}
		}
		blockedPieces*=0.4;
		
		//determine number of pieces remaining - number oppenent remaing
		for(hPiece thisPiece : hPieces){
			if(thisPiece.getxLoc() < board.getN() && thisPiece.getyLoc() < board.getN()){
				oppLessPieces++;
			}
		}
		for(vPiece thisPiece : vPieces){
			if(thisPiece.getxLoc() < board.getN() && thisPiece.getyLoc() < board.getN()){
				oppLessPieces--;
			}
		}
		oppLessPieces*=0.5;
	}
	else{
		//determine number of blocked Pieces
		for(vPiece thisPiece : vPieces){
			for(i = thisPiece.getyLoc(); i < board.getN();i++ ){
				if(board.getChar(thisPiece.getxLoc(), i) != Global.BLANK){
					blockedPieces++;
				}
			}
		}
		blockedPieces*=0.4;
		
		//determine no. pieces in final row
		for(vPiece thisPiece : vPieces){
			if(thisPiece.getxLoc() == board.getN() - 1){
				piecesWinning++;
			}
		}
		piecesWinning*=0.5;
		
		//determine no.pieces opponents
		for(hPiece thisPiece : hPieces){
			for( i = thisPiece.getxLoc(); i < board.getN(); i++){
				if(board.getChar(i, thisPiece.getyLoc()) != Global.BLANK){
					blockedOpps++;
					break;
				}
			}
		}
		blockedOpps*=0.3;
		
		//determine number of pieces remaining - number oppenent remaing
		for(vPiece thisPiece : vPieces){
			if(thisPiece.getxLoc() < board.getN() && thisPiece.getyLoc() < board.getN()){
				oppLessPieces--;
			}
		}
		for(vPiece thisPiece : vPieces){
			if(thisPiece.getxLoc() < board.getN() && thisPiece.getyLoc() < board.getN()){
				oppLessPieces++;
			}
		}
		oppLessPieces*=0.5;
		
	}
	return (blockedOpps+piecesWinning-blockedPieces+oppLessPieces);
}
}


