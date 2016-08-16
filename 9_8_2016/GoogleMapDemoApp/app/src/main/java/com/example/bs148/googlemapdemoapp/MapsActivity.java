package com.example.bs148.googlemapdemoapp;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Marker> markers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        markers=new ArrayList<Marker>();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng dhaka = new LatLng(23.781003, 90.396204);
        mMap.addMarker(new MarkerOptions().position(dhaka).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));*/

        LatLng dhaka=new LatLng(23.781003, 90.396204);
        MarkerOptions options=new MarkerOptions();
        options.position(dhaka).title("Mohakhali Dohs").snippet("This is my location").draggable(true);
        Marker marker=mMap.addMarker(options);
        markers.add(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));

        LatLng ewu=new LatLng(23.768644, 90.425624);
        MarkerOptions options1=new MarkerOptions();
        options1.position(ewu).title("EWU").snippet("This is my University").draggable(true);
        final Marker marker1=mMap.addMarker(options1);
        markers.add(marker1);

        //for adding icon use icon or for changinf marker color

        LatLng chittagong=new LatLng(28.23434, 95.46765756);
        MarkerOptions options2=new MarkerOptions();
        options2.position(chittagong).title("Chittagong").snippet("This is Chittagong").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        Marker marker2=mMap.addMarker(options2);
        markers.add(marker2);

        /*mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker.equals(markers.get(0))){
                    Toast.makeText(getApplicationContext(),"This is mohakhali dohs",Toast.LENGTH_LONG).show();
                }else if(marker.equals(markers.get(1))){
                    Toast.makeText(getApplicationContext(),"This is EWU",Toast.LENGTH_LONG).show();
                }else if(marker.equals(markers.get(2))){
                    Toast.makeText(getApplicationContext(),"This is Chittagong",Toast.LENGTH_LONG).show();
                }
            }
        });*/

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                //Toast.makeText(getApplicationContext(), "Marker Start",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), marker.getPosition().toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                //Toast.makeText(getApplicationContext(), "Marker End",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), marker.getPosition().toString(),Toast.LENGTH_LONG).show();
                Geocoder geocoder=new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses=geocoder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,5);//aita list of address return kore.here 5 means 5 ta return korbe
                    for (Address address: addresses){
                        Log.e("Address",address.getAddressLine(0)+"--"+address.getAdminArea()+"--"+address.getCountryName()+"--"+address.getPostalCode());
                        Toast.makeText(getApplicationContext(),address.getAddressLine(0)+"--"+address.getAdminArea()+"--"+address.getCountryName()+"--"+address.getPostalCode(),Toast.LENGTH_LONG).show();
                    }
                    //also change the marker title when drag
                    marker.setTitle(addresses.get(0).getCountryCode());
                    marker.setSnippet(addresses.get(0).getAddressLine(0));
                    marker.hideInfoWindow();
                    marker.showInfoWindow();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "Marker Clicked",Toast.LENGTH_LONG).show();
                return false;
            }
        });*/
        // draw polyline
        PolylineOptions rect=new PolylineOptions();
        rect.add(new LatLng(23.23434, 90.46765756)).add(new LatLng(23.23434, 95.46765756)).add(new LatLng(28.23434, 95.46765756)).add(new LatLng(28.23434,90.46765756)).add(new LatLng(23.23434, 90.46765756));
        Polyline polyline = mMap.addPolyline(rect);
        polyline.setColor(Color.GREEN);
    }

}
