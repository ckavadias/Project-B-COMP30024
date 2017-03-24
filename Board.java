package projectA;

public class Board {
	//All the pieces information also about the block and the free space in the form of 2d arrays
	
	private int n;
	private char[][] gameboard;
	
	public Board(int n) {
		//this is the game board constructor
		this.n = n;
		gameboard = new char[n][n];
	}
	
	//enter the data
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

}
