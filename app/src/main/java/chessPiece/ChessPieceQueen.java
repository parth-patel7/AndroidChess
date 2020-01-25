package chessPiece;
import chess.*;

/**
 * @author parthpatel
 * @author joshherrera
 */

public class ChessPieceQueen extends ChessPiece{

	/**
	 * Constructor to initialize queen object (boolean value true if the color is black
	 * and false for white)
	 * @param color Color of the piece (true for black false for white)
	 */
	public ChessPieceQueen(boolean color) {
		this.isBlack = color;
	}


	/*
	 * Legal Moves
	 * Both as Bishop or Rook
	 * can't skip pieces.
	 */


	/**
	 * This method takes current and target location for the queen and returns true if the target 
	 * location is legal.
	 * @param currentI Current rank of the queen
	 * @param currentJ Current file of the queen
	 * @param targetI Target rank for the queen
	 * @param targetJ Target file for the queen
	 * @return boolean value
	 */
	public static boolean queenLegalMove(int currentI, int currentJ, int targetI, int targetJ) {

		if(!ChessHelper.isWithInBoard(targetI, targetJ)) {
			return false;
		}

		if( (currentI != targetI && currentJ != targetJ) ) {
			return ChessPieceBishop.bishopLegalMove(currentI, currentJ, targetI, targetJ);
		} else {
			return ChessPieceRook.rookLegalMove(currentI, currentJ, targetI, targetJ);
		}

	}
	
	/**
	 * This method takes current location of the queen and returns true if it can send a check to opponent's king
	 * @param i Current rank of the queen
	 * @param j Current file of the queen
	 * @return boolean value
	 */
	public static boolean queenCanCheck(int i, int j) {
		if(ChessPieceBishop.bishopCanCheck(i, j)) {
			return true;
		}else if(ChessPieceRook.rookCanCheck(i, j)) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * This method prints the queen object.
	 */
	public String toString() {
		if(this.checkColor()) {
			return "bQ ";
		}
		return "wQ ";
	}
}
