package com.example.gmaps;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);

    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);

    private static final LatLng PERTH = new LatLng(-31.95285, 115.85734);

    private static final LatLng LHR = new LatLng(51.471547, -0.460052);

    private static final LatLng LAX = new LatLng(33.936524, -118.377686);

    private static final LatLng JFK = new LatLng(40.641051, -73.777485);

    private static final LatLng AKL = new LatLng(-37.006254, 174.783018);

    private static final int WIDTH_MAX = 50;

    private static final int HUE_MAX = 360;

    private static final int ALPHA_MAX = 255;

    private Polyline mMutablePolyline;

    private Polyline mClickablePolyline;

    private SeekBar mColorBar;

    private SeekBar mAlphaBar;

    private SeekBar mWidthBar;

    private CheckBox mClickabilityCheckbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
    public void onMapReady(GoogleMap map) {
        map.setContentDescription("Google Map with polylines.");

        // A simple polyline with the default options from Melbourne-Adelaide-Perth.
        map.addPolyline((new PolylineOptions())
                .add(MELBOURNE, ADELAIDE, PERTH));

        // A geodesic polyline that goes around the world.
        mClickablePolyline = map.addPolyline((new PolylineOptions())
                .add(LHR, AKL, LAX, JFK, LHR)
                .width(5)
                .color(Color.BLUE)
                .geodesic(true)
                .clickable(mClickabilityCheckbox.isChecked()));

        // Rectangle centered at Sydney.  This polyline will be mutable.
        int radius = 5;
        PolylineOptions options = new PolylineOptions()
                .add(new LatLng(SYDNEY.latitude + radius, SYDNEY.longitude + radius))
                .add(new LatLng(SYDNEY.latitude + radius, SYDNEY.longitude - radius))
                .add(new LatLng(SYDNEY.latitude - radius, SYDNEY.longitude - radius))
                .add(new LatLng(SYDNEY.latitude - radius, SYDNEY.longitude + radius))
                .add(new LatLng(SYDNEY.latitude + radius, SYDNEY.longitude + radius));
        int color = Color.HSVToColor(
                mAlphaBar.getProgress(), new float[]{mColorBar.getProgress(), 1, 1});
        mMutablePolyline = map.addPolyline(options
                .color(color)
                .width(mWidthBar.getProgress())
                .clickable(mClickabilityCheckbox.isChecked()));
/*
        mColorBar.setOnSeekBarChangeListener(this);
        mAlphaBar.setOnSeekBarChangeListener(this);
        mWidthBar.setOnSeekBarChangeListener(this);
*/
        // Move the map so that it is centered on the mutable polyline.
        map.moveCamera(CameraUpdateFactory.newLatLng(SYDNEY));

        // Add a listener for polyline clicks that changes the clicked polyline's color.
        map.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                // Flip the values of the r, g and b components of the polyline's color.
                int strokeColor = polyline.getColor() ^ 0x00ffffff;
                polyline.setColor(strokeColor);
            }
        });
    }
}
