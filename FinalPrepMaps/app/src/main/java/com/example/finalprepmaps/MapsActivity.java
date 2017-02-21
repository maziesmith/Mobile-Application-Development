package com.example.finalprepmaps;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager mLocationManager;
    private DatabaseReference mDatabase;
    Geocoder geocoder, geo1;
    LatLng p1;
    List<Address> addresses = null;
    List<Address> addresses1 = null;
    final WeakHashMap<Marker, String> mMarkers = new WeakHashMap<>();
    private DatabaseReference mFavCityReference;
    String key, key1;
    Boolean flag, citygone;
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    List<LatLng> plist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //mapFragment.getMapAsync(this);

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
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        //  LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
        final EditText ed = new EditText(this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Enter a city");
        builder1.setView(ed);
        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                //Editable YouEditTextValue = ed.getText();
                //OR
                String YouEditTextValue = ed.getText().toString();
                try {
                    geocoder = new Geocoder(MapsActivity.this);
                    addresses = geocoder.getFromLocationName(YouEditTextValue, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("demo1", addresses.toString());
                Address location = addresses.get(0);
                //location.getLatitude();
                //location.getLongitude();
                p1 = new LatLng(location.getLatitude(), location.getLongitude());
                //new Geolocate().execute(YouEditTextValue);
                Marker m1 = mMap.addMarker(new MarkerOptions()
                        .position(p1)
                        .title("Start Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                //mMap.moveCamera(CameraUpdateFactory);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(p1));
                mMarkers.put(m1, "m1");
                flag = true;
                mMap.setOnMarkerClickListener(MapsActivity.this);

            }
        });
        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });


        AlertDialog alert1 = builder1.create();
        alert1.show();
        mMap.setOnMapLongClickListener(this);
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


    private void onBackgroundTaskDataObtained(LatLng results) {
        //dismissDialog(DIALOG_LOADING);
        //do stuff with the results here..

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //Location location = new Location();
        LatLng location = marker.getPosition();
        try {
            geo1 = new Geocoder(MapsActivity.this);
            addresses1 = geocoder.getFromLocation(location.latitude, location.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("demo1", addresses1.toString());
        Address location1 = addresses1.get(0);

        location1.getAddressLine(0);
        Log.d("demo2", "here");
        Marker newMarker = mMap.addMarker(new MarkerOptions().position(marker.getPosition()).title("You are here")
                .snippet(location1.getAddressLine(0))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        newMarker.showInfoWindow();


        return true;
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        if(flag) {
            mMap.clear();
        }
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
        final Marker m2 = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("fav")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        String m = mMarkers.toString();
        Log.d("demomarker", m);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        flag = false;

        mFavCityReference = FirebaseDatabase.getInstance().getReference().child("Favloc");
        key = mFavCityReference.push().getKey();
        try {
            geo1 = new Geocoder(MapsActivity.this);
            addresses1 = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("demo1", addresses1.toString());
        Address location1 = addresses1.get(0);
        final String cityname = location1.getAddressLine(0);

        citygone = false;
        mFavCityReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    child.getValue();
                    //  Log.d("demotostring",child.toString());
                        Log.d("demovalue",child.getValue().toString());
                    // Log.d("demovalue",child.getKey().toString());
                    if(child.getValue().toString().contains(cityname)){
                        //addfavcity.setImageResource(R.drawable.favorite);
                        key1 = child.getKey().toString();
                        citygone = true;
                        Log.d("demoin", "in here");
                        break;
                    }
                    //if(child.getValue().toString().contains(key)){
                        //addfavcity.setImageResource(R.drawable.favorite);
                        //counter++;
                        //  Log.d("demokey", "key matches");
                    //}
                }
                Log.d("demogonefor", citygone.toString());
                if(!citygone) {
                    Favloc e = new Favloc(cityname, key);
                    Map<String, Object> postValues = e.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/Favloc/" + key, postValues);
                    e.setKey(key);
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.updateChildren(childUpdates);
                    plist.add(latLng);
                }else{
                    DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child("Favloc").child(key1);
                    db_node.setValue(null);
                    citygone = false;
                    m2.remove();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMarkers.put(m2,"m2");
        Log.d("demogone", citygone.toString());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.enter_address){
            final AlertDialog.Builder ad = new AlertDialog.Builder(this);
            final EditText edt = new EditText(this);
            edt.setHint("Enter Address");
            ad.setView(edt);
            ad.setTitle("Enter Address")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(edt.getText().toString().equals("") && edt.getText().toString().isEmpty()){
                                Toast.makeText(MapsActivity.this,"Location is needed!!!",Toast.LENGTH_LONG).show();
                            } else {
                                Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                                String location = edt.getText().toString();
                                try {
                                    List<Address> addressList =  geocoder.getFromLocationName(location,1);
                                    if(addressList.size()== 0){
                                        Toast.makeText(MapsActivity.this,"Invalid Address Entered.!!",Toast.LENGTH_LONG).show();
                                    }
                                    Address address = addressList.get(0);
                                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
                                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                                    mMap.setOnMarkerClickListener(MapsActivity.this);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .setCancelable(false);
            AlertDialog alert = ad.create();
            ad.show();
        }
        if(id == R.id.zoom_to_fit){
            for(int i=0;i<plist.size();i++) {
                builder.include(plist.get(i));
            }
            LatLngBounds bounds = builder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
        }
        if(id == R.id.clear_fav){
            mMap.clear();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().getRoot().child("Favloc");
            ref.setValue(null);
            plist.clear();
        }
        return super.onOptionsItemSelected(item);
    }
}
