package com.example.thithirat.test;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UpdateLocationMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    PlaceAutocompleteFragment placeAutoComplete;

    private GPSTracker gpsTracker;
    private Location mLocation;

    double latitude, longtitude;
    double latitudefromselect, longtitudefromselect;
    String placename;

    Marker marker;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        gpsTracker = new GPSTracker(getApplicationContext());
        mLocation = gpsTracker.getLocation();

        latitude = mLocation.getLatitude();
        longtitude = mLocation.getLongitude();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.marker_map);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng getLatLng = place.getLatLng();
                latitudefromselect = getLatLng.latitude;
                longtitudefromselect = getLatLng.longitude;
                placename = (String) place.getName();

                geoLocation(latitudefromselect, longtitudefromselect, place);
                Log.d("LatLngLocation", latitudefromselect + ", " + longtitudefromselect + ", " + placename);

                connect_location(latitudefromselect, longtitudefromselect, placename);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        FloatingActionButton fab_place_done_reminder = (FloatingActionButton)findViewById(R.id.fab_place_done);
        fab_place_done_reminder.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("Onclick", "Complete");
                if(placename != null) {
                    Bundle args = new Bundle();
                    args.putString("PlaceName", placename);
                    UpdateLocationFragment.putArguments(args);
                }

                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void connect_location(double latitudefromselect, double longtitudefromselect, String placename) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(UpdateLocationMapsActivity.this);
            String URL = "http://161.246.5.195:3000/place/location";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("latitude", latitudefromselect);
            jsonBody.put("longtitude", longtitudefromselect);
            jsonBody.put("name", placename);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_location = json.getString("msg");
                                Log.i("VOLLEY", msg_location);
                                if(msg_location.equals("place/location : insert location complete")) {
                                    Toast.makeText(UpdateLocationMapsActivity.this, "Success", Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(UpdateLocationMapsActivity.this, "Already", Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void geoLocation(double latitudefromselect, double longtitudefromselect, Place place) {
        setMarker(place);
        goToLocationZoom(latitude, longtitude, 11);
    }

    private void setMarker(Place place) {
        if(marker != null) {
            marker.remove();
        }

        marker = mMap.addMarker(new MarkerOptions()
                .position(place.getLatLng())
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_marker_red))
                .title("Select here : " + place.getName()));

    }

    private void goToLocationZoom(double latitude, double longtitude, int zoom) {
        LatLng latlng = new LatLng(latitude, longtitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, zoom);
        mMap.moveCamera(update);
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

        // Add a marker in Sydney and move the camera
        LatLng current = new LatLng(latitude, longtitude);
        mMap.addMarker(new MarkerOptions()
                .position(current).title("You are here")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_person_red)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current,(float) 7.0));
    }

    private void callPlaceAutocompleteActivityIntent() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            //PLACE_AUTOCOMPLETE_REQUEST_CODE is integer for request code
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //autocompleteFragment.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("maps", "Place:" + place.toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("maps", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}
