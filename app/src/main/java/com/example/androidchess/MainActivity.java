package com.example.androidchess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView;
import android.view.View;
import android.util.Log;
import android.content.Intent;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import chess.chessBoard;

public class MainActivity extends AppCompatActivity {

    Button PlayChessButton;
    Button ReplayChessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        chess.chessBoard.resetGame(); // might remove this
        //PlayGame.moves.clear();
        getGames();
        playChessPressed ();
        replayPressed();
    }

    public void playChessPressed (){
        PlayChessButton = findViewById(R.id.PlayChessButton);

        PlayChessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PlayGame.class));
            }
        });
    }

    public void replayPressed(){
        ReplayChessButton = findViewById(R.id.ReplayGamesButton);

        ReplayChessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReplayGame.class));
            }
        });
    }

    public void getGames(){
        try{
            FileInputStream fis = getApplicationContext().openFileInput("DataBase.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            try{
                ReplayGame.gameList = (ArrayList<Game>) ois.readObject();
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (EOFException e) {}

            fis.close();
            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}