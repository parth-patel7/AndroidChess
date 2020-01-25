package com.example.androidchess;

import java.io.Serializable;

import chessPiece.ChessPiece;

// make move object which stores every move and the counter/flags at that time
public class Moves implements Serializable {
    // move
    int cI, cJ, tI, tJ, counter;
    public boolean promo;
    public int promoPiece;

    public Moves(int cI, int cJ, int tI, int tJ, int counter){
        this.cI=cI; this.cJ=cJ; this.tI=tI; this.tJ=tJ;
        this.promo=false;
        this.promoPiece=-1;
        this.counter=counter;
    }

    public int getCI(){ return this.cI; }
    public int getCJ(){ return this.cJ; }
    public int getTI(){ return this.tI; }
    public int getTJ(){ return this.tJ; }
    public int getCounter(){ return this.counter; }

}
