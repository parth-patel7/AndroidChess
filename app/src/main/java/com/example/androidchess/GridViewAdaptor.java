package com.example.androidchess;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.util.Log;

import chess.chessBoard;
import chessPiece.ChessPieceBishop;
import chessPiece.ChessPieceKing;
import chessPiece.ChessPieceKnight;
import chessPiece.ChessPiecePawn;
import chessPiece.ChessPieceQueen;
import chessPiece.ChessPieceRook;


public class GridViewAdaptor extends BaseAdapter {

    private Context ctx;


    public GridViewAdaptor(Context c){
        this.ctx = c;
    }

    @Override
    public int getCount() {
        return 64;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(convertView == null) {
            imageView = new ImageView(ctx);
            imageView.setLayoutParams(new GridView.LayoutParams(90 ,92 ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(getImage(position));
        return imageView;
    }

    public int getImage(int position) {
        //chessPiece.ChessPiece piece = chessBoard.board[position / 8][position % 8];

        if(chessBoard.board[position / 8][position % 8] == null){
            return R.drawable.empty;
        }

        if(chessBoard.board[position / 8][position % 8].isBlack && chessBoard.board[position / 8][position % 8] != null){
            if(chessBoard.board[position / 8][position % 8] instanceof ChessPiecePawn){
                return R.drawable.blackpawn;
            }
            if(chessBoard.board[position / 8][position % 8] instanceof ChessPieceKing){
                return R.drawable.blackking;
            }
            if(chessBoard.board[position / 8][position % 8] instanceof ChessPieceQueen){
                return R.drawable.blackqueen;
            }
            if(chessBoard.board[position / 8][position % 8] instanceof ChessPieceRook){
                return R.drawable.blackrook;
            }
            if(chessBoard.board[position / 8][position % 8] instanceof ChessPieceKnight){
                return R.drawable.blackknight;
            }
            if(chessBoard.board[position / 8][position % 8] instanceof ChessPieceBishop){
                return R.drawable.blackbishop;
            }

        } else if(!chessBoard.board[position / 8][position % 8].isBlack && chessBoard.board[position / 8][position % 8] != null){
            if (chessBoard.board[position / 8][position % 8] instanceof ChessPiecePawn) {
                return R.drawable.whitepawn;
            }
            if (chessBoard.board[position / 8][position % 8] instanceof ChessPieceKing) {
                return R.drawable.whiteking;
            }
            if (chessBoard.board[position / 8][position % 8] instanceof ChessPieceQueen) {
                return R.drawable.whitequeen;
            }
            if (chessBoard.board[position / 8][position % 8] instanceof ChessPieceRook) {
                return R.drawable.whiterook;
            }
            if (chessBoard.board[position / 8][position % 8] instanceof ChessPieceKnight) {
                return R.drawable.whiteknight;
            }
            if (chessBoard.board[position / 8][position % 8] instanceof ChessPieceBishop) {
                return R.drawable.whitebishop;
            }
        }
        return R.drawable.empty;
    }

}
