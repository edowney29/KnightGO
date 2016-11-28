package com.ucf.knightgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.content.Intent;


public class FormationActivity extends AppCompatActivity
{
    public static final String FORMATION = "com.ucf.knightgo.Formation";

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
                {   case("Shield"):
                {
                    img.setImageResource(R.drawable.shield);
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
            {}
        });
    }

    public int getType(Spinner spin)
    {
        int type = -1;

        //I know, I know, another switch
        String choice = spin.getSelectedItem().toString();
        switch(choice)
        {   case("Shield"):
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

                goToSimulation(v, typeList);
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
