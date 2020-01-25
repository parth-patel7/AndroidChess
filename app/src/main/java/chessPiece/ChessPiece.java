package chessPiece;
import chess.*;

/**
 * @author parthpatel
 * @author joshherrera
 */
public class ChessPiece {

	public boolean isBlack;

	/**
	 * Super class constructor 
	 */
	public ChessPiece() {
	}

	/**
	 * This method checks if the piece is black or white color
	 * and return true if it is black or false if it is white.
	 * @return boolean value
	 */
	public boolean checkColor(){
		if(this.isBlack == true)
			return true;
		else 
			return false;
	}

	
	/**
	 * This method moves the piece from current location to target location.
	 * @param i Current rank of the piece
	 * @param j Current file of the piece
	 * @param ti Target rank for the piece
	 * @param tj Target file for the piece
	 */
	public static void movePiece(int i, int j, int ti, int tj) {
		if(chessBoard.board[ti][tj] != null) {
			chessBoard.board[ti][tj] = null;
		}
		chessBoard.board[ti][tj] = chessBoard.board[i][j];
		chessBoard.board[i][j] = null;
	}
	
	


	/**
	 * This method checks if the current and target piece are of same color.
	 * Return true if they are of same color and false other wise
	 * @param i Current rank of the piece
	 * @param j Current file of the piece
	 * @param ti Target rank for the piece
	 * @param tj Target file for the piece
	 * @return boolean value
	 */
	public static boolean sameColor(int i, int j, int ti, int tj) {
		if(chessBoard.board[i][j] != null && chessBoard.board[ti][tj] != null) {
			if(chessBoard.board[i][j].isBlack == chessBoard.board[ti][tj].isBlack) {
				return true;
			}
		}
		return false;
	}


	/**
	 * super class toString method
	 */
	public String toString() {
		return "";
	}

}

