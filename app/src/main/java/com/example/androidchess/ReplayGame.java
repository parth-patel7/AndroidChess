package com.example.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

public class ReplayGame extends AppCompatActivity {

    public static ListView replayList;
    private ArrayAdapter<Game> adapter;
    public static ArrayList<Game> gameList = new ArrayList<Game>();
    public static Game selected;

    Button name;
    Button date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay_game);
        replayList = (ListView) findViewById(R.id.replayList);

        adapter = new ArrayAdapter<Game>(getApplicationContext(), android.R.layout.simple_list_item_1, gameList);

        replayList.setAdapter(adapter);
        replayList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (Game) replayList.getItemAtPosition(position);
                // the selected item
                Log.d("MYINT", "Item Pressed " + replayList.getItemAtPosition(position));
                startActivity(new Intent(ReplayGame.this, ReplayGameHelper.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReplayGame.this, MainActivity.class));
        finish();
    }

    public void namePressed(View view){
        Collections.sort(gameList, new SortByName());
        adapter.notifyDataSetChanged();
    }

    public void datePressed(View view){
        Collections.sort(gameList, new SortByDate());
        adapter.notifyDataSetChanged();
    }

    private class SortByName implements Comparator<Game>{
        @Override
        public int compare(Game g1, Game g2){
            return g1.getName().compareTo(g2.getName());
        }
    }

    private class SortByDate implements Comparator<Game>{
        @Override
        public int compare(Game g1, Game g2){
            return g1.getDate().compareTo(g2.getDate());
        }
    }
}
