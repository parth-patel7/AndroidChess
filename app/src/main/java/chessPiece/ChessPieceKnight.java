package chessPiece;

import chess.*;

/**
 * @author parthpatel
 * @author joshherrera
 */
public class ChessPieceKnight extends ChessPiece{


	/**
	 * Constructor to initialize knight object (boolean value true if the color is black
	 * and false for white)
	 * @param color Color of the piece (true for black false for white)
	 */
	public ChessPieceKnight(boolean color) {
		this.isBlack = color;
	}


	/*
	 * Legal Moves (8)
	 * (i+2, j+1)
	 * (i+1, j+2)
	 * (i-1, j+2)
	 * (i-2, j+1)
	 * 
	 * (i-2, j-1)
	 * (i-1, j-2)
	 * (i+1, j-2)
	 * (i+2, j-1)
	 * 
	 */
	/**
	 * This method takes current and target location for the knight and returns true if the target 
	 * location is legal.
	 * @param i Current rank of the knight
	 * @param j Current file of the knight
	 * @param targetI Target rank for the knight
	 * @param targetJ Target file for the knight
	 * @return boolean value
	 */
	public static boolean knightLegalMove(int i, int j, int targetI, int targetJ) {

		boolean legalMove = false;


		if(!ChessHelper.isWithInBoard(targetI, targetJ)) {
			return false;
		}

		// All 8 possibilities
		if(i+2 == targetI && j+1 == targetJ) {
			legalMove = true;
		}
		if(i+1 == targetI && j+2 == targetJ) {
			legalMove = true;
		}
		if(i-1 == targetI && j+2 == targetJ) {
			legalMove = true;
		}
		if(i-2 == targetI && j+1 == targetJ) {
			legalMove = true;
		}
		if(i-2 == targetI && j-1 == targetJ) {
			legalMove = true;
		}
		if(i-1 == targetI && j-2 == targetJ) {
			legalMove = true;
		}
		if(i+1 == targetI && j-2 == targetJ) {
			legalMove = true;
		}
		if(i+2 == targetI && j-1 == targetJ) {
			legalMove = true;
		}

		// compare the current piece to the target piece and make sure they are not of same color
		if(sameColor(i,j,targetI,targetJ)){
			return false;
		}

		return legalMove;
	}

	
	/**
	 * This method takes current location of the knight and returns true if it can send a check to opponent's king.
	 * @param i Current rank of the knight
	 * @param j Current file of the knight
	 * @return boolean value
	 */
	public static boolean knightCanCheck(int i, int j) {
		boolean c = chessBoard.board[i][j].isBlack;
		
		if(i<=5 && j<=6) {
			if(chessBoard.board[i+2][j+1]!=null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i+2][j+1]) && chessBoard.board[i+2][j+1].isBlack != c) {
					return true;
				}
			}
		}
		if(i<=5 && j>=1) {
			if(chessBoard.board[i+2][j-1]!=null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i+2][j-1]) && chessBoard.board[i+2][j-1].isBlack != c) {
					return true;
				}
			}
		}
		if(i>=2 && j<=6) {
			if(chessBoard.board[i-2][j+1]!=null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i-2][j+1]) && chessBoard.board[i-2][j+1].isBlack != c) {
					return true;
				}
			}
		}
		if(i>=2 && j>=1) {
			if(chessBoard.board[i-2][j-1]!=null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i-2][j-1]) && chessBoard.board[i-2][j-1].isBlack != c) {
					return true;
				}
			}
		}
		if(i<=6 && j<=5) {
			if(chessBoard.board[i+1][j+2]!=null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i+1][j+2]) && chessBoard.board[i+1][j+2].isBlack != c) {
					return true;
				}
			}
		}
		if(i>=1 && j<=5) {
			if(chessBoard.board[i-1][j+2]!=null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i-1][j+2]) && chessBoard.board[i-1][j+2].isBlack != c) {
					return true;
				}
			}
		}
		if(i<=6 && j>=2) {
			if(chessBoard.board[i+1][j-2]!=null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i+1][j-2]) && chessBoard.board[i+1][j-2].isBlack != c) {
					return true;
				}
			}
		}
		if(i>=1 && j>=2) {
			if(chessBoard.board[i-1][j-2]!=null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i-1][j-2]) && chessBoard.board[i-1][j-2].isBlack != c) {
					return true;
				}
			}
		}
			
		return false;
	}

	
	/**
	 * This method prints the knight object.
	 */
	public String toString() {
		if(this.checkColor()) {
			return "bN ";
		}
		return "wN ";
	}


}
