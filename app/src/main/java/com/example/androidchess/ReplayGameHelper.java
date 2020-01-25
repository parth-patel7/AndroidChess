package com.example.androidchess;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import chess.chessBoard;
import chessPiece.ChessPieceBishop;
import chessPiece.ChessPieceKnight;
import chessPiece.ChessPieceQueen;
import chessPiece.ChessPieceRook;

public class ReplayGameHelper extends AppCompatActivity {

    int moveIndex = 0;
    GridView replayBoardGridView;
    GridViewAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay_game_helper);
        chessBoard.loadGame();
        replayLoadBoardGUI();
    }

    public void nextButtonPressed(View view){
        if(moveIndex!=ReplayGame.selected.movesList.size()) {
            Moves move = ReplayGame.selected.movesList.get(moveIndex);

            chessBoard.playGame(move.getCI(), move.getCJ(), move.getTI(), move.getTJ());

            if(move.promo){
                boolean color;
                if(move.getCounter()%2==0){
                    color=true;
                }else{
                    color=false;
                }
                if(move.promoPiece==1){
                    chessBoard.board[move.getTI()][move.getTJ()] = new ChessPieceKnight(color);
                }
                if(move.promoPiece==2){
                    chessBoard.board[move.getTI()][move.getTJ()] = new ChessPieceBishop(color);
                }
                if(move.promoPiece==3){
                    chessBoard.board[move.getTI()][move.getTJ()] = new ChessPieceRook(color);
                }
                if(move.promoPiece==4){
                    chessBoard.board[move.getTI()][move.getTJ()] = new ChessPieceQueen(color);
                }
            }

            moveIndex++;
            adaptor.notifyDataSetChanged();
            replayBoardGridView.setAdapter(adaptor);
        }
        if(moveIndex==ReplayGame.selected.movesList.size())
        {
            AlertDialog.Builder endAlert = new AlertDialog.Builder(this);
            endAlert.setTitle("Done.");
            endAlert.setPositiveButton("Return", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    startActivity(new Intent(ReplayGameHelper.this, MainActivity.class));
                    finish();
                }
            });
            endAlert.show();
        }

    }

    public void replayLoadBoardGUI() {
        replayBoardGridView = (GridView) findViewById(R.id.replayBoardGridView);
        adaptor = new GridViewAdaptor(this);
        replayBoardGridView.setAdapter(adaptor);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReplayGameHelper.this, MainActivity.class));
        finish();
    }

}
