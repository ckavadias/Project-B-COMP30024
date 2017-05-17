/**
 * Name: Constantinos Kavadias (LoginID: ckavadias 664790)
 * Name: Ricky Tanudjaja (LoginID: rtanudjaja 773597)
 */

package CKRTsliderbot;

import java.util.Scanner;
import java.util.ArrayList;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

/** 
 * SliderBot2 class: This bot plays the game of Slider
 * with the A* algorithm to determine the best path
 * and use random pieces chosen to choose the piece to move
 */
public class SliderBot2 implements SliderPlayer {
	
	/* attributes */
	private Scanner boardScan = null;
	private Board gameBoard = null;
	private char player;
	private boolean openingMove = true;
	private static ArrayList<vPiece> vertical = new ArrayList<vPiece>();
	private static ArrayList<hPiece> horizontal = new ArrayList<hPiece>();
	
	public void init(int dimension, String board, char player) {
		
		vertical.clear();
		horizontal.clear();
		
		this.player = player;
		
		this.gameBoard = new Board(dimension);
		
		this.boardScan = new Scanner(board);
		int x,y;
		for(y = gameBoard.getN() - 1;y >= 0; y--) {
			for(x = 0; x < gameBoard.getN(); x++) {
				char readChar = boardScan.next().charAt(0);
				gameBoard.enter(readChar, y, x);
				
				if(gameBoard.getChar(x, y) == 'V'&& vertical.size() < dimension-1){
					vertical.add(new vPiece(x, y));
				}
				else if(gameBoard.getChar(x, y) == 'H' && horizontal.size() < dimension-1){
					horizontal.add(new hPiece(x, y));
				}
			}
		}
	}
	
	public void update(Move move) {
		boolean remove = false;
		
		try {
			if (move == null) {
				return;
			} else {
				char player = this.gameBoard.getChar(move.i, move.j);
				if(player == Global.H_CELL){
					hPiece thePiece1 = find_hPiece(horizontal, move);
					remove = change_place(move, gameBoard, thePiece1);
					if(remove){
						horizontal.remove(thePiece1);
					}
				}
				
				else{
					vPiece thePiece = find_vPiece(vertical, move); 
					remove = change_place(move, gameBoard, thePiece);
					if(remove){
						vertical.remove(thePiece);
					}
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public Move move() {
		Move chosen = null;
		boolean remove;
		
		if(openingMove) {
			/* in the initial stage of the game, try to move as right/up as possible 
			 * starting from the pieces at the bottom */
			chosen = OpeningMove.chooseOpeningMove(gameBoard, horizontal, vertical, player);
			openingMove = OpeningMove.checkOpeningMove(gameBoard, horizontal, vertical, player);
		}
		
		if(!openingMove) {
			//implement minimax
			//remove pieces from list if off board
			chosen = Minimax.choose_move(gameBoard, horizontal, vertical, player);
		}
		
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
