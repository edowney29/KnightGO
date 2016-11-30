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
                title.setText("How to Make my Army");
                info.setText("The first Step to make an army is picking up some knights\n" +
                        "In order to pick up Knights, user must go to Main Menu and click on Explore button.\n" +
                        "A map will open up focusing University of Central Florida Location, and player will be able to see all knights on the map.\n" +
                        "It is necessary to be near knight in order to pick it up." +
                        "Once near a knight, you can click it on the map.\n" +
                        "The camera will open up showing you the knight that that you are picking up,\n" +
                        "its necessary to click the knight in order to add it to your inventory.\n" +
                        "user will be returned to the map so can keep looking for more knights." +
                        "The knight collected will disappear from the map and you will be able to use it in your battles.");
                battlefield.setImageResource(0);
                knights.setImageResource(R.drawable.knights);
                break;
            case R.id.mnuBattle:
                title.setText("How to fight Battles");
                info.setText("In order to fight against other players, user must click on the Battle button on Main Menu\n" +
                        "once clicked on battle user will be able to either Host or Search for a battle\n" +
                        "once joined on a battle room, user must select his own strategy and positioning of the knights\n\n" +
                        "REMEMBER THAT THE POSITIONING OF THE KNIGHTS MATTER IN ORDER TO WIN!\n\n" +
                        "Users will be able to look at their inventory in the bottom left.\n" +
                        "Users can place any type of knight as many times as they want as long as they have it in their inventory" +
                        "except for Pegasus, which can be used only once per battle\n" +
                        "After placing your armies on the battlefield, click on Okay." +
                        "Once the two players are ready, the battle will start.\n\n" +
                        "GOOD LUCK");
                knights.setImageResource(0);
                battlefield.setImageResource(R.drawable.battlefield);

                break;
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
