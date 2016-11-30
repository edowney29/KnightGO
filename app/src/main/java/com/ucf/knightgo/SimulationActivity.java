package com.ucf.knightgo;

/*
 * Created by KShoults on 11/27/2016.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SimulationActivity extends AppCompatActivity {
    //public static final String SIMULATION = "com.ucf.knightgo.Simulation";
    private int playerScore;
    private int enemyScore;
    private int[] playerFormation;
    private int[] enemyFormation;
    private int connectionType;
    private Knight[][] battlefield;
    private String result;
    private final String[] columnLabels = {"A", "B", "C"};

    // These arrays are ordered according to the Knights' turns in the simulation.
    // They hold the Knights' coordinates in the battlefield matrix, or -1,-1 if the Knight scored.
    // Very important for knowing when each Knight should move since once they're out of initial
    // position, we no longer know the turn order.
    private int[] knightsRow;
    private int[] knightsCol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        playerScore = 0;
        enemyScore = 0;
        this.battlefield = new Knight[9][3];
        knightsRow = new int[18];
        knightsCol = new int[18];

        Intent intent = getIntent();
        playerFormation = intent.getIntArrayExtra(FormationActivity.PLAYER_FORMATION);
        enemyFormation = intent.getIntArrayExtra(FormationActivity.ENEMY_FORMATION);
        connectionType = intent.getIntExtra(BluetoothActivity.CONNECTION_TYPE, -1);

        initBattlefield();
        simulateBattle();
        battleResult();
    }

    /** Sets up the battlefield with the local player's Knights at the bottom and the enemy's
        Knights at the top */
    private void initBattlefield() {
        int k = 0;

        for(int i=0; i < this.battlefield[0].length; i++) {
            for(int j=0; j < this.battlefield[0].length; j++) {
                // Fills in the player's knights left to right, top to bottom starting at [6][0]
                this.battlefield[i+6][j] = new Knight(playerFormation[k]);
                this.battlefield[i+6][j].setIsEnemy(false);
                this.battlefield[i+6][j].setRow(i+6);
                this.battlefield[i+6][j].setCol(j);

                // Fills in the enemy's Knights right to left, bottom to top starting at [2][2]
                this.battlefield[2-i][2-j] = new Knight(enemyFormation[k]);
                this.battlefield[2-i][2-j].setIsEnemy(true);
                this.battlefield[2-i][2-j].setRow(2-i);
                this.battlefield[2-i][2-j].setCol(2-j);

                // Fills in knight positions in the knightsRow and knightsCol arrays
                if(connectionType == 1) {
                    // Player is the client in the Bluetooth connection, player goes first
                    knightsRow[k] = this.battlefield[i+6][j].getRow();
                    knightsRow[k+9] = this.battlefield[2-i][2-j].getRow();
                    knightsCol[k] = this.battlefield[i+6][j].getCol();
                    knightsCol[k+9] = this.battlefield[2-i][2-j].getCol();
                }
                else {
                    // Player is the server in the Bluetooth connection, enemy goes first
                    knightsRow[k+9] = this.battlefield[i+6][j].getRow();
                    knightsRow[k] = this.battlefield[2-i][2-j].getRow();
                    knightsCol[k+9] = this.battlefield[i+6][j].getCol();
                    knightsCol[k] = this.battlefield[2-i][2-j].getCol();
                }

                // Advance the arrays from the formation activity
                k++;
            }
        }
    }

    /** Uses the knightsRow array to determine if the battlefield is empty */
    private boolean gridIsEmpty() {
        for(int i=0; i < knightsRow.length; i++) {
            // Still a Knight here
            if(knightsRow[i] >= 0 && knightsCol[i] >= 0) {
                return false;
            }
        }

        return true;
    }

     /** Runs the battle according to the rules, assigns the final score values */
    private void simulateBattle() {
        int i = 0, r, c;
        Knight currentKnight;

        // Continue as long as the battlefield contains Knights
        while(!gridIsEmpty()) {
            r = knightsRow[i];
            c = knightsCol[i];

            if(r >= 0 && c >= 0) {
                // Get the Knight whose turn it is and run its turn
                currentKnight = this.battlefield[r][c];
                this.battlefield = currentKnight.turn(this.battlefield);

                // Check for Knights that reached the other end of the battlefield
                if (currentKnight.getRow() < 0 && currentKnight.getCol() < 0) {

                    // The Knight belonged to the enemy
                    if (currentKnight.isEnemy()) {
                        // A Pegasus is worth 2 points
                        if (currentKnight.getType() == 9) {
                            enemyScore += 2;
                        }
                        // All other Knights are worth 1 point
                        else {
                            enemyScore++;
                        }
                    }

                    // The Knight belonged to the player
                    else {
                        if (currentKnight.getType() == 9) {
                            playerScore += 2;
                        } else {
                            playerScore++;
                        }
                    }

                    // Remove the Knight from the battlefield and knightRows/knightCols
                    this.battlefield[r][c] = null;
                    knightsRow[i] = -1;
                    knightsCol[i] = -1;
                }
                else {
                    knightsRow[i] = currentKnight.getRow();
                    knightsCol[i] = currentKnight.getCol();
                }
            }

            i++;

            // Go back to the first Knight's turn
            if (i >= knightsRow.length) {
                i = 0;
            }
        }
    }

    /** Assigns a result based on the final scores of both players */
    private void battleResult() {
        if(playerScore > enemyScore)
            result = "Victory!";
        else if(enemyScore > playerScore)
            result = "Defeat!";
        else
            result = "Draw!";

        // Display result
    }

    /** Returns the player to the main menu */
    public void goToMainMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
