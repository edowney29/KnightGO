package com.ucf.knightgo;

/**
 * Created by KShoults on 11/27/2016.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SimulationActivity extends AppCompatActivity {

    private int playerScore;
    private int enemyScore;
    private int[] playerFormation;
    private int[] enemyFormation;
    private int[] combinedFormations;
    private int connectionType;
    private Knight[][] battlefield;
    private String result;

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
        combinedFormations = intent.getIntArrayExtra(FormationActivity.FORMATION);
        for(int i=0; i < 9; i++) {
            playerFormation[i] = combinedFormations[i];
            enemyFormation[i] = combinedFormations[i+9];
        }
        connectionType = combinedFormations[18];

        initBattlefield();
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
