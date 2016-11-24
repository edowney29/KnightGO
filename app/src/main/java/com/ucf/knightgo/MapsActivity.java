package com.ucf.knightgo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final double x = 28.6024274;
    private final double y = -81.2000599;
    private final LatLng ucfLocation = new LatLng(x, y);
    private final int knightsNumber = 10;
    public ArrayList<Knight> Inventory = new ArrayList<>();
    private ArrayList<Knight> knightList   = new ArrayList<Knight>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in ucf and move the camera
        //mMap.addMarker(new MarkerOptions().position(ucfLocation).title("Marker in ucf"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucfLocation, 15));

        //THIS IS SUPPOSED TO SHOW A CURRENT LOCATION BUTTON
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        //create random markers so we can put objects there.
        CreateKnights();


    }
    public void CreateKnights(){;
        Random r = new Random();
        int knightType = 0;
        for(int i = 0 ;i < knightsNumber ; i++){
            // just until 8 cuz we want only 1 pegasus to be available.
            knightType = r.nextInt(8);
            Knight newKnight = new Knight(knightType);
            knightList.add(newKnight);
            newKnight.setMapLocation();
            double latitude = newKnight.getLatitude();
            double longitude = newKnight.getLongitude();

            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(newKnight.getName()));

        }
        //Adding the only Pegasus
        Knight newKnight = new Knight(9);
        knightList.add(newKnight);
        newKnight.setMapLocation();
        double latitude = newKnight.getLatitude();
        double longitude = newKnight.getLongitude();
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(newKnight.getName()));




}


}
