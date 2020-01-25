package com.example.androidchess;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.*;
import java.util.*;

import chess.ChessHelper;
import chess.chessBoard;
import chessPiece.*;


public class PlayGame extends AppCompatActivity{

    GridView boardGridView;
    int input = 0;
    GridViewAdaptor adaptor;
    int cI = 0, cJ = 0, tI = 0, tJ = 0;
    public static int illegalMove = 0;
    public static int illegalTurn = 0;
    public static boolean checkMate, stalemate;
    public static boolean canUndo;
    public static boolean promoKnight, promoBishop, promoQueen, promoRook;
    public static String pathToAppFolder;
    public static String filePath;

    int drawCounter = 0;

    EditText gameInput;
    String gameName = "";

    public static ArrayList<Moves> movesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pathToAppFolder = getExternalFilesDir(null).getAbsolutePath();
        filePath = pathToAppFolder +File.separator + "DataBase.txt";

        System.out.println(filePath);

        checkMate = false; stalemate = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
        chess.chessBoard.loadGame();
        movesList = new ArrayList<>();
        loadBoardGUI();
    }


    public void loadBoardGUI() {
        boardGridView = (GridView) findViewById(R.id.boardGridView);
        adaptor = new GridViewAdaptor(this);
        boardGridView.setAdapter(adaptor);

        boardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("MYINT", "value: " + position);
                // will print out the current clicked location in Run
                //Log.d("MYINT", "value: " + position / 8 + " " + position % 8);

//                if(drawFlag == 1){
//                    Toast.makeText(PlayGame.this, "Player offered to draw the game, Press Draw to end" , Toast.LENGTH_SHORT).show();
//                }

                if(drawCounter == 1) {
                    drawCounter = 0;
                }

                if (input == 0) {
                    cI = position / 8;
                    cJ = position % 8;
                    input = 1;
                } else if (input == 1) {
                    // Second input
                    tI = position / 8;
                    tJ = position % 8;

//                    Moves.moves.add(Integer.toString(cI) + " " +Integer.toString(cJ) + " " +
//                            Integer.toString(tI) + " " + Integer.toString(tI));  // Storing all moves

                    chessBoard.playGame(cI, cJ, tI, tJ); // runner

                    if(illegalMove == 1){
                        IllegalMoveToast(); illegalMove = 0; illegalTurn = 0;
                        canUndo=false;
                    }
                    else if(illegalTurn == 1) {
                        IllegalTurnToast(); illegalMove = 0; illegalTurn = 0;
                        canUndo=false;
                    }
                    else{
                        movesList.add(new Moves(cI,cJ,tI,tJ, chessBoard.counter));
                        canUndo=true;
                    }

                    if(ChessPiecePawn.class.isInstance(chessBoard.board[tI][tJ])){
                        if(tI==0 || tI==7){
                            promoPrompt(cI,cJ,tI,tJ);
                            return;
                        }
                    }

                    checkForEndgame();

                    input = 0;
                    adaptor.notifyDataSetChanged();
                    boardGridView.invalidateViews();
                    boardGridView.setAdapter(adaptor);

                }
            }
        });
    }

    public void resignPressed(View view) {
        String result = "";
        if (chess.chessBoard.counter % 2 == 0) {
            result = "Black Wins";
        } else if (chess.chessBoard.counter % 2 != 0) {
            result = "White Wins";
        }
        endGame(result);
    }

    public void drawPressed(View view){

        if(drawCounter == 0) {
            Toast.makeText(PlayGame.this, "Draw Offered, Press Draw to draw the game", Toast.LENGTH_SHORT).show();
            drawCounter = 1;
        } else if(drawCounter == 1) {
            endGame("Draw");
        }
    }

    public void undoPressed(View view){

        if(movesList.size() >= 1 && canUndo) {
            chessBoard.undoMove();
            movesList.remove(movesList.size()-1);
            canUndo=false;
            adaptor.notifyDataSetChanged();
            boardGridView.setAdapter(adaptor);
        }
        else{
            Toast.makeText(PlayGame.this, "Cannot Undo" , Toast.LENGTH_SHORT).show();
        }
    }

    public void AIPressed(View view){
        boolean color;
        int[] indicies = new int[4];
        if(chessBoard.counter%2==0){
            color=false;
        }else{color=true;}

        ChessHelper.aiMove(color);

        for(int i=0;i<4;i++){
            indicies[i] = ChessHelper.indicies[i];
        }

        chessBoard.playGame(indicies[0],indicies[1],indicies[2],indicies[3]);
        movesList.add(new Moves(indicies[0],indicies[1],indicies[2],indicies[3], chessBoard.counter));
        canUndo=true;

        if(ChessPiecePawn.class.isInstance(chessBoard.board[indicies[2]][indicies[3]])){
            if(indicies[2]==0 || indicies[2]==7){
                promoPrompt(indicies[0],indicies[1],indicies[2],indicies[3]);
                return;
            }
        }

        checkForEndgame();
        adaptor.notifyDataSetChanged();
        boardGridView.setAdapter(adaptor);
    }

    public void IllegalMoveToast(){
        Toast.makeText(PlayGame.this, "Illegal Move" , Toast.LENGTH_SHORT).show();
    }

    public void IllegalTurnToast(){
        Toast.makeText(PlayGame.this, "Illegal Turn" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PlayGame.this, MainActivity.class));
        finish();
    }

    // Gives option to save game at the end.
    public void endGame(String result){
        AlertDialog.Builder endAlert = new AlertDialog.Builder(this);

        endAlert.setTitle(result + "!");
        endAlert.setMessage("Save game?");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setHint("Save game as..");
        endAlert.setView(input);

        endAlert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String gameName = input.getText().toString();
                System.out.println(gameName);

                Game game = new Game(movesList, gameName);
                ReplayGame.gameList.add(game);
                writeGamesToFile();
                boardGridView.setOnItemClickListener(null);
                onBackPressed();
            }
        });

        endAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                onBackPressed();
            }
        });

        endAlert.show();
    }

    public void promoPrompt(final int ci, final int cj, final int ti, final int tj){
        LayoutInflater layoutInfalter = LayoutInflater.from(PlayGame.this);
        View prompt = layoutInfalter.inflate(R.layout.promo_box, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(PlayGame.this);
        alert.setView(prompt);

        Button knight = (Button) prompt.findViewById(R.id.knight);
        Button bishop = (Button) prompt.findViewById(R.id.bishop);
        Button queen = (Button) prompt.findViewById(R.id.queen);
        Button rook = (Button) prompt.findViewById(R.id.rook);
        alert.setTitle("Pawn Promotion");
        alert.setCancelable(false);

        final boolean color;
        if(chessBoard.counter % 2 == 0){
            color=true;
        }else{color=false;}

        final AlertDialog promoPrompt = alert.create();
        knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessBoard.board[ti][tj] = new ChessPieceKnight(color);
                movesList.remove(movesList.size()-1);
                movesList.add(new Moves(ci,cj,ti,tj, chessBoard.counter));
                movesList.get(movesList.size()-1).promo = true;
                movesList.get(movesList.size()-1).promoPiece=1;

                checkForEndgame();
                input = 0;
                adaptor.notifyDataSetChanged();
                boardGridView.invalidateViews();
                boardGridView.setAdapter(adaptor);
                promoPrompt.dismiss();
            }
        });
        bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessBoard.board[ti][tj] = new ChessPieceBishop(color);
                movesList.remove(movesList.size()-1);
                movesList.add(new Moves(ci,cj,ti,tj, chessBoard.counter));
                movesList.get(movesList.size()-1).promo = true;
                movesList.get(movesList.size()-1).promoPiece=2;

                checkForEndgame();
                input = 0;
                adaptor.notifyDataSetChanged();
                boardGridView.invalidateViews();
                boardGridView.setAdapter(adaptor);
                promoPrompt.dismiss();
            }
        });
        queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessBoard.board[ti][tj] = new ChessPieceQueen(color);
                movesList.remove(movesList.size()-1);
                movesList.add(new Moves(ci,cj,ti,tj, chessBoard.counter));
                movesList.get(movesList.size()-1).promo = true;
                movesList.get(movesList.size()-1).promoPiece=4;

                checkForEndgame();
                input = 0;
                adaptor.notifyDataSetChanged();
                boardGridView.invalidateViews();
                boardGridView.setAdapter(adaptor);
                promoPrompt.dismiss();
            }
        });
        rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessBoard.board[tI][tJ] = new ChessPieceRook(color);
                movesList.remove(movesList.size()-1);
                movesList.add(new Moves(ci,cj,ti,tj, chessBoard.counter));
                movesList.get(movesList.size()-1).promo = true;
                movesList.get(movesList.size()-1).promoPiece=3;

                checkForEndgame();
                input = 0;
                adaptor.notifyDataSetChanged();
                boardGridView.invalidateViews();
                boardGridView.setAdapter(adaptor);
                promoPrompt.dismiss();
            }
        });

        promoPrompt.show();

    }

    public void checkForEndgame(){
        if(checkMate){
            String result = "";
            if (chess.chessBoard.counter % 2 == 0) {
                result = "White Wins";
            } else if (chess.chessBoard.counter % 2 != 0) {
                result = "Black Wins";
            }
            endGame(result);
        }
        if(stalemate){
            endGame("Stalemate");
        }
    }

    public void writeGamesToFile(){
        try{
            FileOutputStream fis = getApplicationContext().openFileOutput("DataBase.txt", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fis);
            os.writeObject(ReplayGame.gameList);
            os.close();
            fis.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}