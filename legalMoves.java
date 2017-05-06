/**
 * Name: Constantinos Kavadias (LoginID: ckavadias 664790)
 * Name: Ricky Tanudjaja (LoginID: rtanudjaja 773597)
 */

package rtanudjaja.slider;

import java.util.ArrayList;
import java.util.Scanner;

public class legalMoves {
	
	public static void main(String[] args) {
		
		/* Counter for number of legal moves */
		int numLegalHMoves = 0, numLegalVMoves = 0;
		ArrayList<vPiece> vertical = new ArrayList<vPiece>();
		ArrayList<hPiece> horizontal = new ArrayList<hPiece>();
		int x,y;
		
		@SuppressWarnings("resource")
		Scanner boardScan = new Scanner(System.in);
		
		/* Get the size of the board and create the board */
		Board gameBoard = new Board(boardScan.nextInt());
		
		/* Run a for loop to enter the data of characters into the board */
		for(y = gameBoard.getN() - 1;y >= 0; y--) {
			for(x = 0; x < gameBoard.getN(); x++) {
				char readChar = boardScan.next().charAt(0);
				gameBoard.enter(readChar, y, x);
				
				//store pieces in easy access ArrayList
				if(gameBoard.getChar(x, y) == 'V'){
					vertical.add(new vPiece(x, y));
				}
				else if(gameBoard.getChar(x, y) == 'H'){
					horizontal.add(new hPiece(x, y));
				}
			}
		}
		
		/* Run a for loop to count the number of legal moves for each piece */
		for(vPiece thePiece : vertical) {
			numLegalVMoves += checkMoves(thePiece, gameBoard);
		}
		for(hPiece thePiece : horizontal) {
			numLegalHMoves += checkMoves(thePiece, gameBoard);
		}
		
		System.out.println(numLegalHMoves);
		System.out.println(numLegalVMoves);
	}
	
	// This function helps to count the number of legal moves for each piece
	public static int checkMoves(piece thePiece, Board gameBoard) {
		int counter = 0;
		if(thePiece.move(1, 0, gameBoard)) {
			counter++;
		}
		
		if(thePiece.move(0, 1, gameBoard)) {
			counter++;
		}
		
		if(thePiece.move(-1, 0, gameBoard)) {
			counter++;
		}
		
		if(thePiece.move(0, -1, gameBoard)) {
			counter++;
		}
		
		return counter;
	}

}
