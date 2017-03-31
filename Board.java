/**
 * Name: Constantinos Kavadias (LoginID: ckavadias 664790)
 * Name: Ricky Tanudjaja (LoginID: rtanudjaja 773597)
 */

package rtanudjaja.slider;

public class Board {
	
	/* Keep the information about the characters in a 2d array */
	private int n;
	private char[][] gameboard;
	
	public Board(int n) {
		//this is the game board constructor
		this.n = n;
		gameboard = new char[n][n];
	}
	
	/* Enter the char character data into the board array */
	public void enter(char character, int y, int x) {
		gameboard[y][x] = character;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public char[][] getGameboard() {
		return gameboard;
	}

	public void setGameboard(char[][] gameboard) {
		this.gameboard = gameboard;
	}
	
	/* Get the char of the board in x,y position */
	public char getChar(int x, int y) {
		return gameboard[y][x];
	}

}
