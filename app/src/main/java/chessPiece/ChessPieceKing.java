package chessPiece;

import java.lang.Math;

import chess.chessBoard;
import chess.ChessHelper;

/**
 * @author parthpatel
 * @author joshherrera
 */
public class ChessPieceKing extends ChessPiece{


	
	/**
	 * Constructor to initialize king object (boolean value true if the color is black
	 * and false for white)
	 * @param color Color of the piece (true for black false for white)
	 */
	public ChessPieceKing(boolean color) {
		this.isBlack = color;
	}
	
	
	

	
	/**
	 * This method takes current and target location for the queen and returns true if the target 
	 * location is legal.
	 * @param i Current rank of the king
	 * @param j Current file of the king
	 * @param targetI Target rank for the king
	 * @param targetJ Target file for the king
	 * @return boolean value
	 */
	public static boolean kingLegalMove(int i, int j, int targetI, int targetJ) {
		ChessPiece tempC = chessBoard.board[i][j];
		ChessPiece tempT = null;
		boolean team = chessBoard.board[i][j].isBlack;
		boolean legalMove = false;
		int n = targetI - i;
		int m = targetJ - j;
		
		// diagonal
		if(Math.abs(n)==1 && Math.abs(m)==1) {
			legalMove=true;
		}
		
		// forward/backward
		if(Math.abs(n)==1 && Math.abs(m)==0) {
			legalMove=true;
		}
		
		// sideways
		if(Math.abs(n)==0 && Math.abs(m)==1) {
			legalMove=true;
		}
		
		// castling
		if(Math.abs(m)==2 && Math.abs(n)==0) {
			// uses booleans that check if the king or rook involved has moved ever (see chessBoard)
			if(team && m>0 && !chessBoard.blackCastleKing) {
				return false;
			}
			if(!team && m>0 && !chessBoard.whiteCastleKing) {
				return false;
			}
			if(team && m<0 && !chessBoard.blackCastleQueen) {
				return false;
			}
			if(!team && m<0 && !chessBoard.whiteCastleQueen) {
				return false;
			}
			
			// skipping -> can't skip pieces AND can't skip spaces where the king would be in check.
			if(m>0) {
				// right side castle
				for(int k=j+1;k<=targetJ;k++) {
					if(chessBoard.board[i][k]!=null) {
						return false;
					}else{
						chessBoard.board[i][k] = tempC;
						chessBoard.board[i][j] = null;
						if(ChessHelper.inCheck(team)) {
							chessBoard.board[i][k] = null;
							chessBoard.board[i][j] = tempC;
							return false;
						}
						chessBoard.board[i][k] = null;
					}
				}
			}else{
				// left side castle
				for(int k=j-1;k>=targetJ;k--) {
					if(chessBoard.board[i][k]!=null) {
						return false;
					}else{
						chessBoard.board[i][k] = tempC;
						chessBoard.board[i][j] = null;
						if(ChessHelper.inCheck(team)) {
							chessBoard.board[i][k] = null;
							chessBoard.board[i][j] = tempC;
							return false;
						}
						chessBoard.board[i][k] = null;
					}
				}
			}
			chessBoard.board[i][j] = tempC;
			
			// if king is in check -> illegal
			if(ChessHelper.inCheck(team)) {
				return false;
			}
			
			legalMove=true;
			
			// moves rook behind king if move is legal.
			if(!team && m>0) {
				ChessPieceRook.movePiece(7, 7, i, j+1);
			}
			if(team && m>0) {
				ChessPieceRook.movePiece(0, 7, i, j+1);
			}
			if(team && m<0) {
				ChessPieceRook.movePiece(0, 0, i, j-1);
			}
			if(!team && m<0) {
				ChessPieceRook.movePiece(7, 0, i, j-1);
			}
		}
		
		// compare the current piece to the target piece and make sure they are not of same color
		if(sameColor(i,j,targetI,targetJ)){
			return false;
		}
		
		return legalMove;
	}

	
	
	/**
	 * This method takes current location of the king and returns true if it can send a check to opponent's king.
	 * @param i Current rank of the king
	 * @param j Current file of the king
	 * @return boolean value
	 */
	public static boolean kingCanCheck(int i, int j) {
		boolean color = chessBoard.board[i][j].isBlack;
		
		for(int p=0;p<8;p++) {
			for(int q=0;q<8;q++) {
				if(Math.abs(p-i) <= 1 && Math.abs(q-j) <= 1) {
					if(ChessPieceKing.kingLegalMove(i, j, p, q)) {
						if(chessBoard.board[p][q]!=null) {
							if(ChessPieceKing.class.isInstance(chessBoard.board[p][q]) && chessBoard.board[p][q].isBlack != color) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}

	
	
	/**
	 * This method prints the king object.
	 */
	public String toString() {
		if(this.checkColor()) {
			return "bK ";
		}
		return "wK ";
	}
}
