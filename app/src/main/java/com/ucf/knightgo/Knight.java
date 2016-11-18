package com.ucf.knightgo;
import java.util.Random;

public class Knight
{
    /* Under the assumption knightList will be a freq. array,
    the value representing the quantity and index representing type
    (Ex: knightList[1] = 3; //means the player has three bowman)*/
    public Knight(int type)
    {
        switch(type)
        {
            case 0://SHIELD
            {
                this.health =   10;
                this.damage =   1;
                this.range =    1;
                this.movement = 1;
                break;
            }

            case 1://BOW
            {
                this.health =   1;
                this.damage =   3;
                this.range =    3;
                this.movement = 1;
                break;
            }

            case 2://CROSSBOW
            {
                this.health =   2;
                this.damage =   2;
                this.range =    2;
                this.movement = 2;
                break;
            }

            case 3://SPEAR
            {
                this.health =   4;
                this.damage =   3;
                this.range =    2;
                this.movement = 1;
                break;
            }

            case 4://SWORD
            {
                this.health =   3;
                this.damage =   2;
                this.range =    2;
                this.movement = 2;
                break;
            }

            case 5://AXE
            {
                this.health =   5;
                this.damage =   4;
                this.range =    1;
                this.movement = 1;
                break;
            }

            case 6://MACE
            {
                this.health =   6;
                this.damage =   3;
                this.range =    1;
                this.movement = 1;
                break;
            }

            case 7://DAGGER
            {
                this.health =   1;
                this.damage =   6;
                this.range =    1;
                this.movement = 2;
                break;
            }

            case 8://HALBERD
            {
                this.health =   7;
                this.damage =   4;
                this.range =    1;
                this.movement = 1;
                break;
            }

            case 9://PEGASUS
            {
                this.health =   5;
                this.damage =   5;
                this.range =    1;
                this.movement = 3;
                break;
            }

            default:
                this.health =   1;
                this.damage =   1;
                this.range =    1;
                this.movement = 1;
                break;
        }
    }

    // extra constructor
    Knight(int health, int damage, int range, int movement)
    {
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.movement = movement;
    }

	private int health;
	private int damage;
	private int range;
	private int movement;

    private boolean isEnemy;
    private int xLoc;
    private int yLoc;

    private double latitude;
    private double longitude;

	//getters
	private int getHealth(){return health;}
	private int getDamage(){return damage;}
	private int getRange(){return range;}
	private int getMovement(){return movement;}
    private boolean isEnemy(){return isEnemy;}
	private int getXLoc(){return xLoc;}
    private int getYLoc(){return yLoc;}
    private double getLatitude(){return latitude;}
    private double getLongitude(){return longitude;}

    //modifiers - for Decreasing health when needed and potential dmg mods
	private void modHealth(int modifier){this.health += modifier;}
	private void modDamage(int modifier){this.damage += modifier;}
	
    //moving - will move forward until hitting another unit(?)
    public Knight[][] move(Knight knight, Knight[][] board)
    {
        return null;
    }
    
    //attacking - special attacks switch with basic as default
	public void attack(Knight attacker, Knight[][] board)
	{}

    //location set - sets the latitude and longitude for google maps
    public void setMapLocation()
    {
        double minLong = -81.1935;
        double maxLong = -81.1938;
        double minLat = 28.5962;
        double maxLat = 28.60618;

        double rand = new Random().nextDouble();
        double latitude = minLat + (rand*(maxLat-minLat));
        rand = new Random().nextDouble();
        double longitude = minLat + (rand*(maxLat-minLat));

        this.latitude = latitude;
        this.longitude = longitude;
    }

}