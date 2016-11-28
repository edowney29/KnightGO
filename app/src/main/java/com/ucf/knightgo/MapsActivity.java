package com.ucf.knightgo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import android.location.*;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.gms.common.ConnectionResult;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public int inventorySize = 0;
    private static GoogleMap mMap;

    private final Location ucfCampus = new Location("UCF Campus");
    private final int knightsNumber = 10;
    public static ArrayList<Knight> knightList  = new ArrayList<Knight>();
    public static ArrayList<MarkerOptions> markerList = new ArrayList<>();
    public static ArrayList<Marker> knightMarkers = new ArrayList<>();
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private LatLng ucfLocation;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private Marker curMarker;
    private Knight curKnight;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ucfCampus.setLatitude(28.6024274);
        ucfCampus.setLongitude(-81.2000599);
        ucfLocation = new LatLng(28.6024274,-81.2000599);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        // Shows current location button
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                buildGoogleApiClient();
            } else {
                checkLocationPermission();
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucfLocation, 15));

        // Welcome message on 1st visit of activity
        if(knightList.size()== 0)
            WelcomeMessage();

        // Generate knights
        if(markerList.size() < 2) {
            CreateKnights();
        }

        DisplayKnights();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        if(mLastLocation.distanceTo(ucfCampus) > 5000)
        {
            Context context = getApplicationContext();
            Toast offCampus = Toast.makeText(context,"Travel to the UCF Campus to find Knights", Toast.LENGTH_LONG);
            offCampus.show();
        }

        // Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        // Move map camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        // Stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public void DisplayKnights(){
        knightMarkers.clear();
        for(int i = 0; i < markerList.size();i++) {
            knightMarkers.add(mMap.addMarker(markerList.get(i)));
            knightMarkers.get(i).setTag(knightList.get(i));
        }
        Context context = getApplicationContext();
        Toast welcome = Toast.makeText(context,"Knights left " + knightMarkers.size(), Toast.LENGTH_LONG);
        welcome.show();
    }

    public void CreateKnights(){
        Random r = new Random();
        int knightType = 0;
        LatLng knightLoc;
        MarkerOptions knightMarker;
        for(int i = 0 ;i < knightsNumber ; i++){
            // Just until 8 because we want only 1 pegasus to be available
            knightType = r.nextInt(8);

            Knight newKnight = new Knight(knightType);

            newKnight.setMapLocation();

            double latitude = newKnight.getLatitude();
            double longitude = newKnight.getLongitude();
            knightLoc = new LatLng(latitude,longitude);
            newKnight.setLocation(knightLoc);
            // Create the marker
            knightMarker = new MarkerOptions()
                    .position(knightLoc)
                    .title(newKnight.getName())
                    .icon(BitmapDescriptorFactory.fromResource(newKnight.getMapIcon()));

            // Lets add a circle around each mark. so when we are near the circle, we can pick them up
            Circle circle = mMap.addCircle(new CircleOptions().center(knightLoc).radius(40).strokeColor(Color.RED));
            circle.setVisible(false);
            circle.setClickable(true);

            // At the very end we at them to our Array list to keep track of what is that we have created
            knightList.add(newKnight);
            markerList.add(knightMarker);
        }

        // Adding the only Pegasus
        Knight newKnight = new Knight(9);
        newKnight.setMapLocation();
        double latitude = newKnight.getLatitude();
        double longitude = newKnight.getLongitude();
        knightLoc = new LatLng(latitude,longitude);
        newKnight.setLocation(knightLoc);
        knightMarker = new MarkerOptions()
                .position(knightLoc)
                .title(newKnight.getName())
                .icon(BitmapDescriptorFactory.fromResource(newKnight.getMapIcon()));
        Circle circle = mMap.addCircle(new CircleOptions().center(knightLoc).radius(40).strokeColor(Color.RED));
        //circle.setVisible(false);
        circle.setClickable(true);
        knightList.add(newKnight);
        markerList.add(knightMarker);
    }

    // Runs when a marker is pressed
    @Override
    public boolean onMarkerClick(final Marker marker) {
        Knight selectedKnight= (Knight)marker.getTag();
        curKnight = selectedKnight;
        curMarker = marker;
        Intent intent = new Intent(this, CameraViewActivity.class);
        intent.putExtra("icon",selectedKnight.getBigIcon());
        intent.putExtra("kLat",selectedKnight.getLatitude());
        intent.putExtra("kLong",selectedKnight.getLongitude());
        intent.putExtra("myLat",mLastLocation.getLatitude());
        intent.putExtra("myLong",mLastLocation.getLongitude());

        startActivityForResult(intent,1);

        return true;
    }


    // Called when Camera activity returns
    @Override
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aData) {
        if(aRequestCode == 1)
        {
            // If cameraActivity returned a 1, the knight was captured
            if(aResultCode == 1)
            {
                // Add knight to inventory and delete marker
                Player.getInstance().addKnight(curKnight.getType());
                inventorySize++;
                knightList.remove(curKnight);
                knightMarkers.remove(curMarker);
                markerList.clear();
                curMarker.remove();
            }
            DisplayKnights();
        }
    }

    @Override
    public void onResume(){

        super.onResume();
        // TODO: timer goes here
    }

    private void WelcomeMessage(){
        Context context = getApplicationContext();
        Toast welcome = Toast.makeText(context,"Welcome! Start picking up knights", Toast.LENGTH_LONG);
        welcome.show();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
