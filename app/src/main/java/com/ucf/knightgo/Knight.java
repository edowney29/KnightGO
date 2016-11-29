package com.ucf.knightgo;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class Knight {

    /* Under the assumption knightList will be a freq. array,
    the value representing the quantity and index representing type
    (Ex: knightList[1] = 3; means the player has three bowman)*/
    public Knight(int type) {//TODO: Update stats. 10 hp, 4 damage is baseline (SWORD). Also think about # of hits to kill.
        switch(type)
        {
            case 0://SHIELD
            {
                this.type = 0;
                this.name = "Shield";
                this.health =   20;
                this.damage =   1;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_shield;
                this.bigIcon = R.drawable.shield;
                break;
            }

            case 1://BOW
            {
                this.type = 1;
                this.name = "Bow";
                this.health =   4;
                this.damage =   3;
                this.range =    3;
                this.movement = 1;
                this.mapIcon = R.drawable.map_longbow;
                this.bigIcon = R.drawable.longbow;
                break;
            }

            case 2://CROSSBOW
            {
                this.type = 2;
                this.name = "Crossbow";
                this.health =   8;
                this.damage =   2;
                this.range =    2;
                this.movement = 1;
                this.mapIcon = R.drawable.map_crossbow;
                this.bigIcon = R.drawable.crossbow;
                break;
            }

            case 3://SPEAR
            {
                this.type = 3;
                this.name = "Spear";
                this.health =   8;
                this.damage =   4;
                this.range =    2;
                this.movement = 1;
                this.mapIcon = R.drawable.map_spear;
                this.bigIcon = R.drawable.spear;
                break;
            }

            case 4://SWORD
            {
                this.type = 4;
                this.name = "Sword";
                this.health =   10;
                this.damage =   5;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_sword;
                this.bigIcon = R.drawable.sword;
                break;
            }

            case 5://AXE
            {
                this.type = 5;
                this.name = "Axe";
                this.health =   10;
                this.damage =   4;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_axe;
                this.bigIcon = R.drawable.axe;
                break;
            }

            case 6://MACE
            {
                this.type = 6;
                this.name = "Mace";
                this.health =   10;
                this.damage =   4;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_mace;
                this.bigIcon = R.drawable.mace;
                break;
            }

            case 7://DAGGER
            {
                this.type = 7;
                this.name = "Dagger";
                this.health =   4;
                this.damage =   8;
                this.range =    1;
                this.movement = 2;
                this.mapIcon = R.drawable.map_dagger;
                this.bigIcon = R.drawable.dagger;
                break;
            }

            case 8://HALBERD
            {
                this.type = 8;
                this.name = "Halberd";
                this.health =   16;
                this.damage =   2;
                this.range =    1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_halberd;
                this.bigIcon = R.drawable.halberd;
                break;
            }

            case 9://PEGASUS
            {
                this.type = 9;
                this.name = "Pegasus";
                this.health =   8;
                this.damage =   8;
                this.range =    1;
                this.movement = 3;
                this.mapIcon = R.drawable.map_pegasus;
                this.bigIcon = R.drawable.pegasus;
                break;
            }

            default: // Should never occur...
            {
                this.name = "MISSINGNO.";
                this.health = 1;
                this.damage = 1;
                this.range = 1;
                this.movement = 1;
                this.mapIcon = R.drawable.map_sword;
                this.bigIcon = R.drawable.sword;
                break;
            }
        }
    }

    // Extra constructor for a custom Knight
    Knight(String name, int health, int damage, int range, int movement, LatLng location) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.movement = movement;
        this.location = location;
    }

    private int type;
	private int health;
	private int damage;
	private int range;
	private int movement;
    private int mapIcon;
    private int bigIcon;
    private String name;

    private boolean isEnemy;
    private int row;
    private int col;

    private double latitude;
    private double longitude;
    public LatLng location = new LatLng(latitude,longitude);

	// Getters
    public int getType(){return type;}
	public int getHealth(){return health;}
	public int getDamage(){return damage;}
	public int getRange(){return range;}
	public int getMovement(){return movement;}
    public int getMapIcon(){return mapIcon;}
    public int getBigIcon(){return bigIcon;}
    public boolean isEnemy(){return isEnemy;}
	public int getRow(){return row;}
    public int getCol(){return col;}
    public double getLatitude(){return latitude;}
    public double getLongitude(){return longitude;}
    public String getName(){return name;}
    public LatLng getLocation(){return location;}

    // Setters - setting location and enemy status
    public void setRow(int row){this.row = row;}
    public void setCol(int col){this.col = col;}
    public void setIsEnemy(boolean status){this.isEnemy = status;}
    public void setLocation(LatLng x){this.location = x;}

    // Modifiers - for Decreasing health when needed and potential dmg mods
	private void modHealth(int modifier){this.health += modifier;}
	private void modDamage(int modifier){this.damage += modifier;}

    //TODO: Finish move
    //Moving - will move forward a number of spaces equal to movement until it hits another Knight
    public Knight[][] move(Knight[][] battlefield) {
        int moveSpace;
        Knight cellContents;

        // PEGASUS (jumps over enemies to furthest space within range of movement)
        if(this.type == 9) {
            // Pegasus belongs to the enemy player
            if(this.isEnemy) {
                // Check the furthest point within movement
                moveSpace = this.row + this.movement;

                // Reached the end of the board. Score!
                if(moveSpace >= battlefield.length) {
                    // The -1 row/col changes indicate a Knight has reached the other side.
                    // Handle scoring and removal of the Knight in SimulationActivity.
                    // SimulationActivity needs to check these values on every turn while it still
                    // has the Knight's actual position.
                    this.row = -1;
                    this.col = -1;
                    return battlefield;
                }

                // Check closer spaces until the Pegasus' current space is reached
                while(moveSpace > this.row) {
                    // Check the contents of the space to move to
                    cellContents = battlefield[moveSpace][this.col];

                    // The space is empty. Cleared for landing.
                    if(cellContents == null) {
                        battlefield[moveSpace][this.col] = this;
                        battlefield[this.row][this.col] = null;
                        this.row = moveSpace;

                        // Early return statement to break out of loop
                        return battlefield;
                    }

                    // Occupied space, check 1 space closer
                    moveSpace--;
                }
            }

            // Pegasus belongs to the player - logic and battlefield positions are reversed
            else {
                moveSpace = this.row - this.movement;

                if(moveSpace < 0) {
                    this.row = -1;
                    this.col = -1;
                    return battlefield;
                }

                while(moveSpace < this.row) {
                    cellContents = battlefield[moveSpace][this.col];

                    if(cellContents == null) {
                        battlefield[moveSpace][this.col] = this;
                        battlefield[this.row][this.col] = null;
                        this.row = moveSpace;

                        return battlefield;
                    }

                    moveSpace++;
                }
            }
        }

        // All other Knight types (move up to movement unless blocked by another Knight)
        else {
            // Knight belongs to the enemy player
            if(this.isEnemy) {
                moveSpace = this.row + 1;

                // Continue until the maximum movement is reached
                while(Math.abs(moveSpace - this.row) <= this.movement) {
                    // Knight moved out-of-bounds, deletion and scoring to be handled by Simulation
                    if(moveSpace >= battlefield.length) {
                        this.row = -1;
                        this.col = -1;
                        return battlefield;
                    }

                    cellContents = battlefield[moveSpace][this.col];

                    // Something in the way here, only move as far as the space before this
                    if(cellContents != null && moveSpace != this.row) {
                        battlefield[moveSpace-1][this.col] = this;
                        battlefield[this.row][this.col] = null;
                        this.row = moveSpace-1;
                    }

                    // Reached the end of our movement, nothing in the way
                    else if(Math.abs(moveSpace - this.row) == this.movement) {
                        battlefield[moveSpace][this.col] = this;
                        battlefield[this.row][this.col] = null;
                        this.row = moveSpace;
                    }

                    moveSpace++;
                }
            }

            // Knight belongs to the player - logic and battlefield positions are reversed
            else {
                moveSpace = this.row - 1;

                while(Math.abs(moveSpace - this.row) <= this.movement) {
                    if(moveSpace < 0) {
                        this.row = -1;
                        this.col = -1;
                        return battlefield;
                    }

                    cellContents = battlefield[moveSpace][this.col];

                    if(cellContents != null && moveSpace != this.row) {
                        battlefield[moveSpace+1][this.col] = this;
                        battlefield[this.row][this.col] = null;
                        this.row = moveSpace+1;
                    }

                    else if(Math.abs(moveSpace - this.row) == this.movement) {
                        battlefield[moveSpace][this.col] = this;
                        battlefield[this.row][this.col] = null;
                        this.row = moveSpace;
                    }

                    moveSpace--;
                }
            }
        }

        return battlefield;
    }

    //Attacking - will attack the nearest enemy according to individual rules
	public Knight[][] attack(Knight[][] battlefield) {
        int attackRow;
        Knight target;

        // BOW and CROSSBOW (attack closest enemy within range)
        if(this.type == 1 || this.type == 2) {
            // Knight belongs to enemy player
            if(this.isEnemy) {
                // Get the row directly in front of the Knight
                attackRow = this.row + 1;

                // Do not attack out-of-bounds or out-of-range targets
                while(attackRow < battlefield.length && Math.abs(attackRow - this.row) <= this.range) {
                    // Get the next potential target
                    target = battlefield[attackRow][this.col];

                    // Non-friendly Knight occupies this space. We got a live one!
                    if(!target.isEnemy()) {
                        // Attack the enemy knight
                        target.modHealth(-this.damage);

                        // Enemy health hit 0 or lower. He's dead, Jim.
                        if(target.getHealth() <= 0)
                            battlefield[attackRow][this.col] = null;

                        // Not enough to kill the Knight, it goes back on the grid. Still Alive.
                        else
                            battlefield[attackRow][this.col] = target;

                        // Early return here so only one enemy is attacked. The rest live. For now.
                        return battlefield;
                    }
                    attackRow++;
                }
            }

            // Knight belongs to player - logic and battlefield positions are reversed
            else {
                attackRow = this.row - 1;

                while(attackRow >= 0 && Math.abs(attackRow - this.row) <= this.range) {
                    target = battlefield[attackRow][this.col];

                    if(target.isEnemy()) {
                        target.modHealth(-this.damage);

                        if(target.getHealth() <= 0)
                            battlefield[attackRow][this.col] = null;
                        else
                            battlefield[attackRow][this.col] = target;

                        return battlefield;
                    }
                    attackRow--;
                }
            }
        }


        // SPEAR (range = 1 unless behind an ally, then range = 2)
        else if(this.type == 3) {
            // Spearman belongs to the enemy player
            if(this.isEnemy) {
                // Get the row to attack and check if it's in-bounds
                attackRow = this.row + (this.range - 1);
                if(attackRow < battlefield.length) {
                    // Get the contents of the space in front of the Spearman
                    target = battlefield[attackRow][this.col];

                    // Allied unit here, check next row
                    if (target != null && target.isEnemy()) {

                        attackRow = this.row + this.range;
                        if (attackRow < battlefield.length) {
                            target = battlefield[attackRow][this.col];

                            // Enemy Knight in front of ally. Sometimes, length DOES matter.
                            if (target != null && !target.isEnemy()) {
                                target.modHealth(-this.damage);

                                if (target.getHealth() <= 0) {
                                    battlefield[attackRow][this.col] = null;
                                }
                                else {
                                    battlefield[attackRow][this.col] = target;
                                }
                            }
                        }
                    }

                    // Non-friendly Knight directly in front. Enemy dead ahead, Cap'n!
                    else if(target != null) {
                        target.modHealth(-this.damage);

                        if (target.getHealth() <= 0) {
                            battlefield[attackRow][this.col] = null;
                        }
                        else {
                            battlefield[attackRow][this.col] = target;
                        }
                    }
                }
            }

            // Spearman belongs to the player - logic and battlefield positions are reversed
            else {
                attackRow = this.row - (this.range - 1);
                if(attackRow >= 0) {
                    target = battlefield[attackRow][this.col];

                    if (target != null && !target.isEnemy()) {

                        attackRow = this.row - this.range;
                        if (attackRow < battlefield.length) {
                            target = battlefield[attackRow][this.col];

                            if (target != null && target.isEnemy()) {
                                target.modHealth(-this.damage);

                                if (target.getHealth() <= 0) {
                                    battlefield[attackRow][this.col] = null;
                                }
                                else {
                                    battlefield[attackRow][this.col] = target;
                                }
                            }
                        }
                    }

                    else if(target != null) {
                        target.modHealth(-this.damage);

                        if (target.getHealth() <= 0) {
                            battlefield[attackRow][this.col] = null;
                        }
                        else {
                            battlefield[attackRow][this.col] = target;
                        }
                    }
                }
            }
        }


        // HALBERD (attack entire row directly in front)
        else if(this.type == 8) {
            // Halberdier belongs to the enemy player
            if(this.isEnemy) {
                attackRow = this.row + this.range;
                if(attackRow < battlefield.length) {

                    //Move across the columns in this row
                    for(int i=0; i < battlefield[0].length; i++) {
                        // Check each cell in the row
                        target = battlefield[attackRow][i];

                        // Found an enemy Knight
                        if(target != null && !target.isEnemy()) {
                            target.modHealth(-this.damage);

                            if (target.getHealth() <= 0) {
                                battlefield[attackRow][this.col] = null;
                            }
                            else {
                                battlefield[attackRow][this.col] = target;
                            }
                        }
                    }
                }
            }

            // Halberdier belongs to the player - logic and battlefield positions are reversed
            else {
                attackRow = this.row - this.range;
                if(attackRow >= 0) {

                    //Move across the columns in this row
                    for(int i=0; i < battlefield[0].length; i++) {
                        // Check each cell in the row
                        target = battlefield[attackRow][i];

                        // Found an enemy Knight
                        if(target != null && target.isEnemy()) {
                            target.modHealth(-this.damage);

                            if (target.getHealth() <= 0) {
                                battlefield[attackRow][this.col] = null;
                            }
                            else {
                                battlefield[attackRow][this.col] = target;
                            }
                        }
                    }
                }
            }
        }


        // SHIELD, SWORD, AXE, MACE, DAGGER, and PEGASUS (attack enemy directly in front)
        else {
            // Knight belongs to the enemy player
            if(this.isEnemy) {
                attackRow = this.row + this.range;
                if(attackRow < battlefield.length) {

                    target = battlefield[attackRow][this.col];

                    if (target != null && !target.isEnemy()) {

                        // AXE vs. SHIELD or MACE vs. HALBERD
                        if((this.type == 5 && target.getType() == 0)
                                || (this.type == 6 && target.getType() == 8)) {
                            target.modHealth(-(this.damage + 2));
                        }
                        // Any other combination
                        else {
                            target.modHealth(-this.damage);
                        }

                        if (target.getHealth() <= 0) {
                            battlefield[attackRow][this.col] = null;
                        }
                        else {
                            battlefield[attackRow][this.col] = target;
                        }
                    }
                }
            }

            // Knight belongs to the player - logic and battlefield positions are reversed
            else {
                attackRow = this.row - this.range;
                if(attackRow >= 0) {

                    target = battlefield[attackRow][this.col];

                    if (target != null && target.isEnemy()) {

                        // AXE vs. SHIELD or MACE vs. HALBERD
                        if((this.type == 5 && target.getType() == 0)
                                || (this.type == 6 && target.getType() == 8)) {
                            target.modHealth(-(this.damage + 2));
                        }
                        // Any other combination
                        else {
                            target.modHealth(-this.damage);
                        }

                        if (target.getHealth() <= 0) {
                            battlefield[attackRow][this.col] = null;
                        }
                        else {
                            battlefield[attackRow][this.col] = target;
                        }
                    }
                }
            }
        }

        return battlefield;
    }

    public Knight[][] turn(Knight[][] battlefield) {
        // BOW and CROSSBOW (attack first, then move)
        if(this.type == 1 || this.type == 2) {
            battlefield = this.attack(battlefield);
            battlefield = this.move(battlefield);
        }

        // All other Knights (move first, then attack)
        else {
            battlefield = this.move(battlefield);
            battlefield = this.attack(battlefield);
        }

        return battlefield;
    }

    //location set - sets the latitude and longitude for google maps
    public void setMapLocation() {

        double minLong = -81.1960;
        double maxLong = -81.2040;
        double minLat = 28.59900;
        double maxLat = 28.60500;

        Random rand = new Random();
        double latitude = minLat + (rand.nextDouble()*(maxLat-minLat));
        double longitude = minLong + (rand.nextDouble()*(maxLong-minLong));

        this.latitude = latitude;
        this.longitude = longitude;
    }
}