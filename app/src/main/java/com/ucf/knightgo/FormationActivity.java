package com.ucf.knightgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;


public class FormationActivity extends AppCompatActivity
{

    private int[] inventory;
    private int[] armyFormation = new int[9];
    public static final String FORMATION = "com.ucf.knightgo.Formation";
    private TextView invenText;

    //add the image-changing functionality
    public void setSpinListener(Spinner spin, final ImageView img)
    {
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                String current = parent.getSelectedItem().toString();

                switch(current)
                {
                    case("None"):
                    {img.setImageResource(R.drawable.none);
                        break;}
                    case("Shield"):
                    {img.setImageResource(R.drawable.shield);
                        break;}
                    case("Bow"):
                    {img.setImageResource(R.drawable.longbow);
                        break;}
                    case("Crossbow"):
                    {img.setImageResource(R.drawable.crossbow);
                        break;}
                    case("Spear"):
                    {img.setImageResource(R.drawable.spear);
                        break;}
                    case("Sword"):
                    {img.setImageResource(R.drawable.sword);
                        break;}
                    case("Axe"):
                    {img.setImageResource(R.drawable.axe);
                        break;}
                    case("Mace"):
                    {img.setImageResource(R.drawable.mace);
                        break;}
                    case("Dagger"):
                    {img.setImageResource(R.drawable.dagger);
                        break;}
                    case("Halberd"):
                    {img.setImageResource(R.drawable.halberd);
                        break;}
                    case("Pegasus"):
                    {img.setImageResource(R.drawable.pegasus);
                        break;}
                    default:
                    {break;}
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }

    public int getType(Spinner spin)
    {
        int type = -1;

        //I know, I know, another switch
        String choice = spin.getSelectedItem().toString();
        switch(choice)
        {
            case("None"):
            {
                type=-1;
                break;
            }
            case("Shield"):
            {   type=0;
                break;
            }
            case("Bow"):
            {
                type=1;
                break;
            }
            case("Crossbow"):
            {
                type=2;
                break;
            }
            case("Spear"):
            {   type=3;
                break;
            }
            case("Sword"):
            {   type=4;
                break;
            }
            case("Axe"):
            {   type=5;
                break;
            }
            case("Mace"):
            {   type=6;
                break;
            }
            case("Dagger"):
            {   type=7;
                break;
            }
            case("Halberd"):
            {   type=8;
                break;
            }
            case("Pegasus"):
            {   type=9;
                break;
            }
            default:
            {break;}
        }

        return type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);

        inventory = Player.getInstance().getInventory();

        invenText = (TextView)findViewById(R.id.inventoryText);
        invenText.setMovementMethod(new ScrollingMovementMethod());

        // Show the players inventory
        invenText.setText("Available Knights:\nShield: " + inventory[0] + "\nBow: " +
                + inventory[1] +"\nCrossbow: " + inventory[2] + "\nSpear: " + inventory[3] + "\nSword: "
                + inventory[4] + "\nAxe: " + inventory[5] + "\nMace: " + inventory[6] + "\nDagger: "
                + inventory[7] + "\nHalberd: " + inventory[8] + "\nPegasus: " + inventory[9]);

        //create all variables for the images and dropdowns
        final ImageView ia1 = (ImageView) findViewById(R.id.ia1);
        final Spinner sa1 = (Spinner) findViewById(R.id.sa1);
        final ImageView ia2 = (ImageView) findViewById(R.id.ia2);
        final Spinner sa2 = (Spinner) findViewById(R.id.sa2);
        final ImageView ia3 = (ImageView) findViewById(R.id.ia3);
        final Spinner sa3 = (Spinner) findViewById(R.id.sa3);
        final ImageView ib1 = (ImageView) findViewById(R.id.ib1);
        final Spinner sb1 = (Spinner) findViewById(R.id.sb1);
        final ImageView ib2 = (ImageView) findViewById(R.id.ib2);
        final Spinner sb2 = (Spinner) findViewById(R.id.sb2);
        final ImageView ib3 = (ImageView) findViewById(R.id.ib3);
        final Spinner sb3 = (Spinner) findViewById(R.id.sb3);
        final ImageView ic1 = (ImageView) findViewById(R.id.ic1);
        final Spinner sc1 = (Spinner) findViewById(R.id.sc1);
        final ImageView ic2 = (ImageView) findViewById(R.id.ic2);
        final Spinner sc2 = (Spinner) findViewById(R.id.sc2);
        final ImageView ic3 = (ImageView) findViewById(R.id.ic3);
        final Spinner sc3 = (Spinner) findViewById(R.id.sc3);

        //set listeners for all drop-downs
        setSpinListener(sa1,ia1); setSpinListener(sa2,ia2); setSpinListener(sa3,ia3);
        setSpinListener(sb1,ib1); setSpinListener(sb2,ib2); setSpinListener(sb3,ib3);
        setSpinListener(sc1,ic1); setSpinListener(sc2,ic2); setSpinListener(sc3,ic3);


        //done button
        final Button done = (Button) findViewById(R.id.doneButton);
        done.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //get the knight types for the users formation and populate an array
                int typeList[] = new int[19];
                typeList[0] = getType(sa1);
                typeList[1] = getType(sb1);
                typeList[2] = getType(sc1);
                typeList[3] = getType(sa2);
                typeList[4] = getType(sb2);
                typeList[5] = getType(sc2);
                typeList[6] = getType(sa3);
                typeList[7] = getType(sb3);
                typeList[8] = getType(sc3);

                // Scan every cell for an empty knight (value -1)
                int emptyFlag = 0;
                int insufFlag = 0;
                for(int i= 0; i<9;i++)
                {
                    if(typeList[i] == -1)
                        emptyFlag = 1;

                    // Build submitted army
                    armyFormation[i]++;
                }
                for(int i = 0; i<9;i++)
                {
                    if(inventory[i] - armyFormation[i] < 0)
                    {
                        insufFlag = 1;
                    }
                }

                // If an empty knight is found, notify user
                if(emptyFlag == 1)
                {
                    Context context = getApplicationContext();
                    Toast emptySpot = Toast.makeText(context,"All spaces must be occupied", Toast.LENGTH_LONG);
                    emptySpot.show();
                }
                else
                {
                    // If there wasn't enough Knights in inventory for Formation, notify user
                    if(insufFlag == 1)
                    {
                        Context context = getApplicationContext();
                        Toast insufKnights = Toast.makeText(context,"Not enough knights for formation. Go collect some!", Toast.LENGTH_LONG);
                        insufKnights.show();
                    }
                    // Else all spots are filled and formation is valid.
                    else
                    {
                        goToSimulation(v, typeList);
                    }
                }
            }
        });
    }


    public void goToSimulation(View view, int typeList[])
    {
        Intent intent = new Intent(this, SimulationActivity.class);
        //grab connection type from BluetoothActivity
        typeList[18] = intent.getIntExtra(BluetoothActivity.CONNECTION_TYPE, -1);
        //send the list off to the FormationActivity
        intent.putExtra(FORMATION,typeList);
        startActivity(intent);
    }
}
