package projectA;

import java.util.Scanner;

public class legalMoves {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// array of objects of Horizontal & Vertical
		// vertical[]
		// horizontal[]
		// int numLegalHMoves
		// int numLegalVMoves
		
		// scanner + record piece location
		Scanner boardScan = new Scanner(System.in);
		// assume the input is correct
		Board gameBoard = new Board(boardScan.nextInt());
		//ignore newline
		boardScan.next();
		
		int i,j;
		for(i = gameBoard.getN() - 1;i >= 0; i--) {
			for(j = 0; j <= gameBoard.getN(); j++) {
				gameBoard.enter(boardScan.next().charAt(0), i, j);
			}
			//ignore newline
			boardScan.next();
		}
		
		// run a while/for loop to enter the data into the board
		// identify pieces
		// and record location at the same time
		
		// run the counter for number of legalMoves for each pieces based on recorded location
		// (we already know whether it is a hPiece or vPiece)
		// if move(1,0) run counter
		// e.g. move(1,0,board) either returns true if possible else false
		
		// return the value as the specifications said
	}

}
