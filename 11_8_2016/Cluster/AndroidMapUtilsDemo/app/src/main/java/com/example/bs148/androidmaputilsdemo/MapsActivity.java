package com.example.bs148.androidmaputilsdemo;

import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ClusterManager<ClusterMarkerLocation> mClusterManager;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        MultiDex.install(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
        setUpClusterer();
    }

    private void setUpClusterer() {
        // Declare a variable for the cluster manager.

        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.781009, 90.396250), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<ClusterMarkerLocation>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraChangeListener(mClusterManager);
        //mMap.setOnCameraIdleListener((GoogleMap.OnCameraIdleListener) mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        /*double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            ClusterMarkerLocation offsetItem = new ClusterMarkerLocation(lat, lng);
            mClusterManager.addItem(offsetItem);
        }*/
        ClusterMarkerLocation offsetItem = new ClusterMarkerLocation(23.781009, 90.396250);
        mClusterManager.addItem(offsetItem);
        ClusterMarkerLocation offsetItem1 = new ClusterMarkerLocation(23.768625, 90.425597);
        mClusterManager.addItem(offsetItem1);
        ClusterMarkerLocation offsetItem2 = new ClusterMarkerLocation(23.813424, 90.423601);
        mClusterManager.addItem(offsetItem2);
        ClusterMarkerLocation offsetItem3 = new ClusterMarkerLocation(23.734717, 90.413221);
        mClusterManager.addItem(offsetItem3);
        ClusterMarkerLocation offsetItem4 = new ClusterMarkerLocation(23.717225, 90.418246);
        mClusterManager.addItem(offsetItem4);
        ClusterMarkerLocation offsetItem5 = new ClusterMarkerLocation(23.731875, 90.392669);
        mClusterManager.addItem(offsetItem5);
        ClusterMarkerLocation offsetItem6 = new ClusterMarkerLocation(23.688683, 90.443112);
        mClusterManager.addItem(offsetItem6);
        ClusterMarkerLocation offsetItem7 = new ClusterMarkerLocation(23.676097, 90.396012);
        mClusterManager.addItem(offsetItem7);
        ClusterMarkerLocation offsetItem8 = new ClusterMarkerLocation(23.877585, 90.390201);
        mClusterManager.addItem(offsetItem8);
        ClusterMarkerLocation offsetItem9 = new ClusterMarkerLocation(23.900153, 90.323884);
        mClusterManager.addItem(offsetItem9);
    }
}
