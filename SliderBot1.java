package CKRTsliderbot;

import java.util.Scanner;

public class SliderBot1 implements SliderPlayer {
	
	/* attributes */
	private Scanner boardScan = null;
	private Board gameBoard = null;
	private char player;

	public SliderBot1() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(int dimension, String board, char player) {
		this.player = player;
		System.out.println("player: " + this.player);
		this.gameBoard = new Board(dimension);
		System.out.println("dimension: " + dimension);
		
		this.boardScan = new Scanner(board);
		int x,y;
		for(y = gameBoard.getN() - 1;y >= 0; y--) {
			for(x = 0; x < gameBoard.getN(); x++) {
				char readChar = boardScan.next().charAt(0);
				System.out.printf("%c ",readChar);
				gameBoard.enter(readChar, y, x);
			}
			System.out.println();
		}
	}
	
	public void update(Move move) {
		
	}
	
	public Move move() {
		return null;
	}

}
