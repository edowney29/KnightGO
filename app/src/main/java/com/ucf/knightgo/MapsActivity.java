package com.ucf.knightgo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static GoogleMap mMap;
    private final double x = 28.6024274;
    private final double y = -81.2000599;
    private final LatLng ucfLocation = new LatLng(x, y);
    private final int knightsNumber = 10;
    public ArrayList<Knight> Inventory = new ArrayList<>();
    public static ArrayList<Knight> knightList  = new ArrayList<Knight>();
    public static ArrayList<MarkerOptions> markerList = new ArrayList<>();


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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            Context context = getApplicationContext();
            Toast see = Toast.makeText(context,"Hey we see you!", Toast.LENGTH_LONG);
            see.show();
        } else {
            // Show rationale and request permission.
        }
        //WELCOME!
        WelcomeMessage();

        //create random markers so we can put objects there.
        if(knightList.size() < 10)
            CreateKnights();

        DisplayKnights();

    }

    public void DisplayKnights(){
        for(int i = 0; i < markerList.size();i++)
        mMap.addMarker(markerList.get(i));
    }
    public void CreateKnights(){
        Random r = new Random();
        int knightType = 0;
        LatLng knightLoc = ucfLocation;
        for(int i = 0 ;i < knightsNumber ; i++){
            // just until 8 because we want only 1 pegasus to be available.
            knightType = r.nextInt(8);

            Knight newKnight = new Knight(knightType);
            MarkerOptions knightMarker;

            newKnight.setMapLocation();

            double latitude = newKnight.getLatitude();
            double longitude = newKnight.getLongitude();
            knightLoc = new LatLng(latitude,longitude);
            newKnight.setLocation(knightLoc);
            //create the marker
            knightMarker = new MarkerOptions()
                    .position(knightLoc)
                    .title(newKnight.getName())
                    .icon(BitmapDescriptorFactory.fromResource(newKnight.getMapIcon()));

            // lets add a circle around each mark. so when we are near the circle, we can pick them up.
            Circle circle = mMap.addCircle(new CircleOptions().center(knightLoc).radius(40).strokeColor(Color.RED));
            circle.setVisible(false);
            circle.setClickable(true);

            //at the very end we at them to our Array list to keep track of what is that we have created!
            knightList.add(newKnight);
            markerList.add(knightMarker);

        }
        //Adding the only Pegasus
        Knight newKnight = new Knight(9);
        newKnight.setMapLocation();
        double latitude = newKnight.getLatitude();
        double longitude = newKnight.getLongitude();
        knightLoc = new LatLng(latitude,longitude);
        newKnight.setLocation(knightLoc);
        mMap.addMarker(new MarkerOptions()
                .position(knightLoc)
                .title(newKnight.getName())
                .icon(BitmapDescriptorFactory.fromResource(newKnight.getMapIcon())));
        Circle circle = mMap.addCircle(new CircleOptions().center(knightLoc).radius(40).strokeColor(Color.RED));
        //circle.setVisible(false);
        circle.setClickable(true);
        knightList.add(newKnight);

}
    @Override
    public boolean onMarkerClick(final Marker marker) {

        String name= marker.getTitle();
        Context context = getApplicationContext();
        Toast welcome = Toast.makeText(context,"Clicked!", Toast.LENGTH_LONG);
        welcome.show();
        marker.remove();
        if (name.equalsIgnoreCase("My Spot"))
        {

            //write your code here
        }
        return true;
    }

    private void WelcomeMessage(){
        Context context = getApplicationContext();
        Toast welcome = Toast.makeText(context,"Welcome! Start picking up knights", Toast.LENGTH_LONG);
        welcome.show();
    }

    private void PickUpKnights(){

    }


}
