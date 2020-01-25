package chess;


import java.util.Random;

import chessPiece.*;

/**
 * @author parthpatel
 * @author joshherrera
 *
 */
public class ChessHelper {


    public static int enPassantCounter = 0;
    public static int enPassantDone = 0;
    static int[] swap = {8,7,6,5,4,3,2,1,0};
    public static int[] indicies = new int[4];

    /**
     * This method takes the user input (String) and returns the current rank in which the piece is.
     * @param input user input
     * @return current rank index of the piece
     */
    public static int getCurrentI(String input) {
        return swap[Character.getNumericValue(input.charAt(1))];
    }


    /**
     * This method takes the user input (String) and returns the current file in which the piece is.
     * @param input user input
     * @return current file index of the piece
     */
    public static int getCurrentJ(String input) {
        return (int)input.charAt(0)-(int)'a';
    }


    /**
     * This method takes the user input (String) and returns the target rank for the piece.
     * @param input user input
     * @return target rank index for the piece
     */
    public static int getTargetI(String input) {
        return swap[Character.getNumericValue(input.charAt(4))];
    }


    /**
     * This method takes the user input (String) and returns the target file for the piece.
     * @param input user input
     * @return target file index for the piece
     */
    public static int getTargetJ(String input) {
        return (int)input.charAt(3)-(int)'a';
    }



    /**
     * This method takes current and target location for piece and returns true if the piece
     * was able to move false otherwise
     * @param i current rank index of the piece
     * @param j current file index of the piece
     * @param ti target rank index for the piece
     * @param tj target file index for the piece
     * @return boolean value
     */
    public static boolean movePiece(int i, int j, int ti, int tj) {

        ChessPiece tempC = chessBoard.board[i][j];
        ChessPiece tempT = null;

        boolean color = chessBoard.board[i][j].isBlack;

        if(chessBoard.board[ti][tj] != null) {
            tempT = chessBoard.board[ti][tj];
        }

        // Pawn
        if(ChessPiecePawn.class.isInstance(chessBoard.board[i][j])) {
            if(ChessPiecePawn.pawnLegalMove(i, j, ti, tj)) {

                //
                ChessPiecePawn.canEnPassantMove(i, j, ti, tj);
                //

                // promotion
                if(ti==7 || ti==0) {
                    chessBoard.canPromo=true;
                }

                ChessPiece.movePiece(i, j , ti, tj);

                if(enPassantDone == 1) {
                    chessBoard.board[i][tj] = null;
                    enPassantDone = 0;

                }

                if(inCheck(color)) { // if true, revert pieces back to old state using temps
                    chessBoard.board[i][j] = tempC;
                    chessBoard.board[ti][tj] = tempT;
                    chessBoard.canEnPassantHere[0] = -1;
                    return false;
                }

                return true;
            }
        }

        // Knight
        if(ChessPieceKnight.class.isInstance(chessBoard.board[i][j])) {
            if(ChessPieceKnight.knightLegalMove(i, j, ti, tj)) {
                ChessPiece.movePiece(i, j , ti, tj);
                if(inCheck(color)) {
                    chessBoard.board[i][j] = tempC;
                    chessBoard.board[ti][tj] = tempT;
                    return false;
                }

                return true;
            }
        }

        // Rook
        if(ChessPieceRook.class.isInstance(chessBoard.board[i][j])) {
            if(ChessPieceRook.rookLegalMove(i, j, ti, tj)) {
                ChessPiece.movePiece(i, j , ti, tj);
                if(inCheck(color)) {
                    chessBoard.board[i][j] = tempC;
                    chessBoard.board[ti][tj] = tempT;
                    return false;
                }

                return true;
            }
        }

        // Queen
        if(ChessPieceQueen.class.isInstance(chessBoard.board[i][j])) {
            if(ChessPieceQueen.queenLegalMove(i, j, ti, tj)) {
                ChessPiece.movePiece(i, j , ti, tj);
                if(inCheck(color)) {
                    chessBoard.board[i][j] = tempC;
                    chessBoard.board[ti][tj] = tempT;
                    return false;
                }

                return true;

            }
        }

        // Bishop
        if(ChessPieceBishop.class.isInstance(chessBoard.board[i][j])) {
            if(ChessPieceBishop.bishopLegalMove(i, j, ti, tj)) {
                ChessPiece.movePiece(i, j , ti, tj);
                if(inCheck(color)) {
                    chessBoard.board[i][j] = tempC;
                    chessBoard.board[ti][tj] = tempT;
                    return false;
                }

                return true;
            }
        }

        // King
        if(ChessPieceKing.class.isInstance(chessBoard.board[i][j])) {
            if(ChessPieceKing.kingLegalMove(i, j, ti, tj)) {
                ChessPiece.movePiece(i, j , ti, tj);
                if(inCheck(color)) { // if true, revert pieces back to old state using temps
                    chessBoard.board[i][j] = tempC;
                    chessBoard.board[ti][tj] = tempT;
                    return false;
                }
                return true;
            }
        }

        return false;
    }



    /**
     * This method given a team (color), traverse's through board and checks if any piece can capture that team's king.
     * if so, team is in check. Returns true if it can and false otherwise
     * @param color Color to decide the team
     * @return boolean value
     */
    public static boolean inCheck(boolean color) {
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if(chessBoard.board[i][j] != null && chessBoard.board[i][j].isBlack != color){
                    if(ChessPiecePawn.class.isInstance(chessBoard.board[i][j])) {
                        if(ChessPiecePawn.pawnCanCheck(i, j)) {
                            return true;
                        }
                    }
                    if(ChessPieceKnight.class.isInstance(chessBoard.board[i][j])) {
                        if(ChessPieceKnight.knightCanCheck(i, j)) {
                            return true;
                        }
                    }
                    if(ChessPieceRook.class.isInstance(chessBoard.board[i][j])) {
                        if(ChessPieceRook.rookCanCheck(i, j)) {
                            return true;
                        }
                    }
                    if(ChessPieceBishop.class.isInstance(chessBoard.board[i][j])) {
                        if(ChessPieceBishop.bishopCanCheck(i, j)) {
                            return true;
                        }
                    }
                    if(ChessPieceQueen.class.isInstance(chessBoard.board[i][j])) {
                        if(ChessPieceQueen.queenCanCheck(i, j)) {
                            return true;
                        }
                    }
                    if(ChessPieceKing.class.isInstance(chessBoard.board[i][j])) {
                        if(ChessPieceKing.kingCanCheck(i, j)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }





    /**
     * This method given a team (color), traverse's through board and checks if any same color piece can legally
     * move to a space so that team is no longer in check. Return true if it can otherwise false
     * @param color Color to decide the team
     * @return boolean value
     */
    public static boolean canUncheck(boolean color) {
        ChessPiece tempC = null;
        ChessPiece tempT = null;

        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if(chessBoard.board[i][j] != null && chessBoard.board[i][j].isBlack == color) {
                    tempC = chessBoard.board[i][j];
                    tempT = null;

                    if(ChessPiecePawn.class.isInstance(chessBoard.board[i][j])) {
                        for(int p=0;p<8;p++) {
                            for(int q=0;q<8;q++) {
                                if(ChessPiecePawn.pawnLegalMove(i, j, p, q)) {
                                    if(chessBoard.board[p][q]!=null) {
                                        tempT = chessBoard.board[p][q];
                                    }
                                    ChessPiecePawn.movePiece(i, j, p, q);
                                    if(!inCheck(color)){
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        return true;
                                    }else{
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        tempT=null;
                                    }
                                }
                            }
                        }
                        continue;
                    }
                    if(ChessPieceQueen.class.isInstance(chessBoard.board[i][j])) {
                        for(int p=0;p<8;p++) {
                            for(int q=0;q<8;q++) {
                                if(ChessPieceQueen.queenLegalMove(i, j, p, q)) {
                                    if(chessBoard.board[p][q]!=null) {
                                        tempT = chessBoard.board[p][q];
                                    }
                                    ChessPieceQueen.movePiece(i, j, p, q);
                                    if(!inCheck(color)){
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        return true;
                                    }else{
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        tempT=null;
                                    }
                                }
                            }
                        }
                        continue;
                    }
                    if(ChessPieceBishop.class.isInstance(chessBoard.board[i][j])) {
                        for(int p=0;p<8;p++) {
                            for(int q=0;q<8;q++) {
                                if(ChessPieceBishop.bishopLegalMove(i, j, p, q)) {
                                    if(chessBoard.board[p][q]!=null) {
                                        tempT = chessBoard.board[p][q];
                                    }
                                    ChessPieceBishop.movePiece(i, j, p, q);
                                    if(!inCheck(color)){
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        return true;
                                    }else{
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        tempT=null;
                                    }
                                }
                            }
                        }
                        continue;
                    }
                    if(ChessPieceRook.class.isInstance(chessBoard.board[i][j])) {
                        for(int p=0;p<8;p++) {
                            for(int q=0;q<8;q++) {
                                if(ChessPieceRook.rookLegalMove(i, j, p, q)) {
                                    if(chessBoard.board[p][q]!=null) {
                                        tempT = chessBoard.board[p][q];
                                    }
                                    ChessPieceRook.movePiece(i, j, p, q);
                                    if(!inCheck(color)){
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        return true;
                                    }else{
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        tempT=null;
                                    }
                                }
                            }
                        }
                        continue;
                    }
                    if(ChessPieceKnight.class.isInstance(chessBoard.board[i][j])) {
                        for(int p=0;p<8;p++) {
                            for(int q=0;q<8;q++) {
                                if(ChessPieceKnight.knightLegalMove(i, j, p, q)) {
                                    if(chessBoard.board[p][q]!=null) {
                                        tempT = chessBoard.board[p][q];
                                    }
                                    ChessPieceKnight.movePiece(i, j, p, q);
                                    if(!inCheck(color)){
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        return true;
                                    }else{
                                        chessBoard.board[i][j]=tempC;
                                        chessBoard.board[p][q]=tempT;
                                        tempT=null;
                                    }
                                }
                            }
                        }
                        continue;
                    }
                    if(ChessPieceKing.class.isInstance(chessBoard.board[i][j])) {
                        for(int p=0;p<8;p++) {
                            for(int q=0;q<8;q++) {
                                if(Math.abs(q-j)<2) {
                                    if(ChessPieceKing.kingLegalMove(i, j, p, q)) {
                                        if(chessBoard.board[p][q]!=null) {
                                            tempT = chessBoard.board[p][q];
                                        }
                                        ChessPieceKing.movePiece(i, j, p, q);
                                        if(!inCheck(color)){
                                            chessBoard.board[i][j]=tempC;
                                            chessBoard.board[p][q]=tempT;
                                            return true;
                                        }else{
                                            chessBoard.board[i][j]=tempC;
                                            chessBoard.board[p][q]=tempT;
                                            tempT=null;
                                        }
                                    }
                                }
                            }
                        }
                        continue;
                    }
                }
            }
        }
        return false;
    }

    public static void aiMove(boolean color) {
        Random r = new Random();
        int i = r.nextInt(8-0);
        int j = r.nextInt(8-0);
        ChessPiece tempC = null;
        ChessPiece tempT = null;

        if(chessBoard.board[i][j] != null) {
            if(chessBoard.board[i][j].isBlack == color) {
                if (ChessPiecePawn.class.isInstance(chessBoard.board[i][j])) {
                    for (int p = 0; p < 8; p++) {
                        for (int q = 0; q < 8; q++) {
                            if (ChessPiecePawn.pawnLegalMove(i, j, p, q)) {
                                tempC=chessBoard.board[i][j];
                                tempT=chessBoard.board[p][q];
                                if(movePiece(i, j, p, q)){
                                    chessBoard.board[i][j]=tempC;
                                    chessBoard.board[p][q]=tempT;
                                    indicies[0]=i; indicies[1]=j; indicies[2]=p; indicies[3]=q;
                                    return;
                                }
                            }
                        }
                    }
                }
                if (ChessPieceKnight.class.isInstance(chessBoard.board[i][j])) {
                    for (int p = 0; p < 8; p++) {
                        for (int q = 0; q < 8; q++) {
                            if (ChessPieceKnight.knightLegalMove(i, j, p, q)) {
                                tempC=chessBoard.board[i][j];
                                tempT=chessBoard.board[p][q];
                                if(movePiece(i, j, p, q)){
                                    chessBoard.board[i][j]=tempC;
                                    chessBoard.board[p][q]=tempT;
                                    indicies[0]=i; indicies[1]=j; indicies[2]=p; indicies[3]=q;
                                    return;
                                }
                            }
                        }
                    }
                }
                if (ChessPieceBishop.class.isInstance(chessBoard.board[i][j])) {
                    for (int p = 0; p < 8; p++) {
                        for (int q = 0; q < 8; q++) {
                            if (ChessPieceBishop.bishopLegalMove(i, j, p, q)) {
                                tempC=chessBoard.board[i][j];
                                tempT=chessBoard.board[p][q];
                                if(movePiece(i, j, p, q)){
                                    chessBoard.board[i][j]=tempC;
                                    chessBoard.board[p][q]=tempT;
                                    indicies[0]=i; indicies[1]=j; indicies[2]=p; indicies[3]=q;
                                    return;
                                }
                            }
                        }
                    }
                }
                if (ChessPieceRook.class.isInstance(chessBoard.board[i][j])) {
                    for (int p = 0; p < 8; p++) {
                        for (int q = 0; q < 8; q++) {
                            if (ChessPieceRook.rookLegalMove(i, j, p, q)) {
                                tempC=chessBoard.board[i][j];
                                tempT=chessBoard.board[p][q];
                                if(movePiece(i, j, p, q)){
                                    chessBoard.board[i][j]=tempC;
                                    chessBoard.board[p][q]=tempT;
                                    indicies[0]=i; indicies[1]=j; indicies[2]=p; indicies[3]=q;
                                    return;
                                }
                            }
                        }
                    }
                }
                if (ChessPieceQueen.class.isInstance(chessBoard.board[i][j])) {
                    for (int p = 0; p < 8; p++) {
                        for (int q = 0; q < 8; q++) {
                            if (ChessPieceQueen.queenLegalMove(i, j, p, q)) {
                                tempC=chessBoard.board[i][j];
                                tempT=chessBoard.board[p][q];
                                if(movePiece(i, j, p, q)){
                                    chessBoard.board[i][j]=tempC;
                                    chessBoard.board[p][q]=tempT;
                                    indicies[0]=i; indicies[1]=j; indicies[2]=p; indicies[3]=q;
                                    return;
                                }
                            }
                        }
                    }
                }
                if (ChessPieceKing.class.isInstance(chessBoard.board[i][j])) {
                    for (int p = 0; p < 8; p++) {
                        for (int q = 0; q < 8; q++) {
                            if (ChessPieceKing.kingLegalMove(i, j, p, q)) {
                                tempC=chessBoard.board[i][j];
                                tempT=chessBoard.board[p][q];
                                if(movePiece(i, j, p, q)){
                                    chessBoard.board[i][j]=tempC;
                                    chessBoard.board[p][q]=tempT;
                                    indicies[0]=i; indicies[1]=j; indicies[2]=p; indicies[3]=q;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        aiMove(color);


        return;
    }

    /**
     * This method checks if the rank or file is greater than 8
     * Returns true if it's under 8 and false other wise
     * @param i rank value
     * @param j file value
     * @return boolean value
     */
    public static boolean isWithInBoard(int i, int j) {
        if(i > 8 || j > 8) {
            return false;
        }
        return true;
    }


    /**
     * This method checks for the correct turn or
     * if the user tried to move a piece from an empty location and returns true if everything
     * is in correct order
     * @param counter -to check whose turn it is (even for white side, odd for black side)
     * @param currentI -row in which the piece is
     * @param currentJ -column in which the piece is
     * @return boolean value
     */
    public static boolean checkTurn(int counter, int currentI, int currentJ) {

        if(isCellEmpty(currentI,currentJ)) {
            System.out.println("Illegal move, try again\n");
            return false;
        }

        if(counter%2 == 0) {
            if(chessBoard.board[currentI][currentJ].checkColor()) {
                System.out.println("Illegal move, try again\n");
                return false;
            }
        } else {
            if(!chessBoard.board[currentI][currentJ].checkColor()) {
                System.out.println("Illegal move, try again\n");
                return false;
            }
        }
        return true;
    }


    /**
     * This method takes current location of the piece and checks if the piece is present or not
     * return true if the piece is present
     * @param currentI Current rank of the piece
     * @param currentJ Current file of the piece
     * @return boolean value
     */
    public static boolean isCellEmpty(int currentI, int currentJ) {
        if(chessBoard.board[currentI][currentJ] == null) {
            return true;
        }
        return false;
    }

}
