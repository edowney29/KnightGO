package com.ucf.knightgo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.MainThread;
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
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public int inventorySize = 0;
    private int pickupRange = 75;
    private static GoogleMap mMap;

    private final Location ucfCampus = new Location("UCF Campus");
    private final int knightsNumber = 30;
    public static ArrayList<Knight> knightList  = new ArrayList<>();
    public static ArrayList<Marker> knightMarkers = new ArrayList<>();
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private LatLng ucfLocation;
    Location mLastLocation;
    Circle circle;
    private MarkerOptions knightMarker;
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


        Calendar cal = Calendar.getInstance();

        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);

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

        // Check if location services is enabled.
        LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        // If Location Services are disabled, notify user and return to main menu.
        if(!gps_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Please enable Location Services to use Exploration Mode");
            dialog.setNeutralButton("Return to Main Menu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent intent = new Intent(MapsActivity.this, MainMenu.class);
                    startActivity(intent);
                }
            });
            dialog.setCancelable(false);
            dialog.show();

        }
            // Focus map to UCF campus.
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucfLocation, 15));

        // Generate knights
        if(knightList.size() == 0) {
            CreateKnights();
        }

        DisplayKnights();

    }

    // Set up Play Services client
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
        // Set up location requests.
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        // Request location updates.
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

        // Get current location
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Draw pick up range indicator around player location.
        if(circle != null) {
            circle.remove();
        }
        circle = mMap.addCircle(new CircleOptions().center(latLng).radius(pickupRange).strokeColor(Color.RED).visible(true));

    }

    public void CreateKnights(){
        Random r = new Random();
        int knightType = 0;
        LatLng knightLoc;
        for(int i = 0 ;i < knightsNumber ; i++){

            knightType = r.nextInt(10);

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

            // At the very end we at them to our Array list to keep track of Knights
            knightList.add(newKnight);
        }

    }


    public void DisplayKnights(){
        //Clear map and marker array.
        mMap.clear();
        knightMarkers.clear();

        // Generate new markers based on knight array.
        for(int i = 0; i < knightList.size();i++) {
            Knight mKnight = knightList.get(i);
            knightMarker = new MarkerOptions()
                    .position(mKnight.getLocation())
                    .title(mKnight.getName())
                    .icon(BitmapDescriptorFactory.fromResource(mKnight.getMapIcon()));
            knightMarkers.add(mMap.addMarker(knightMarker));

            // Set the knight instance to each marker.
            knightMarkers.get(i).setTag(mKnight);
        }
    }

    // Runs when a marker is pressed.
    @Override
    public boolean onMarkerClick(final Marker marker) {

        Knight selectedKnight= (Knight)marker.getTag();
        curKnight = selectedKnight;
        curMarker = marker;
        if(curKnight == null)
            return false;

        Location knightLoc = new Location("Knight Location");
        knightLoc.setLatitude(curKnight.getLatitude());
        knightLoc.setLongitude(curKnight.getLongitude());

        // REMOVE || TRUE ON RELEASE!!!
        // If player is within pickup range of selected knight, move to Camera activity.
        if(mLastLocation.distanceTo(knightLoc) <= pickupRange || true) {
            Intent intent = new Intent(this, CameraViewActivity.class);
            intent.putExtra("icon", selectedKnight.getBigIcon());
            intent.putExtra("kLat", selectedKnight.getLatitude());
            intent.putExtra("kLong", selectedKnight.getLongitude());
            intent.putExtra("myLat", mLastLocation.getLatitude());
            intent.putExtra("myLong", mLastLocation.getLongitude());

            startActivityForResult(intent, 1);

            return true;
        }
        else
        {
            outofRangeMessage();
            return false;
        }
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
                curMarker.remove();
                Context context = getApplicationContext();
                Toast confirm = Toast.makeText(context,curKnight.getName() + " Knight has been added to your army", Toast.LENGTH_LONG);
                confirm.show();
            }
        }
        // Generate knights if needed. NOTE: This should be replaced by timer generated knights.
        if(knightList.size() == 0) {
            CreateKnights();
        }

        DisplayKnights();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
    @Override
    public void onResume(){
        super.onResume();

        // Start location updates
        if(mGoogleApiClient != null) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }

        // TODO: timer goes here
    }

    private void outofRangeMessage(){
        Context context = getApplicationContext();
        Toast outRange = Toast.makeText(context,"Knight is out of range for collection", Toast.LENGTH_LONG);
        outRange.show();
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
    //request permission to access location
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
