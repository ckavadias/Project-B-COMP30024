package CKRTsliderbot;

import java.util.Scanner;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

/** 
 * SliderBot1 class: This bot plays the game of Slider
 * with the A* algorithm to determine the best path
 * and use random pieces chosen to choose the piece to move
 */
public class SliderBot1 implements SliderPlayer {
	
	/* attributes */
	private Scanner boardScan = null;
	private Board gameBoard = null;
	private char player;
	private char opponent;
	private ArrayList<vPiece> vertical = new ArrayList<vPiece>();
	private ArrayList<hPiece> horizontal = new ArrayList<hPiece>();
	
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
				
				if(gameBoard.getChar(x, y) == 'V'){
					vertical.add(new vPiece(x, y));
				}
				else if(gameBoard.getChar(x, y) == 'H'){
					horizontal.add(new hPiece(x, y));
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
		/*the 'update' should update the state of the board (can be used for any Move) */
		Move selected_move = null;
		this.update(selected_move);
		
		return null;
	}
	
	public void change_place(Move move, Board board, piece thePiece){
		char player = 'V';
		
		if(thePiece.isH()){
			player = 'H'
		}
		
		board[move.i, move.j] = '+';
		
		switch(move.d):
			case Move.direction.UP:
				thePiece.setyLoc(move.j + 1);
				board.enter[move.i][move.j + 1] = player;
				break;
			case Move.direction.DOWN:
				thePiece.setyLoc(move.j -1);
				board.enter[move.i][move.j - 1] = player;
				break;
			case Move.direction.LEFT:
				thePiece.setxLoc(move.i - 1);
				board.enter[move.i - 1][move.j] = player;
				break;
			case Move.direction.RIGHT:
				thePiece.setxLoc(move.i + 1);
				board.enter[move.i + 1][move.j] = player;
				break;
	}

}
