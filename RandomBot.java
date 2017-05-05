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
	private ArrayList<int[]> vertical = new ArrayList<int[]>();
	private ArrayList<int[]> horizontal = new ArrayList<int[]>();
	
	public void init(int dimension, String board, char player) {
		this.player = player;
		System.out.println("player: " + this.player);
		
		if(player == Global.H_CELL) {
			this.opponent = Global.V_CELL;
		} else {
			this.opponent = Global.H_CELL;
		}
		
		System.out.println("opponent: " + this.opponent);
		
		this.gameBoard = new Board(dimension);
		System.out.println("dimension: " + dimension);
		
		this.boardScan = new Scanner(board);
		int x,y;
		for(y = gameBoard.getN() - 1;y >= 0; y--) {
			for(x = 0; x < gameBoard.getN(); x++) {
				char readChar = boardScan.next().charAt(0);
				System.out.printf("%c ",readChar);
				gameBoard.enter(readChar, y, x);
				
				//store pieces in easy access ArrayList
				int[] location = new int[2];
				location[0] = x;
				location[1] = y;
				
				if(gameBoard.getChar(x, y) == 'V'){
					vertical.add(location);
				}
				else if(gameBoard.getChar(x, y) == 'H'){
					horizontal.add(location);
				}
			}
			System.out.println();
		}
	}
	
	public void update(Move move) {
		try {
			if (move == null) {
				return;
			} else {
				char thePiece = this.gameBoard.getChar(move.i, move.j);
				
				/* switch case for direction the piece is moving 
				 * set the new position to the opponent's piece */
				switch(move.d) {
					case UP:
						if(thePiece == 'V' && move.j == this.gameBoard.getN() - 1) {
							break;
						}
						this.gameBoard.setChar(move.i, move.j + 1, thePiece);
						break;
					case DOWN:
						this.gameBoard.setChar(move.i, move.j - 1, thePiece);
						break;
					case LEFT:
						this.gameBoard.setChar(move.i - 1, move.j, thePiece);
						break;
					case RIGHT:
						if(thePiece == 'H' && move.i == this.gameBoard.getN() - 1) {
							break;
						}
						this.gameBoard.setChar(move.i + 1, move.j, thePiece);
						break;
				}
				
				/* set the old position to blank */
				this.gameBoard.setChar(move.i, move.j, Global.BLANK);
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			/********************************************FOR TESTING PURPOSES********************************************/
			this.gameBoard.print();
		}
	}
	
	public Move move() {
		/* make it into passive by return null */
		// return null;
		
		Move.Direction piece_direction = null;
		int[] piece_location = null;
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
						if(piece_location[0] == 0) {
							continue;
						} else if(this.gameBoard.getChar(piece_location[0] - 1, piece_location[1]) != Global.BLANK) {
							continue;
						}
					} else if(piece_direction.equals(Move.Direction.RIGHT)) {
						if(piece_location[0] == this.gameBoard.getN()-1) {
							continue;
						} else if(this.gameBoard.getChar(piece_location[0] + 1, piece_location[1]) != Global.BLANK) {
							continue;
						}
					} else if(piece_direction.equals(Move.Direction.UP)) {
						if (piece_location[1] == this.gameBoard.getN()-1) {
						} else if(this.gameBoard.getChar(piece_location[0], piece_location[1] + 1) != Global.BLANK) {
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
						if(piece_location[1] == 0) {
							continue;
						} else if(this.gameBoard.getChar(piece_location[0], piece_location[1] - 1) != Global.BLANK) {
							continue;
						}
					} else if(piece_direction.equals(Move.Direction.UP)) {
						if(piece_location[1] == this.gameBoard.getN()-1) {
							continue;
						} else if(this.gameBoard.getChar(piece_location[0], piece_location[1] + 1) != Global.BLANK) {
							continue;
						}
					} else if(piece_direction.equals(Move.Direction.RIGHT)) {
						if (piece_location[0] == this.gameBoard.getN()-1) {
						} else if(this.gameBoard.getChar(piece_location[0] + 1, piece_location[1]) != Global.BLANK) {
							continue;
						}
					}
				}
				illegalMove = false;
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
		}
		
		Move selected_move = new Move(piece_location[0],piece_location[1], piece_direction);
		
		/********************************************FOR TESTING PURPOSES********************************************/
		System.out.println(selected_move.toString());
		
		/* self update the state of the game board */
		System.out.println("self-update");
		this.update(selected_move);
		
		return selected_move;
	}
	
}
