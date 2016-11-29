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
    private int[] combinedFormations;
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
        playerFormation = new int[9];
        enemyFormation = new int[9];
        battlefield = new Knight[9][3];

        Intent intent = getIntent();
        //combinedFormations = intent.getIntArrayExtra(FormationActivity.FORMATION);
        for(int i=0; i < 9; i++) {
            playerFormation[i] = combinedFormations[i];
            enemyFormation[i] = combinedFormations[i+9];
        }
        connectionType = combinedFormations[18];

        initBattlefield();

        // This is for Kevin's testing purposes:
        // BAAAAAAAD DO NOT DO THIS OMG WHAT THE HELL STOP NO GET RID OF IT BEFORE RELEASE VERSION
        /*for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                Player.getInstance().addKnight(j);
            }
        }*/
    }

    /** Sets up the battlefield with the local player's Knights at the bottom and the enemy's
     * Knights at the top */
    private void initBattlefield() {
        int k = 0;

        for(int i=0; i < 3; i++) {
            for(int j=0; j < 3; j++) {
                // Fills in the player's knights left to right, top to bottom starting at [6][0]
                battlefield[i+6][j] = new Knight(playerFormation[k]);
                battlefield[i+6][j].setIsEnemy(false);
                battlefield[i+6][j].setRow(i+6);
                battlefield[i+6][j].setCol(j);

                // Fills in the enemy's Knights right to left, bottom to top starting at [2][2]
                battlefield[2-i][2-j] = new Knight(enemyFormation[k]);
                battlefield[2-i][2-j].setIsEnemy(true);
                battlefield[2-i][2-j].setRow(2-i);
                battlefield[2-i][2-j].setCol(2-j);

                // Advance the arrays from the formation activity
                k++;
            }
        }
    }
     /** Runs the battle according to the rules, assigns the final score values */
    private void simulateBattle() {
        Knight currentKnight = new Knight(0);

        // Needs to be somewhere in here immediately after turn()
        // to check for Knights that reached the end
        if(currentKnight.getRow() < 0 && currentKnight.getCol() < 0) {

        }

    }

    /** Assigns a result based on the scores of both players */
    private void battleResult() {
        if(playerScore > enemyScore)
            result = "Victory!";
        else if(enemyScore > playerScore)
            result = "Defeat!";
        else
            result = "Draw!";
    }

    /** Returns the player to the main menu */
    public void goToMainMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
