package com.example.rivertracker;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.rivertracker.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Vector;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;

    MarkerOptions marker;
    LatLng centerLocation;

    Vector<MarkerOptions> markerOptions;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        sessionManager = new SessionManager(getApplicationContext());
        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        client = LocationServices.getFusedLocationProviderClient(this);

        centerLocation = new LatLng(3.0,101);

        markerOptions = new Vector<>();


        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //get current location when permission is granted
            getCurrentLocation();

        } else {
            //request permission when permission is denied
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
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


    private void getCurrentLocation() {
        //Initialize task location
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //get current location when permission is granted
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    //When success
                    if (location != null) {
                        //sync map
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                //Initialize Lat Lng
                                LatLng latLng = new LatLng(location.getLatitude(),
                                        location.getLongitude());


                                //Create marker options
                                MarkerOptions options = new MarkerOptions()
                                        .position(latLng)
                                        .title("I am here");

                                //zoom map scale 15
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                googleMap.addMarker(options);

                                MarkerOptions options2 = (new MarkerOptions().title("Sungai Kampar, Perak")
                                        .position(new LatLng(4.237848,101.1485002))
                                        .snippet("Open: 24 Hour")
                                );

                                MarkerOptions options3 = (new MarkerOptions().title("Sungai Padas, Sabah")
                                        .position(new LatLng(5.17994,115.56536))
                                        .snippet("Open: 24 Hour")
                                );

                                MarkerOptions options4 = (new MarkerOptions().title("Sungai Gopeng, Perak")
                                        .position(new LatLng(4.472722,101.166405))
                                        .snippet("Open: 24 Hour")
                                );
                                MarkerOptions options5 = (new MarkerOptions().title("Sungai Kuala Kubu Bharu, Selangor")
                                        .position(new LatLng(3.560105,101.658310))
                                        .snippet("Open: 24 Hour")
                                );

                                googleMap.addMarker(options2);
                                googleMap.addMarker(options3);
                                googleMap.addMarker(options4);
                                googleMap.addMarker(options5);




                            }
                        });


                    }
                }
            });
        } else {
            //request permission when permission is denied
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
}}