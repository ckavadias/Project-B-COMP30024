package CKRTsliderbot;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

public class RandomBot implements SliderPlayer{
	/* attributes */
	private Scanner boardScan = null;
	private Board gameBoard = null;
	private char player;
	private char opponent;
	private ArrayList<vPiece> vertical = new ArrayList<vPiece>();
	private ArrayList<hPiece> horizontal = new ArrayList<hPiece>();
	
	public void init(int dimension, String board, char player) {
		this.player = player;
		//System.out.println("player: " + this.player);
		
		if(player == Global.H_CELL) {
			this.opponent = Global.V_CELL;
		} else {
			this.opponent = Global.H_CELL;
		}
		
		//System.out.println("opponent: " + this.opponent);
		
		this.gameBoard = new Board(dimension);
		//System.out.println("dimension: " + dimension);
		
		this.boardScan = new Scanner(board);
		int x,y;
		for(y = gameBoard.getN() - 1;y >= 0; y--) {
			for(x = 0; x < gameBoard.getN(); x++) {
				char readChar = boardScan.next().charAt(0);
				//System.out.printf("%c ",readChar);
				gameBoard.enter(readChar, y, x);
				
				//store pieces in easy access ArrayList
				int[] location = new int[2];
				location[0] = x;
				location[1] = y;
				
				if(gameBoard.getChar(x, y) == 'V'&& vertical.size() < dimension-1){
					vertical.add(new vPiece(x, y));
				}
				else if(gameBoard.getChar(x, y) == 'H' && horizontal.size() < dimension-1){
					horizontal.add(new hPiece(x, y));
				}
			}
			//System.out.println();
		}
	}
	
	public void update(Move move) {
		try {
			if (move == null) {
				return;
			} else {
				char thePiece = this.gameBoard.getChar(move.i, move.j);
				
				if(thePiece == Global.V_CELL){
					vertical.remove(find_vPiece(vertical, move));
					//vertical.add(new vPiece(move.i, move.j));
				} else if(thePiece == Global.H_CELL){
					horizontal.remove(find_hPiece(horizontal, move));
					//horizontal.add(new hPiece(move.i, move.j));
				}

				/* switch case for direction the piece is moving 
				 * set the new position to the opponent's piece */
				switch(move.d) {
				case UP:
					if(thePiece == Global.V_CELL && move.j == this.gameBoard.getN() - 1) {
						break;
					}
					
					if(thePiece == Global.V_CELL){
						vertical.add(new vPiece( move.i, move.j + 1));
					} else if(thePiece == Global.H_CELL){
						horizontal.add(new hPiece(move.i, move.j + 1));
					}
					this.gameBoard.enter( thePiece, move.j + 1,move.i);
					break;
				case DOWN:
					
					if(thePiece == Global.V_CELL){
						vertical.add(new vPiece(move.i, move.j - 1));
					} else if(thePiece == Global.H_CELL){
						horizontal.add(new hPiece(move.i, move.j - 1));
					}
					
					this.gameBoard.enter(thePiece, move.j - 1, move.i);
					break;
				case LEFT:
					
					if(thePiece == Global.V_CELL){
						vertical.add(new vPiece(move.i - 1, move.j));
					} else if(thePiece == Global.H_CELL){
						horizontal.add(new hPiece(move.i - 1, move.j));
					}
					
					this.gameBoard.enter(thePiece,move.j, move.i - 1);
					break;
				case RIGHT:
					if(thePiece == Global.H_CELL && move.i == this.gameBoard.getN() - 1) {
						break;
					}
					
					if(thePiece == Global.V_CELL){
						vertical.add(new vPiece(move.i + 1, move.j));
					} else if(thePiece == Global.H_CELL){
						horizontal.add(new hPiece(move.i + 1, move.j));
					}
					
					this.gameBoard.enter(thePiece, move.j, move.i + 1);
					break;
				}
				
				/* set the old position to blank */
				this.gameBoard.enter( Global.BLANK, move.j, move.i);
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			/********************************************FOR TESTING PURPOSES********************************************/
			//this.gameBoard.print();
		}
	}
	
	public Move move() {
		/* make it into passive by return null */
		// return null;
		
		Move.Direction piece_direction = null;
		piece piece_location = null;
		boolean illegalMove = true;
		
		while(illegalMove) {
			try {
				/* select a random move */
				int pick = new Random().nextInt(Move.Direction.values().length);
				piece_direction = Move.Direction.values()[pick];
				
				/* select a random piece on the board and make sure the move is legal */
				if(this.player == 'V') {
					pick = new Random().nextInt(vertical.size());
					piece_location = vertical.get(pick);
					
					/* check for illegal move */
					if (piece_direction.equals(Move.Direction.DOWN)) {
						continue;
					} else if(piece_direction.equals(Move.Direction.LEFT)) {
						if(piece_location.getxLoc() == 0) {
							continue;
						} else if(this.gameBoard.getChar(piece_location.getxLoc() - 1, piece_location.getyLoc()) != Global.BLANK) {
							continue;
						}
					} else if(piece_direction.equals(Move.Direction.RIGHT)) {
						if(piece_location.getxLoc() == this.gameBoard.getN()-1) {
							continue;
						} else if(this.gameBoard.getChar(piece_location.getxLoc() + 1, piece_location.getyLoc()) != Global.BLANK) {
							continue;
						}
					} else if(piece_direction.equals(Move.Direction.UP)) {
						if (piece_location.getyLoc() == this.gameBoard.getN()-1) {
						} else if(this.gameBoard.getChar(piece_location.getxLoc(), piece_location.getyLoc() + 1) != Global.BLANK) {
							continue;
						}
					}
				} else if(this.player == 'H') {
					pick = new Random().nextInt(horizontal.size());
					piece_location = horizontal.get(pick);
					
					/* check for illegal move */
					if (piece_direction.equals(Move.Direction.LEFT)) {
						continue;
					} else if(piece_direction.equals(Move.Direction.DOWN)) {
						if(piece_location.getyLoc() == 0) {
							continue;
						} else if(this.gameBoard.getChar(piece_location.getxLoc(), piece_location.getyLoc() - 1) != Global.BLANK) {
							continue;
						}
					} else if(piece_direction.equals(Move.Direction.UP)) {
						if(piece_location.getyLoc() == this.gameBoard.getN()-1) {
							continue;
						} else if(this.gameBoard.getChar(piece_location.getxLoc(), piece_location.getyLoc() + 1) != Global.BLANK) {
							continue;
						}
					} else if(piece_direction.equals(Move.Direction.RIGHT)) {
						if (piece_location.getxLoc() == this.gameBoard.getN()-1) {
						} else if(this.gameBoard.getChar(piece_location.getxLoc() + 1, piece_location.getyLoc()) != Global.BLANK) {
							continue;
						}
					}
				}
				illegalMove = false;
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
		}
		
		Move selected_move = new Move(piece_location.getxLoc(),piece_location.getyLoc(), piece_direction);
		
		/********************************************FOR TESTING PURPOSES********************************************/
		//System.out.println(selected_move.toString());
		
		/* self update the state of the game board */
		this.update(selected_move);
		
		return selected_move;
	}
	
	//find the piece being moved in the ArrayList of pieces
		private vPiece find_vPiece(ArrayList<vPiece> pieces, Move theMove){
			for(vPiece thePiece : pieces){
				if(thePiece.getxLoc() == theMove.i){
					if(thePiece.getyLoc() == theMove.j){
						return thePiece;
					}
				}
			}
			
			return new vPiece(0,0);
		}
		
		private hPiece find_hPiece(ArrayList<hPiece> pieces, Move theMove){
			for(hPiece thePiece : pieces){
				if(thePiece.getxLoc() == theMove.i){
					if(thePiece.getyLoc() == theMove.j){
						return thePiece;
					}
				}
			}
			
			return new hPiece(0,0);
		}
}
