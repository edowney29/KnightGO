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
        info.setText("Learn how to play Knight GO by selecting the menu on the top-right of the screen.\n" +
                "Here you can learn how to build your army, battle other players, and find out more about each type of Knight.");
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
                        "You can view your inventory in the bottom left of the formation screen.\n" +
                        "You can place any type of knight (other than the Pegasus) as many times as you want as long as there's enough in your inventory.\n" +
                        "After placing your formation on the battlefield, click Okay." +
                        "Once the two players are ready, the battle will commence.\n\n" +
                        "The goal is to gain more points than your opponent. Points are earned by invading the enemy.\n" +
                        "When one of your Knights crosses the battlefield and into the enemy's side, you gain 1 point*.\n" +
                        "Will you take a defensive approach and keep the enemy at bay, or will you overwhelm them with a ferocious charge?\n\n" +
                        "GOOD LUCK AND HAVE FUN!");
                knights.setImageResource(0);
                battlefield.setImageResource(R.drawable.battlefield);

                break;
            case R.id.knightGuide:
                title.setText("Knight Guide");
                info.setText("Here are descriptions of each type of Knight in Knight GO.\nNote: The rating " +
                        "values are not the exact numbers.\n\nSword\nHealth: 4/5\nDamage: 4/5\nStyle: Melee\nSpecial: None\n" +
                        "The standard fighter. Can deal and take a decent amount of damage\n\n" +
                        "Spear\nHealth: 3/5\nDamage: 3/5\nStyle: Melee\nSpecial: Can attack enemies while standing behind an ally\n" +
                        "A formidable Knight. Sacrifices some damage for utility.\n\n" +
                        "Bow\nHealth:2/5\nDamage:3/5\nStyle: Ranged\nSpecial:None\n" +
                        "Assaults enemies from afar. Try to keep them away from the frontline.\n\n" +
                        "Mace\nHealth: 4/5\nDamage: 3/5\nStyle: Melee\nSpecial: Deals increased damage towards Halberdiers.\n" +
                        "A brutal fighter. Great for pushing down the battlefield\n\n" +
                        "Halberd\nHealth: 4/5\nDamage: 2/5\nStyle: Melee\nSpecial: Cleaves all enemies in an arc in front of him.\n" +
                        "A devastating Knight that can deal damage to multiple enemies.\n\n" +
                        "Axe\nHealth: 4/5\nDamage: 3/5\nStyle: Melee\nSpecial: Deals increased damage towards Shield Knights.\n" +
                        "Cuts through defenses. Great frontline Knights.\n\n" +
                        "Dagger\nHealth:1/5\nDamage: 5/5\nStyle: Melee\nSpecial: Can move 2 spaces in 1 turn.\n" +
                        "Fast and deadly. Weak towards tanky enemies\n\n" +
                        "Crossbow\nHealth:3/5\nDamage: 2/5\nStyle: Ranged\nSpecial:None\n" +
                        "Ranged fighter. Tougher than Bowman but deals less damage\n\n" +
                        "Shield\nHealth: 5/5\nDamage: 1/5\nRange: Melee\nSpecial:None\n" +
                        "The best defensive Knight. Great for defending weaker knights.\n\n" +
                        "Pegasus\nHealth: 4/5\nDamage:4/5\nRange:Melee\nSpecial: Can fly over enemies and gives 2 points when invading enemy.\n" +
                        "The ultimate Knight. Only one can be deployed per formation.");
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
