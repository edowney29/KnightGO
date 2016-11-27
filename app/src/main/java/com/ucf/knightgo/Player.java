package com.ucf.knightgo;

public class Player {
    private static Player mInstance = null;

    private String mString;
    public int[] Inventory = new int[10];

    private Player(){
        mString = "Hello";
    }

    public static Player getInstance(){
        if(mInstance == null)
        {
            mInstance = new Player();
        }
        return mInstance;
    }

    public String getString(){
        return this.mString;
    }

    public void setString(String value){
        mString = value;
    }

    public int[] getInventory()
    {
        return this.Inventory;
    }

    public void addKnight(int type)
    {
        Inventory[type]++;
    }
}
