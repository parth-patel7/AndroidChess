package com.example.androidchess;

import android.content.Context;
import android.os.Environment;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
    /**
     * @author joshherrera
     * @author parthpatel
     */
    public class StoreData implements Serializable{

        private static final long serialVersionUID = 3727994457398773678L;

        /**
         * Writes users and user related objects to file
         */
        public static void writeGamesToFile() {

            File f = new File(Environment.getExternalStorageDirectory(), "DataBase.txt");
            System.out.println(f.getAbsolutePath());

           /* try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PlayGame.filePath));
                os.writeObject(ReplayGame.gameList);
                os.close();



            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }

        /**
         * Loads users and user related objects to file
         */
        public static void getGames() {
            System.out.println("here");
            try {
                File f = new File("DataBase.txt");
                if (f.length() != 0) {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PlayGame.filePath));

                    try {
                        if (new File(PlayGame.filePath).length()==0){
                            System.out.println("HERRRRRRRRRRRRRRR");
                        }
                        ReplayGame.gameList = (ArrayList<Game>) ois.readObject();
                    } catch (ClassNotFoundException e) { e.printStackTrace();
                    } catch (EOFException e) { }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

