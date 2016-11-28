package com.ucf.knightgo;

/**
 * Created by KShoults on 11/23/2016.
 * Some code adapted from examples by Manoj Prasad.
 */

import android.app.DialogFragment;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

    private static final int REQUEST_BT_ENABLE = 1;
    private ArrayAdapter<String> blueArrayAdapter;
    private ArrayList<String> discoveredDevices;
    private BluetoothAdapter blueAdapter;
    public static BluetoothSocket socket;
    private ListView deviceList;
    private UUID uuid = UUID.fromString("8a0100f0-fce2-4c2c-8521-a90e4c2f1189");
    private int connectAs;
    public static final String CONNECTION_TYPE = "com.ucf.knightgo.ConnectionTypeBlue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        // Gets the device's default Bluetooth adapter
        blueAdapter = BluetoothAdapter.getDefaultAdapter();

        // No Bluetooth adapter detected, pull up a dialog box with an error message
        if(blueAdapter == null) {
            DialogFragment noBluetooth = new NoBluetoothDialogFragment();
            noBluetooth.show(getFragmentManager(), "bluetooth");
        }

        else {
            if (!blueAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_BT_ENABLE);
            }
            setupDeviceList();
        }
    }

    private void setupDeviceList() {
        discoveredDevices = new ArrayList<String>();
        blueArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, discoveredDevices);

        deviceList = (ListView)findViewById(R.id.list_discovered);
        deviceList.setAdapter(blueArrayAdapter);

        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
                AsyncTask<Integer, Void, Void> connectTask = new AsyncTask<Integer, Void, Void>() {

                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            String deviceStr = discoveredDevices.get(params[0]);
                            int MACIdx = deviceStr.indexOf('\n') + 1;
                            String MACAddress = deviceStr.substring(MACIdx);
                            BluetoothDevice device = blueAdapter.getRemoteDevice(MACAddress);
                            socket = device.createRfcommSocketToServiceRecord(uuid);
                            socket.connect();
                        } catch (IOException e) {
                            Log.d("BLUETOOTH_CLIENT", e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // Labels this device as the client (attacker) for future Activities to use
                        connectAs = 1;
                        openFormation();
                    }
                };
                connectTask.execute(index);
            }
        });
    }

    BroadcastReceiver discoveryResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                blueArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };

    // Called when the user presses the "Start a Battle" button
    public void searchButton(View view) {
        registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        blueAdapter.startDiscovery();
    }

    public void waitButton(View view) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(discoverableIntent);
        AcceptThread waiting = new AcceptThread();
        waiting.start();
    }

    // Begins battle formation. Called when a Bluetooth connection has been made.
    public void openFormation() {
        Intent intent = new Intent(this, FormationActivity.class);
        intent.putExtra(CONNECTION_TYPE, connectAs);
        startActivity(intent);
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket serverSocket;

        AcceptThread() {

            BluetoothServerSocket tmp = null;
            try {
                tmp = blueAdapter.listenUsingRfcommWithServiceRecord("Knight GO Battle", uuid);
            } catch (IOException e) {
                Log.d("BLUETOOTH_SERVER", e.getMessage());
            }
            serverSocket = tmp;
        }

        public void run() {
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = serverSocket.accept();
                    if (socket != null) {
                        serverSocket.close();

                        // Labels this device as the server (defender) for future Activities to use
                        connectAs = 0;
                        openFormation();
                        break;
                    }
                } catch (IOException e) {
                    Log.d("BLUETOOTH_SERVER", e.getMessage());
                    break;
                }
            }
        }

        // Will cancel the listening socket, and cause the thread to finish
        public void cancel() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.d("BLUETOOTH_SERVER", e.getMessage());
            }
        }
    }
}