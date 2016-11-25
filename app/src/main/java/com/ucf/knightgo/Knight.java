package com.ucf.knightgo;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class Knight {

    /* Under the assumption knightList will be a freq. array,
    the value representing the quantity and index representing type
    (Ex: knightList[1] = 3; //means the player has three bowman)*/
    public Knight(int type)
    {
        switch(type)
        {
            case 0://SHIELD
            {
                this.name = "Shield";
                this.health =   10;
                this.damage =   1;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_shield;
                this.bigIcon = R.drawable.shield;
                break;
            }

            case 1://BOW
            {
                this.name = "Bow";
                this.health =   1;
                this.damage =   3;
                this.range =    3;
                this.movement = 1;
                this.mapIcon = R.drawable.map_longbow;
                this.bigIcon = R.drawable.longbow;
                break;
            }

            case 2://CROSSBOW
            {
                this.name = "Crossbow";
                this.health =   2;
                this.damage =   2;
                this.range =    2;
                this.movement = 2;
                this.mapIcon = R.drawable.map_crossbow;
                this.bigIcon = R.drawable.crossbow;
                break;
            }

            case 3://SPEAR
            {
                this.name = "Spear";
                this.health =   4;
                this.damage =   3;
                this.range =    2;
                this.movement = 1;
                this.mapIcon = R.drawable.map_spear;
                this.bigIcon = R.drawable.spear;
                break;
            }

            case 4://SWORD
            {
                this.name = "Sword";
                this.health =   3;
                this.damage =   2;
                this.range =    2;
                this.movement = 2;
                this.mapIcon = R.drawable.map_sword;
                this.bigIcon = R.drawable.sword;
                break;
            }

            case 5://AXE
            {
                this.name = "Axe";
                this.health =   5;
                this.damage =   4;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_axe;
                this.bigIcon = R.drawable.axe;
                break;
            }

            case 6://MACE
            {
                this.name = "Mace";
                this.health =   6;
                this.damage =   3;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_mace;
                this.bigIcon = R.drawable.mace;
                break;
            }

            case 7://DAGGER
            {
                this.name = "Dagger";
                this.health =   1;
                this.damage =   6;
                this.range =    1;
                this.movement = 2;
                this.mapIcon = R.drawable.map_dagger;
                this.bigIcon = R.drawable.dagger;
                break;
            }

            case 8://HALBERD
            {
                this.name = "Halberd";
                this.health =   7;
                this.damage =   4;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_halberd;
                this.bigIcon = R.drawable.halberd;
                break;
            }

            case 9://PEGASUS
            {
                this.name = "Pegasus";
                this.health =   5;
                this.damage =   5;
                this.range =    1;
                this.movement = 3;
                this.mapIcon = R.drawable.map_pegasus;
                this.bigIcon = R.drawable.pegasus;
                break;
            }

            default:
                this.name = "";
                this.health =   1;
                this.damage =   1;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_sword;
                this.bigIcon = R.drawable.sword;
                break;
        }
    }

    // extra constructor
    Knight(String name, int health, int damage, int range, int movement, LatLng location)
    {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.movement = movement;
        this.location = location;
    }

	private int health;
	private int damage;
	private int range;
	private int movement;
    private int mapIcon;
    private int bigIcon;
    private String name;


    private boolean isEnemy;
    private int xLoc;
    private int yLoc;

    private double latitude;
    private double longitude;
    public LatLng location = new LatLng(latitude,longitude);

	//getters
	public int getHealth(){return health;}
	public int getDamage(){return damage;}
	public int getRange(){return range;}
	public int getMovement(){return movement;}
    public int getMapIcon(){return mapIcon;}
    public int getBigIcon(){return bigIcon;}
    public boolean isEnemy(){return isEnemy;}
	public int getXLoc(){return xLoc;}
    public int getYLoc(){return yLoc;}
    public double getLatitude(){return latitude;}
    public double getLongitude(){return longitude;}
    public String getName(){return name;}
    public LatLng getLocation(){return location;}


    //setters - setting location and enemy status
    public void setXLoc(int x){this.xLoc = x;}
    public void setYLoc(int y){this .yLoc = y;}
    public void SetIsEnemy(boolean status){this.isEnemy = status;}
    public void setLocation(LatLng x){this.location = x;}

    //modifiers - for Decreasing health when needed and potential dmg mods
	private void modHealth(int modifier){this.health += modifier;}
	private void modDamage(int modifier){this.damage += modifier;}

    //TODO: Finish move
    //moving - will move forward until hitting another unit(?)
    public Knight[][] move(Knight knight, Knight[][] board)
    {
        return null;
    }

    //TODO: Finish attack
    //attacking - special attacks switch with basic as default
	public void attack(Knight attacker, Knight[][] board)
	{}

    //location set - sets the latitude and longitude for google maps
    public void setMapLocation()
    {
        double minLong = -81.1960;
        double maxLong = -81.2040;
        double minLat = 28.59900;
        double maxLat = 28.60500;

        double rand = new Random().nextDouble();
        double latitude = minLat + (rand*(maxLat-minLat));
        rand = new Random().nextDouble();
        double longitude = minLong + (rand*(maxLong-minLong));

        this.latitude = latitude;
        this.longitude = longitude;


    }



}