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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;


public class FormationActivity extends AppCompatActivity
{

    private int[] inventory;
    private int[] armyFormation;
    public static final String PLAYER_FORMATION = "com.ucf.knight.go.PLAYER_FORMATION";
    public static final String ENEMY_FORMATION = "com.ucf.knightgo.ENEMY_FORMATION";
    private static InputStream mmInStream;
    private static OutputStream mmOutStream;
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

        try {
            mmInStream = BluetoothActivity.socket.getInputStream();
            mmOutStream = BluetoothActivity.socket.getOutputStream();
        } catch (IOException e) { }


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
                armyFormation = new int[10];

                //get the knight types for the users formation and populate an array
                int typeList[] = new int[9];
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
                int pegasusCount = 0;
                for(int i= 0; i < typeList.length;i++)
                {
                    if(typeList[i] == -1) {
                        emptyFlag = 1;
                    }

                    else {
                        // Build submitted army
                        armyFormation[typeList[i]]++;
                    }
                }

                for(int i = 0; i<9;i++)
                {
                    if(inventory[i] - armyFormation[i] < 0)
                    {
                        insufFlag = 1;
                    }
                }

                pegasusCount = armyFormation[9];

                // If an empty knight is found, notify user
                if(emptyFlag == 1)
                {
                    Context context = getApplicationContext();
                    Toast emptySpot = Toast.makeText(context,"All spaces must be occupied", Toast.LENGTH_LONG);
                    emptySpot.show();
                }
                else
                {
                    // If > 1 pegasus is in formation, notify user
                    if(pegasusCount > 1)
                    {
                        Context context = getApplicationContext();
                        Toast pegasusOvrflw = Toast.makeText(context,"Only 1 Pegasus allowed per formation", Toast.LENGTH_LONG);
                        pegasusOvrflw.show();
                    }
                    else
                    {
                        // If there wasn't enough Knights in inventory for Formation, notify user
                        if (insufFlag == 1) {
                            Context context = getApplicationContext();
                            Toast insufKnights = Toast.makeText(context, "Not enough knights for formation. Go collect some!", Toast.LENGTH_LONG);
                            insufKnights.show();
                        }
                        // Else all spots are filled and formation is valid.
                        else {
                            goToSimulation(v, typeList);
                        }
                    }
                }
            }
        });
    }


    public void goToSimulation(View view, int typeList[])
    {
        Intent intent2sim = new Intent(this, SimulationActivity.class);

        // Player formation is passed to method
        int[] playerFormation = typeList;

        // Enemy byte string length is 9*4 bits
        byte[] enemyBytes = new byte[typeList.length << 2];

        // Get connection type (Host or Server)
        Intent intentFromBlue = getIntent();
        int connectionType = intentFromBlue.getIntExtra(BluetoothActivity.CONNECTION_TYPE, -1);

        byte[] playerByteArray = int2byte(playerFormation);

        try{
            // Write formation array to Output stream
            mmOutStream.write(playerByteArray);
            mmInStream.read(enemyBytes);
        } catch (IOException e) { }

        // Convert enemy byte array to int array.
        int[] enemyFormation = byte2int(enemyBytes);


        //send the Formations off to FormationActivity
        intent2sim.putExtra(PLAYER_FORMATION,playerFormation);
        intent2sim.putExtra(ENEMY_FORMATION,enemyFormation);

        // Pass in BT connection type (1 or 0)
        intent2sim.putExtra(BluetoothActivity.CONNECTION_TYPE, connectionType);

        startActivity(intent2sim);
    }

    // Converts int array to byte array for Bluetooth
    public static byte[] int2byte(int[]src) {
        int srcLength = src.length;
        byte[]dst = new byte[srcLength << 2];

        for (int i=0; i<srcLength; i++) {
            int x = src[i];
            int j = i << 2;
            dst[j++] = (byte) ((x >>> 24) & 0xff);
            dst[j++] = (byte) ((x >>> 16) & 0xff);
            dst[j++] = (byte) ((x >>> 8) & 0xff);
            dst[j++] = (byte) ((x >>> 0) & 0xff);
        }
        return dst;
    }

    // Converts byte array to int array
    public static int[] byte2int(byte buf[]) {
        int intArr[] = new int[buf.length / 4];
        int offset = 0;
        for(int i = 0; i < intArr.length; i++) {
            intArr[i] = (buf[3 + offset] & 0xFF) | ((buf[2 + offset] & 0xFF) << 8) |
                    ((buf[1 + offset] & 0xFF) << 16) | ((buf[0 + offset] & 0xFF) << 24);
            offset += 4;
        }
        return intArr;
    }
}
