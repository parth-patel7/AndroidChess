package chess;

import java.util.*;

import chessPiece.*;

import android.util.Log;
import android.widget.Toast;

import com.example.androidchess.Moves;
import com.example.androidchess.PlayGame;

/**
 * @author parthpatel
 * @author joshherrera
 */
public class chessBoard {

    public static boolean whiteCastleKing = true;
    public static boolean whiteCastleQueen = true;
    public static boolean blackCastleKing = true;
    public static boolean blackCastleQueen = true;

    public static boolean canPromo = false;
    public static Character promo;
    public static boolean promoColor;

    public static ChessPiece [][] board = new ChessPiece[9][9];
    public static int [] canEnPassantHere = new int[2];


    public static int counter = 0;
    public static boolean drawMode = false;
    static int drawCounter = 0;


    public static ChessPiece[][] oldBoard = new ChessPiece[9][9];
    public static boolean[] flags = new boolean[7];
    public static int [] oldEnPassantHere = new int[2];
    public static int oldEnPassantCounter;
    public static int oldEnPassantDone;

    /**
     * This method loads all the pieces in the chess board array
     */
    public static void loadGame() {

        // Filling all black pieces
        board[0][0] = new ChessPieceRook(true);
        board[0][1] = new ChessPieceKnight(true);
        board[0][2] = new ChessPieceBishop(true);
        board[0][3] = new ChessPieceQueen(true);
        board[0][4] = new ChessPieceKing(true);
        board[0][5] = new ChessPieceBishop(true);
        board[0][6] = new ChessPieceKnight(true);
        board[0][7] = new ChessPieceRook(true);


        // Filling all white pieces
        board[7][0] = new ChessPieceRook(false);
        board[7][1] = new ChessPieceKnight(false);
        board[7][2] = new ChessPieceBishop(false);
        board[7][3] = new ChessPieceQueen(false);
        board[7][4] = new ChessPieceKing(false);
        board[7][5] = new ChessPieceBishop(false);
        board[7][6] = new ChessPieceKnight(false);
        board[7][7] = new ChessPieceRook(false);

        // Filling all black pawns
        for(int i=0; i<8; i++) {
            board[1][i] = new ChessPiecePawn(true);
        }

        // Filling all white pawns
        for(int i=0; i<8; i++) {
            board[6][i] = new ChessPiecePawn(false);
        }

        //Log.d("MYINT", "loaded");
    }


    // added in android
    public static void resetGame() {
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                board[i][j] = null;
            }
        }
        loadGame();
        whiteCastleKing = true;
        whiteCastleQueen = true;
        blackCastleKing = true;
        blackCastleQueen = true;

        canPromo = false;
        Character promo;

        promoColor = false; // should this be set to false as default?

        canEnPassantHere = new int[2];

        counter = 0;
        drawMode = false;
        drawCounter = 0;

        ChessHelper.enPassantCounter = 0;
        ChessHelper.enPassantDone = 0;
    }

    /**
     * This method prints the chess board
     */
    public static void printBoard() {

        for(int i =0; i<9; i++) {
            if(i == 8) {
                System.out.println(" a  b  c  d  e  f  g  h");
                break;
            }
            for(int j =0; j<9; j++) {
                if (j==8) {
                    System.out.print(8-i);
                } else {
                    if(board[i][j] == null) {
                        if(i%2 != 0) {
                            if(j%2 == 0) {
                                System.out.print("## ");
                            } else {
                                System.out.print("   ");
                            }
                        } else {
                            if(j%2 != 0) {
                                System.out.print("## ");
                            } else {
                                System.out.print("   ");
                            }
                        }

                    } else {
                        System.out.print(board[i][j].toString());
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }


    /**
     * This method runs the game
     * checks if the move and turns are legal
     * if there is a check, checkmate or stalemate
     */
    /*public static void runGame(int cI, int cJ, int tI, int tJ) {

        int counter = 0;
        int currentI = cI ,currentJ = cJ, targetI = tI, targetJ = tJ;
        String input = null;
        boolean drawMode = false;
        int drawCounter = 0;

        while(true) {

            Scanner scanner = new Scanner(System.in);

            //*
            if(chessBoard.canEnPassantHere[0] != -1) {
                ChessHelper.enPassantCounter++;
            }
            if(ChessHelper.enPassantCounter == 3) {
                ChessHelper.enPassantCounter = 0;
                chessBoard.canEnPassantHere[0] = -1;
            }
            //*



            if(counter%2 == 0) {
                // check if the piece is black
                System.out.print("White's move:");
                input = scanner.nextLine();
                if(input.equalsIgnoreCase("resign")) {
                    System.out.print("Black wins");
                    break;
                }
                System.out.println();

            } else {
                // check if the piece is white
                System.out.print("Black's move:");
                input = scanner.nextLine();
                if(input.equalsIgnoreCase("resign")) {
                    System.out.print("White wins");
                    break;
                }
                System.out.println();
            }

            if(input.endsWith("draw?")) {
                drawCounter = 1;
            }

            if(input.endsWith("draw")) {
                if(drawCounter == 1) {
                    System.out.println("draw");
                    break;
                }
            }

            if(!input.endsWith("draw") && !input.endsWith("draw?")) {
                if(drawCounter == 1) {
                    drawCounter = 0;
                }
            }

            currentI = ChessHelper.getCurrentI(input);
            currentJ = ChessHelper.getCurrentJ(input);
            targetI = ChessHelper.getTargetI(input);
            targetJ = ChessHelper.getTargetJ(input);


            if(input.contains("Q") || input.contains("N") || input.contains("R") || input.contains("B")) {
                wantPromo=true;
            }

            // Checks White and Black turns
            if(!ChessHelper.checkTurn(counter,currentI,currentJ)) {
                ChessHelper.enPassantCounter--;
                continue;
            }


            // If turn is correct, move on
            if(!ChessHelper.movePiece(currentI, currentJ, targetI, targetJ)) {
                System.out.println("Illegal move, try again\n");
                ChessHelper.enPassantCounter--;
                continue;
            }

            // (pawn promotion)
            if(canPromo) {
                promo = input.charAt(6);
                promoColor = board[targetI][targetJ].isBlack;

                if(promo.equals('N')) {
                    board[targetI][targetJ] = new ChessPieceKnight(promoColor);
                }
                if(promo.equals('R')) {
                    board[targetI][targetJ] = new ChessPieceRook(promoColor);
                }
                if(promo.equals('Q')) {
                    board[targetI][targetJ] = new ChessPieceQueen(promoColor);
                }
                if(promo.equals('B')) {
                    board[targetI][targetJ] = new ChessPieceBishop(promoColor);
                }
                wantPromo=false;
                canPromo=false;
            }

            chessBoard.printBoard();

            // (end game):
            // if: in check && cannot uncheck -> checkmate
            // if: not in check && cannot uncheck -> stalemate
            // if: not in check && can uncheck -> continue
            if(ChessHelper.inCheck(!chessBoard.board[targetI][targetJ].isBlack)) {
                System.out.println("Check");
                // Detect for checkmate
                if(!ChessHelper.canUncheck(!chessBoard.board[targetI][targetJ].isBlack)) {
                    System.out.println("Checkmate");
                    if(chessBoard.board[targetI][targetJ].isBlack) {
                        System.out.println("Black Wins");
                    }else{
                        System.out.println("White Wins");

                    }
                    break;
                }
            }else {
                // Detect for stalemate
                if(!ChessHelper.canUncheck(!chessBoard.board[targetI][targetJ].isBlack)) {
                    System.out.println("Stalemate");
                    System.out.println("draw");
                    break;
                }
            }

            // (castling): 	determine if either king has moved, if so, that team cannot castle.
            if(ChessPieceKing.class.isInstance(board[targetI][targetJ])) {
                if(board[targetI][targetJ].isBlack) {
                    blackCastleKing = false;
                    blackCastleQueen = false;
                } else {
                    whiteCastleKing = false;
                    whiteCastleQueen = false;
                }

            }

            // (castling): 	determine if attempted rook has ever moved or had been captured,
            //				if so, that team cannot castle on that side.
            if((currentI==7 && currentJ==7) || (targetI==7 && targetJ==7)) {
                whiteCastleKing = false;
            }
            if((currentI==7 && currentJ==0) || (targetI==7 && targetJ==0)) {
                whiteCastleQueen = false;
            }
            if(currentI==0 && currentJ==0 || (targetI==0 && targetJ==0)) {
                blackCastleQueen = false;
            }
            if(currentI==0 && currentJ==7 || (targetI==0 && targetJ==7)) {
                blackCastleKing = false;
            }

            counter++;
        }
    }*/


    public static void playGame(int currentI, int currentJ, int targetI, int targetJ) {

        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++){
                oldBoard[i][j] = board[i][j];
            }
        }
        flags[0]=whiteCastleKing;
        flags[1]=whiteCastleQueen;
        flags[2]=blackCastleKing;
        flags[3]=blackCastleQueen;
        flags[4]=canPromo;
        flags[5]=promoColor;
        flags[6]=drawMode;
        oldEnPassantHere[0]=canEnPassantHere[0];
        oldEnPassantHere[1]=canEnPassantHere[1];
        oldEnPassantCounter=ChessHelper.enPassantCounter;
        oldEnPassantDone=ChessHelper.enPassantDone;

        String input = "-";

        //*
        if (chessBoard.canEnPassantHere[0] != -1) {
            ChessHelper.enPassantCounter++;
        }
        if (ChessHelper.enPassantCounter == 3) {
            ChessHelper.enPassantCounter = 0;
            chessBoard.canEnPassantHere[0] = -1;
        }
        //*


        if (input.endsWith("draw?")) {
            drawCounter = 1;
        }

        if (input.endsWith("draw")) {
            if (drawCounter == 1) {
                System.out.println("draw");
                return;
            }
        }

        if (!input.endsWith("draw") && !input.endsWith("draw?")) {
            if (drawCounter == 1) {
                drawCounter = 0;
            }
        }

        // Checks White and Black turns
        if (!ChessHelper.checkTurn(counter, currentI, currentJ)) {
            PlayGame.illegalTurn = 1;
            ChessHelper.enPassantCounter--;
            return;
        }


        // If turn is correct, move on
        if (!ChessHelper.movePiece(currentI, currentJ, targetI, targetJ)) {
            PlayGame.illegalMove = 1;
            ChessHelper.enPassantCounter--;
            return;
        }

        // (pawn promotion)
        /*if (canPromo) {
            promo = input.charAt(6);
            promoColor = board[targetI][targetJ].isBlack;

            if (promo.equals('N')) {
                board[targetI][targetJ] = new ChessPieceKnight(promoColor);
            }
            if (promo.equals('R')) {
                board[targetI][targetJ] = new ChessPieceRook(promoColor);
            }
            if (promo.equals('Q')) {
                board[targetI][targetJ] = new ChessPieceQueen(promoColor);
            }
            if (promo.equals('B')) {
                board[targetI][targetJ] = new ChessPieceBishop(promoColor);
            }
            canPromo = false;
        }*/

        // (end game):
        // if: in check && cannot uncheck -> checkmate
        // if: not in check && cannot uncheck -> stalemate
        // if: not in check && can uncheck -> continue
        if (ChessHelper.inCheck(!chessBoard.board[targetI][targetJ].isBlack)) {
            System.out.println("Check");
            // Detect for checkmate
            if (!ChessHelper.canUncheck(!chessBoard.board[targetI][targetJ].isBlack)) {
                System.out.println("Checkmate");
                if (chessBoard.board[targetI][targetJ].isBlack) {
                    System.out.println("Black Wins");
                } else {
                    System.out.println("White Wins");
                }
                PlayGame.checkMate=true;
                return;
            }
        } else {
            // Detect for stalemate
            if (!ChessHelper.canUncheck(!chessBoard.board[targetI][targetJ].isBlack)) {
                System.out.println("Stalemate");
                System.out.println("draw");
                PlayGame.stalemate=true;
                return;
            }
        }

        // (castling): 	determine if either king has moved, if so, that team cannot castle.
        if (ChessPieceKing.class.isInstance(board[targetI][targetJ])) {
            if (board[targetI][targetJ].isBlack) {
                blackCastleKing = false;
                blackCastleQueen = false;
            } else {
                whiteCastleKing = false;
                whiteCastleQueen = false;
            }

        }

        // (castling): 	determine if attempted rook has ever moved or had been captured,
        //				if so, that team cannot castle on that side.
        if ((currentI == 7 && currentJ == 7) || (targetI == 7 && targetJ == 7)) {
            whiteCastleKing = false;
        }
        if ((currentI == 7 && currentJ == 0) || (targetI == 7 && targetJ == 0)) {
            whiteCastleQueen = false;
        }
        if (currentI == 0 && currentJ == 0 || (targetI == 0 && targetJ == 0)) {
            blackCastleQueen = false;
        }
        if (currentI == 0 && currentJ == 7 || (targetI == 0 && targetJ == 7)) {
            blackCastleKing = false;
        }

        counter++;
        return;
    }

    public static void undoMove(){
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++){
                board[i][j] = oldBoard[i][j];
            }
        }

        whiteCastleKing=flags[0];
        whiteCastleQueen=flags[1];
        blackCastleKing=flags[2];
        blackCastleQueen=flags[3];
        canPromo=flags[4];
        promoColor=flags[5];
        drawMode=flags[6];
        canEnPassantHere[0]=oldEnPassantHere[0];
        canEnPassantHere[1]=oldEnPassantHere[1];
        ChessHelper.enPassantCounter=oldEnPassantCounter;
        ChessHelper.enPassantDone=oldEnPassantDone;

        counter--;
    }
}



