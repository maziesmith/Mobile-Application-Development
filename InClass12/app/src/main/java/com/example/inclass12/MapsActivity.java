package com.example.inclass12;

import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener {
    PolylineOptions rectOptions;
    private GoogleMap mMap;
    LocationManager mLocationManager;
    LocationListener mLocListener;
    private boolean startFlag;
    Polyline polyline;
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    CameraUpdate center;
    List<LatLng> listOfPoints;
    LatLngBounds bounds;
    final WeakHashMap<Marker, String> mMarkers = new WeakHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        listOfPoints = new ArrayList<LatLng>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        startFlag = false;
        mMap = googleMap;
        rectOptions = new PolylineOptions();
        // Add a marker in Sydney and move the camera
        //   LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        polyline = mMap.addPolyline(rectOptions);
        mMap.setOnMapLongClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS Not Enabled").setMessage("Would you liek to enable GPS service?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (!startFlag) {
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("GPS Not Enabled").setMessage("Would you liek to enable GPS service?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                mMap.clear();
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
                Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                listOfPoints.clear();
                //   mMap.clear();
                //mMap.clear();
                mMap.setMyLocationEnabled(true);
                polyline = mMap.addPolyline(rectOptions);

                Marker m1 = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Start Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMarkers.put(m1, "m1");
                Toast.makeText(getApplicationContext(),
                        "Start Location Tracking", Toast.LENGTH_LONG)
                        .show();

/*
                center = CameraUpdateFactory.newLatLng(latLng);
                mMap.moveCamera(center);

                CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
               mMap.animateCamera(zoom);*/
                startFlag = true;
            }
        } else

        {

            listOfPoints.add(latLng);
            polyline.setPoints(listOfPoints);
            mLocationManager.removeUpdates(this);
            Marker m2 = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("End Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMarkers.put(m2, "m2");
            Toast.makeText(getApplicationContext(),
                    "End Location Tracking", Toast.LENGTH_LONG)
                    .show();
            startFlag = false;

        }
    }


    @Override
    public void onLocationChanged(Location location) {

        Log.i("called", "onLocationChanged");
        Log.d("demo", location.getLatitude() + " " + location.getLongitude());

        //rectOptions.add(new LatLng(location.getLatitude(), location.getLongitude()));
        listOfPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));

        polyline.setPoints(listOfPoints);
        if (startFlag) {
            builder.include(new LatLng(location.getLatitude(), location.getLongitude()));

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
            //when the location changes, update the map by zooming to the location
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
