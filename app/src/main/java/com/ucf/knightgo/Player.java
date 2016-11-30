package com.ucf.knightgo;

public class Player {
    private static Player mInstance = null;

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
