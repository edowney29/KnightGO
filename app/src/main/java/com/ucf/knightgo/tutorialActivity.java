package com.ucf.knightgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {
    private TextView title;
    private TextView info;
    private ImageView battlefield;
    private ImageView knights;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Intent intent = getIntent();

        title = (TextView) findViewById(R.id.tvTittle);
        info = (TextView) findViewById(R.id.tvInfo);
        battlefield =  (ImageView) findViewById(R.id.imgBattle);
        knights = (ImageView) findViewById(R.id.imgKnights);

        title.setText("Welcome to Knight GO! ");
        info.setText("Press on the menu and learn how to play!");
        info.setMovementMethod(new ScrollingMovementMethod());
        battlefield.setImageResource(0);
        knights.setImageResource(0);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tutorial_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.mnuMap:
                title.setText("How to Form Your Army");
                info.setText("In order to build your army, you must explore the UCF campus to collect Knights.\n\n" +
                        "First, click on the Explore button in the Main Menu.\n" +
                        "A map of the University of Central Florida's campus will open, showing the Knights on the map.\n\n" +
                        "To collect a Knight, approach a marker until it is within range indicated by the red circle around your location.\n" +
                        "Once you are close enough, tap on the Knight's marker to enter Collection mode.\n\n" +
                        "In Collection mode, you must use your device's camera to locate the Knight.\n" +
                        "Once the Knight's seal has appeared on your camera, tap it to add that Knight to your inventory.\n" +
                        "You are then returned to the map where you can continue collecting more Knights.\n\n" +
                        "New Knights appear on campus every hour, so keep checking to add more to your army.");
                battlefield.setImageResource(0);
                knights.setImageResource(R.drawable.knights);
                break;
            case R.id.mnuBattle:
                title.setText("How to Battle");
                info.setText("In order to fight against other players, click on the Battle button on the Main Menu.\n" +
                        "Then you will be able to either Host a Battleground or Search for one.\n" +
                        "Once both players have joined a battleground, each player must select their own strategy and formation of their collected knights\n\n" +
                        "Each knight has their own unique fighting pattern, so try out different strategies!\n\n" +
                        "You will be able to view your inventory in the bottom left of the formation screen.\n" +
                        "Users can place any type of knight as many times as they want as long as they have enough in their inventory\n" +
                        //"except for Pegasus, which can be used only once per battle\n" +
                        "After placing your formation on the battlefield, click Okay." +
                        "Once the two players are ready, the battle will commence.\n\n" +
                        "GOOD LUCK AND HAVE FUN!");
                knights.setImageResource(0);
                battlefield.setImageResource(R.drawable.battlefield);

                break;
            case R.id.knightGuide:
                title.setText("Knight Guide");
                info.setText("Here are descriptions of each type of Knight in Knight GO.\nNote: The rating " +
                        "values are not the exact numbers.\n\nSword\nHealth: 4/5\nDamage: 4/5\nStyle: Melee\nSpecial: None\n" +
                        "The standard fighter. Can deal and take a decent amount of damage\n\n" +
                        "Spear\nHealth: 3/5\nDamage: 3/5\nStyle:Melee\nSpecial: Can attack enemies while standing behind an ally\n" +
                        "A formidable Knight. Sacrifices some damage for utility.\n\n" +
                        "Bow\nHealth:2/5\nDamage:3/5\nStyle:Ranged\nSpecial:None\n" +
                        "Assaults enemies from afar. Try to keep them away from the frontline.\n\n" +
                        "Mace\nHealth: 4/5\nDamage: 3/5\nStyle:Melee\nSpecial: Deals increased damage towards Halberdiers.\n" +
                        "A brutal fighter. Great for pushing down the battlefield\n\n" +
                        "Halberd\nHealth: 4/5\nDamage: 2/5\nStyle:Melee\nSpecial: Cleaves all enemies in an arc in front of him.\n" +
                        "A devastating Knight that can deal damage to multiple enemies.\n\n" +
                        "Axe\nHealth: 4/5\nDamage:Shield\nHealth: High\nDamage: Low\nRange: Melee\n");
                battlefield.setImageResource(0);
                knights.setImageResource(R.drawable.knights);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

    public void GoBackToMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}
