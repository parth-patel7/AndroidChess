package chessPiece;
import chess.*;

/**
 * @author parthpatel
 * @author joshherrera
 */
public class ChessPieceRook extends ChessPiece{

	
	
	/**
	 * Constructor to initialize rook object (boolean value true if the color is black
	 * and false for white)
	 * @param color Color of the piece (true for black false for white)
	 */
	public ChessPieceRook(boolean color) {
		this.isBlack = color;
	}


	/*
	 * Legal Moves (2)
	 * in I direction or J direction
	 * can't skip pieces.
	 */

	
	/**
	 * This method takes current and target location for the rook and returns true if the target 
	 * location is legal.
	 * @param currentI Current rank of the rook
	 * @param currentJ Current file of the rook
	 * @param targetI Target rank for the rook
	 * @param targetJ Target file for the rook
	 * @return boolean value
	 */
	public static boolean rookLegalMove(int currentI, int currentJ, int targetI, int targetJ) {

		if(!ChessHelper.isWithInBoard(targetI, targetJ)) {
			return false;
		}
		// As rook moves in I or J direction, If it is moving in I direction then I will same as target vice versa
		if( (currentI != targetI && currentJ != targetJ) ) {
			return false;
		}

		if(sameColor(currentI,currentJ,targetI, targetJ)) {
			return false;
		}

		// if moving in I direction
		if( (currentI != targetI && currentJ == targetJ)){
			if(!isPathClearI(currentI, currentJ, targetI, targetJ)) {
				return false;
			}
		}

		// if moving in J direction
		if( (currentI == targetI && currentJ != targetJ)){
			if(!isPathClearJ(currentI, currentJ, targetI, targetJ)) {
				return false;
			}
		}

		return true;
	}



	/**
	 * This method takes in current and target location for the rook and checks if there is any piece blocking the
	 * path (In I direction) returns true if nothing is blocking and false otherwise
	 * @param i Current rank of the rook
	 * @param j Current file of the rook
	 * @param targetI Target rank for the rook
	 * @param targetJ Target file for the rook
	 * @return boolean value
	 */
	public static boolean isPathClearI(int i, int j, int targetI, int targetJ) {
		if(i > targetI) {
			for(int k=i-1; k>targetI; k--) {
				if(chessBoard.board[k][j] != null) {
					return false;
				}
			}
		}else{
			for(int k=i+1; k<targetI; k++) {
				if(chessBoard.board[k][j] != null) {
					return false;
				}
			}
		}
		
		return true;
	}


	
	/**
	 * This method takes in current and target location for the rook and checks if there is any piece blocking the
	 * path (In J direction) returns true if nothing is blocking and false otherwise
	 * @param i Current rank of the rook
	 * @param j Current file of the rook
	 * @param targetI Target rank for the rook
	 * @param targetJ Target file for the rook
	 * @return boolean value
	 */
	public static boolean isPathClearJ(int i, int j, int targetI, int targetJ) {
		if(j > targetJ) {
			for(int k=j-1; k>targetJ; k--) {
				if(chessBoard.board[i][k] != null) {
					return false;
				}
			}
		}else{
			for(int k=j+1; k<targetJ; k++) {
				if(chessBoard.board[i][k] != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	/**
	 * This method takes current location of the rook and returns true if it can send a check to opponent's king.
	 * @param i Current rank of the rook
	 * @param j Current file of the rook
	 * @return boolean value
	 */
	public static boolean rookCanCheck(int i, int j) {
		int up,down,left,right;
		up = i;
		down = i;
		left = j;
		right = j;
		
		while(down<7) {
			down = down+1;
			if(chessBoard.board[down][j] != null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[down][j])) {
					if(chessBoard.board[i][j].isBlack != chessBoard.board[down][j].isBlack) {
						return true;
					}
				}else{ break; }
			}
		}
		down = i;
		
		while(right<7) {
			right = right+1;
			if(chessBoard.board[i][right] != null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i][right])) {
					if(chessBoard.board[i][j].isBlack != chessBoard.board[i][right].isBlack) {
						return true;
					}
				}else{ break; }
			}
		}
		right = j;
		
		while(left>0) {
			left = left-1;
			if(chessBoard.board[i][left] != null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[i][left])) {
					if(chessBoard.board[i][j].isBlack != chessBoard.board[i][left].isBlack) {
						return true;
					}
				}else{ break; }
			}
		}
		left = j;
		
		while(up>0) {
			up = up-1;
			if(chessBoard.board[up][j] != null) {
				if(ChessPieceKing.class.isInstance(chessBoard.board[up][j])) {
					if(chessBoard.board[i][j].isBlack != chessBoard.board[up][j].isBlack) {
						return true;
					}
				}else{ break; }
			}
		}
		
		return false;
	}

	
	
	/**
	 * This method prints the rook object.
	 */
	public String toString() {
		if(this.checkColor()) {
			return "bR ";
		}
		return "wR ";
	}
}
