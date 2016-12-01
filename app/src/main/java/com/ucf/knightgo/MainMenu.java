package com.ucf.knightgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Player.getInstance().loadInventory(this);
    }

    public void openMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void openTutorial(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }

    public void openBattle(View view) {
        Intent intent = new Intent(this, BluetoothActivity.class);
        startActivity(intent);
    }
}
