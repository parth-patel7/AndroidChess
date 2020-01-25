package chessPiece;

import chess.ChessHelper;
import chess.chessBoard;
import java.lang.Math;

/**
 * @author parthpatel
 * @author joshherrera
 */
public class ChessPiecePawn extends ChessPiece{


	/* Pawn rules:
	 * - can move 2 spaces forward on starting position, one space forward otherwise
	 * - cannot move backwards
	 * - can move forward diagonally one space ONLY if a piece is there to capture.
	 */

	
	/** 
	 * Constructor to initialize pawn object (boolean value true if the color is black
	 * and false for white)
	 * @param color Color of the piece (true for black false for white)
	 */
	public ChessPiecePawn(boolean color) { 
		this.isBlack = color;
	}

	
	
	
	
	/**
	 * This method takes current and target location for the pawn and returns true if the target 
	 * location is legal.
	 * @param i Current rank of the pawn
	 * @param j Current file of the pawn
	 * @param targetI Target rank for the pawn
	 * @param targetJ Target file for the pawn
	 * @return boolean value
	 */
	public static boolean pawnLegalMove(int i, int j, int targetI, int targetJ) {

		boolean legalMove = false;
		boolean attacking = false;
		int n = targetI - i;
		int m = targetJ - j;


		if(Math.abs(m)>1) {
			return false;
		}

		if(j != targetJ) {
			if(chessBoard.board[i][j].isBlack) {
				if(chessBoard.board[i+1][targetJ] == null && ChessHelper.enPassantCounter == 2) {
					if(checkForEnPassant()) {
						if((i == chessBoard.canEnPassantHere[0]) &&
								(targetJ == chessBoard.canEnPassantHere[1])){
							ChessHelper.enPassantDone = 1;
							chessBoard.canEnPassantHere[0] = -1;
							ChessHelper.enPassantCounter = 0;
							return true;
						}
					} else {
						return false;
					}

				}
			}else if(!chessBoard.board[i][j].isBlack) {

				if(chessBoard.board[i-1][targetJ] == null && ChessHelper.enPassantCounter == 2) {
					if(checkForEnPassant()) {	
						if((i == chessBoard.canEnPassantHere[0]) &&
								(targetJ == chessBoard.canEnPassantHere[1])){
							//System.out.println("Here 2 " + i + targetJ + "\n");
							ChessHelper.enPassantDone = 1;
							chessBoard.canEnPassantHere[0] = -1;
							ChessHelper.enPassantCounter = 0;
							return true;
						}
					} else {
						return false;
					}
				}
			}
		}

		// checks if pawn tries to move diagonally -> must be a piece in the target space.
		if(Math.abs(m)==1) {

			//System.out.println("m " + m + "\n");
			if(chessBoard.board[i][j].isBlack) {




				if(chessBoard.board[i+1][targetJ] != null ) {
					legalMove = true;
					attacking = true;
				} else { return false; }
			} else {

				if(chessBoard.board[i-1][targetJ] != null) {
					legalMove = true;
					attacking = true;
				} else { return false; }
			}
		}

		// (black) pawn cannot move backward
		if(chessBoard.board[i][j].isBlack && n<0) {
			return false;
		}

		// (white) pawn cannot move backward
		if(!chessBoard.board[i][j].isBlack && n>0) {
			return false;
		}

		// if (black) pawn is at starting position -> can move 2 spaces fwd unless there's a piece in between
		if (chessBoard.board[i][j].isBlack && i==1 && Math.abs(n) == 2) {
			if(chessBoard.board[targetI-1][j] == null && chessBoard.board[targetI][j] == null) {
				legalMove = true;
			} else { return false; }
			// if (white) pawn is at starting position -> can move 2 spaces fwd unless there's a piece in between
		} else if (!chessBoard.board[i][j].isBlack && i==6 && Math.abs(n) == 2) {
			if(chessBoard.board[targetI+1][j] == null && chessBoard.board[targetI][j] == null) {
				legalMove = true;
			} else { return false; }
			// if not at starting position -> can only move 1 space
		} else if(Math.abs(n) == 1) {
			if(!attacking) {
				if(chessBoard.board[i][j].isBlack) {
					if(chessBoard.board[i+1][j] == null) {
						legalMove = true;
					} else { return false; }
				} else {
					if(chessBoard.board[i-1][j] == null) {
						legalMove = true;
					} else { return false; }
				}
			}
		} else {
			return false;
		}

		// compare the current piece to the target piece and make sure they are not of same color
		if(sameColor(i,j,targetI,targetJ)){
			return false;
		}

		return legalMove;
	}


	
	

	/**
	 * This method takes current location of the pawn and returns true if it can send a check to opponent's king.
	 * @param i Current rank of the pawn
	 * @param j Current file of the pawn
	 * @return boolean value
	 */
	public static boolean pawnCanCheck (int i, int j) {


		int down = i + 1;
		int up = i - 1;
		int left = j - 1;
		int right = j + 1;

		boolean color = chessBoard.board[i][j].isBlack;

		if(i + 1 > 7 || i - 1 < 0) {
			return false;
		}

		if(color) {
			if(j != 7) {
				if(chessBoard.board[down][right] != null) {
					if(ChessPieceKing.class.isInstance(chessBoard.board[down][right]) && chessBoard.board[down][right].isBlack != color) {
						return true;
					}
				}
			}
			if(j != 0) {
				if(chessBoard.board[down][left] != null) {
					if(ChessPieceKing.class.isInstance(chessBoard.board[down][left]) && chessBoard.board[down][left].isBlack != color) {
						return true;
					}
				}
			}
		} else {
			if(j != 7) {
				if(chessBoard.board[up][right] != null) {
					if(ChessPieceKing.class.isInstance(chessBoard.board[up][right]) && chessBoard.board[up][right].isBlack != color) {
						return true;
					}
				}
			}
			if(j != 0) {
				if(chessBoard.board[up][left] != null) {
					if(ChessPieceKing.class.isInstance(chessBoard.board[up][left]) && chessBoard.board[up][left].isBlack != color) {
						return true;
					}
				}
			}
		}
		return false;
	}




	/**
	 * This method return boolean value true if the there is a pawn which can be removed by en-passant
	 * @return boolean value
	 */
	public static boolean checkForEnPassant() { 
		if(chessBoard.canEnPassantHere[0] == -1) {
			return false;
		}
		return true;
	}

	
	
	/**
	 * This method takes current location and target location of the pawn and 
	 * returns true if legal en-passant move is being made 
	 * @param i Current rank of the pawn piece
	 * @param j Current file of the pawn piece
	 * @param targetI Target rank for that pawn piece
	 * @param targetJ Target file for that pawn piece
	 * @return boolean value
	 */
	public static boolean canEnPassantMove(int i, int j, int targetI, int targetJ) {
		boolean res = false;
		if(pawnLegalMove( i,  j,  targetI, targetJ)) {
			if (Math.abs(i-targetI) == 2) {
				chessBoard.canEnPassantHere[0] = targetI;
				chessBoard.canEnPassantHere[1] = targetJ;
				res = true;
			}
		}
		if(res == true) {
			ChessHelper.enPassantCounter = 1;
		}
		return res;
	}



	/**
	 * This method prints the pawn object.
	 */
	public String toString() {
		if(this.checkColor()) {
			return "bp ";
		}
		return "wp ";
	}
}
