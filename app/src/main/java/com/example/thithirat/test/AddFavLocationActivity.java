package com.example.thithirat.test;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddFavLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private GPSTracker gpsTracker;
    private Location mLocation;

    double latitude, longtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        gpsTracker = new GPSTracker(getApplicationContext());
        mLocation = gpsTracker.getLocation();

        latitude = mLocation.getLatitude();
        longtitude = mLocation.getLongitude();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fav_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.add_place);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng current = new LatLng(latitude, longtitude);
        mMap.addMarker(new MarkerOptions().position(current).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current,(float) 8.0));
    }
}
