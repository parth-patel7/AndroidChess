package com.example.androidchess;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Game implements Serializable {
    public ArrayList<Moves> movesList;
    public String name;
    public Date date;

    public Game(ArrayList<Moves> movesList, String name){
        this.movesList=movesList;
        this.name=name;
        this.date = new Date();
    }

    public Date getDate(){ return this.date; }
    public String getName(){ return this.name; }

    @Override
    public String toString(){
        DateFormat dateFmt = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat timeFmt = new SimpleDateFormat("hh:mm:ss a");
        String s = ("\"" + this.name + "\"" + "\nDate: " + dateFmt.format(this.date)
                + "\nTime: " + timeFmt.format(this.date));
        return (s);
    }
}
