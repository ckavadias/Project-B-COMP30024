package CKRTsliderbot;

import java.util.Scanner;
import java.util.ArrayList;

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
	private static ArrayList<vPiece> vertical = new ArrayList<vPiece>();
	private static ArrayList<hPiece> horizontal = new ArrayList<hPiece>();
	
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
				
				if(gameBoard.getChar(x, y) == 'V'&& vertical.size() < dimension-1){
					vertical.add(new vPiece(x, y));
				}
				else if(gameBoard.getChar(x, y) == 'H' && horizontal.size() < dimension-1){
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
					if(thePiece == Global.V_CELL && move.j == this.gameBoard.getN() - 1) {
						vertical.remove(find_vPiece(vertical, move));
						break;
					}
					this.gameBoard.enter( thePiece, move.j + 1,move.i);
					break;
				case DOWN:
					this.gameBoard.enter(thePiece, move.j - 1, move.i);
					break;
				case LEFT:
					this.gameBoard.enter(thePiece,move.j, move.i - 1);
					break;
				case RIGHT:
					if(thePiece == Global.H_CELL && move.i == this.gameBoard.getN() - 1) {
						horizontal.remove(find_hPiece(horizontal, move));
						break;
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
		Move chosen;
		boolean remove;
		//implement minimax
		//remove pieces from list if off board
		chosen = Minimax.choose_move(gameBoard, horizontal, vertical, player);
		
		if(chosen == null){
			return chosen;
		}
		
		if(player == Global.H_CELL){
			hPiece thePiece1 = find_hPiece(horizontal, chosen);
			remove = change_place(chosen, gameBoard, thePiece1);
			if(remove){
				horizontal.remove(thePiece1);
			}
		}
		
		else{
			vPiece thePiece = find_vPiece(vertical, chosen); 
			remove = change_place(chosen, gameBoard, thePiece);
			if(remove){
				vertical.remove(thePiece);
			}
		}
		
		
		return chosen;
		/*the 'update' should update the state of the board (can be used for any Move) */
		//Move selected_move = null;
		//this.update(selected_move);
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
	
	public static boolean change_place(Move move, Board board, piece thePiece){
		char player = Global.V_CELL;
		boolean remove = false;
		
		if(thePiece.isH()){
			player = Global.H_CELL;
		}
		
		//as this will also be used to reverse updates this condition is necessary to avoid
		//indedation errors
		if(move.j < board.getN() && move.i < board.getN()){
			board.enter(Global.BLANK, move.j, move.i);
		}
		
		switch(move.d){
			case UP:
				thePiece.setyLoc(move.j + 1);
				if(player == Global.V_CELL && move.j == board.getN() - 1){
					remove = true;
					break;
				}
				board.enter(player,move.j + 1, move.i);
				break;
			case DOWN:
				thePiece.setyLoc(move.j -1);
				board.enter(player,move.j - 1, move.i);
				break;
			case LEFT:
				thePiece.setxLoc(move.i - 1);
				board.enter(player ,move.j, move.i - 1);
				break;
			case RIGHT:
				thePiece.setxLoc(move.i + 1);
				if(player == Global.H_CELL && move.i == board.getN() - 1){
					remove = true;
					break;
				}
				board.enter(player,move.j, move.i + 1);
				break;
		}
		
		return remove;
	}
	
	public static void printMove(Move move){
		System.out.printf("x positon: %d,y position: %d, direction: %s\n" , move.i, move.j, move.d);
	}
}
