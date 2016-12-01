package com.ucf.knightgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static java.security.AccessController.getContext;

public class Player {
    private static Player mInstance;

    public int[] Inventory;

    private Player(){
        Inventory = new int[10];
    }

    public static Player getInstance(){
        if(mInstance == null) {
            mInstance = new Player();
        }
        return mInstance;
    }
    public void setInventory(int [] array) {
        for(int i = 0; i< array.length; i++) {
            this.Inventory[i] = array[i];
        }
    }
    public void loadInventory(Context context)
    {
        //load the player's saved knights
        SharedPreferences preferenceSettings;
        SharedPreferences.Editor preferenceEditor;

        preferenceSettings = context.getSharedPreferences("prefs", context.MODE_PRIVATE);
        Inventory[0] = preferenceSettings.getInt("0",6);
        Inventory[1] = preferenceSettings.getInt("1",6);
        Inventory[2] = preferenceSettings.getInt("2",6);
        Inventory[3] = preferenceSettings.getInt("3",6);
        Inventory[4] = preferenceSettings.getInt("4",6);
        Inventory[5] = preferenceSettings.getInt("5",6);
        Inventory[6] = preferenceSettings.getInt("6",6);
        Inventory[7] = preferenceSettings.getInt("7",6);
        Inventory[8] = preferenceSettings.getInt("8",6);
        Inventory[9] = preferenceSettings.getInt("9",6);
    }

    public void saveInventory(Context context)
    {
        //load the player's saved knights
        SharedPreferences preferenceSettings;
        SharedPreferences.Editor preferenceEditor;

        preferenceSettings = context.getSharedPreferences("prefs", context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        preferenceEditor.putInt("0",Inventory[0]);
        preferenceEditor.putInt("1",Inventory[1]);
        preferenceEditor.putInt("2",Inventory[2]);
        preferenceEditor.putInt("3",Inventory[3]);
        preferenceEditor.putInt("4",Inventory[4]);
        preferenceEditor.putInt("5",Inventory[5]);
        preferenceEditor.putInt("6",Inventory[6]);
        preferenceEditor.putInt("7",Inventory[7]);
        preferenceEditor.putInt("8",Inventory[8]);
        preferenceEditor.putInt("9",Inventory[9]);
        preferenceEditor.apply();
    }
    public int[] getInventory() {
        return this.Inventory;
    }

    public void addKnight(int type) {
        Inventory[type]++;
    }

    public void removeKnight(int type) {
        Inventory[type]--;
    }
}
