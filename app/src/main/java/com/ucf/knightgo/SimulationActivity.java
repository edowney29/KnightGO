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
    private Knight[][] grid;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        playerScore = 0;
        enemyScore = 0;
        playerFormation = new int[9];
        enemyFormation = new int[9];
        grid = new Knight[9][3];

        Intent intent = getIntent();
        combinedFormations = intent.getIntArrayExtra(FormationActivity.FORMATION);
        for(int i=0; i < 9; i++) {
            playerFormation[i] = combinedFormations[i];
            enemyFormation[i] = combinedFormations[i+9];
        }
        connectionType = combinedFormations[18];
    }

    private void battleResult() {
        if(playerScore > enemyScore)
            result = "Victory!";
        else if(enemyScore > playerScore)
            result = "Defeat!";
        else
            result = "Draw!";
    }

    public void goToMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
