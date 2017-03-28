package projectA;

import java.util.Scanner;

public class legalMoves {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// array of objects of Horizontal & Vertical
		// vertical[]
		// horizontal[]
		
		// important things to return
		int numLegalHMoves = 0, numLegalVMoves = 0;
		
		// scanner + record piece location
		@SuppressWarnings("resource")
		Scanner boardScan = new Scanner(System.in);
		// assume the input is correct
		Board gameBoard = new Board(boardScan.nextInt());
		
		// run a for loop to enter the data into the board
		int x,y, vIndex = 0, hIndex = 0;
				
		//Maximum number of pieces per player is n, the dimension of the board
		vPiece vertical[] = new vPiece[gameBoard.getN()];
		hPiece horizontal[] = new hPiece[gameBoard.getN()];
		
		for(y = gameBoard.getN() - 1;y >= 0; y--) {
			for(x = 0; x < gameBoard.getN(); x++) {
				char readChar = boardScan.next().charAt(0);
				gameBoard.enter(readChar, y, x);
				
				//store pieces in easy access array
				if(gameBoard.getChar(x, y) == 'V'){
					vertical[vIndex] = new vPiece(x, y);
					vIndex++;
				}
				else if(gameBoard.getChar(x, y) == 'H'){
					horizontal[hIndex] = new hPiece(x, y);
					hIndex++;
				}
			}
		}
		
		// run a while/for loop to enter the data into the board
		// identify pieces
		// and record location at the same time
		
		//SIMPLE IMPLEMENTATION
		for(y = gameBoard.getN() - 1;y >= 0; y--) {
			for(x = 0; x < gameBoard.getN(); x++) {
				if(gameBoard.getChar(x, y) == 'H') {
					hPiece horizontalPieces = new hPiece(x, y);
					numLegalHMoves += checkMoves(horizontalPieces, gameBoard);
				}
				
				if(gameBoard.getChar(x, y) == 'V') {
					vPiece verticalPieces = new vPiece(x, y);
					numLegalVMoves += checkMoves(verticalPieces, gameBoard);
				}
			}
		}
		
		// return the value as the specifications said
		System.out.println(numLegalHMoves);
		System.out.println(numLegalVMoves);
		
		
		
		// run the counter for number of legalMoves for each pieces based on recorded location
		// (we already know whether it is a hPiece or vPiece)
		// if move(1,0) run counter
		// e.g. move(1,0,board) either returns true if possible else false
		
	}
	
	// THIS FUNCTION IS REQUIRED FOR SIMPLE IMPLEMENTATION BUT I WOULD LIKE TO CHANGE THIS LATER
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
