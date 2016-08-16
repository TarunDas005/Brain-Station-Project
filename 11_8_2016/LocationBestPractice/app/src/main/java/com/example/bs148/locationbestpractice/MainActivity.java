package com.example.bs148.locationbestpractice;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView txtLattitude, txtLongitude, txtAccuracy, txtProvider;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLattitude = (TextView) findViewById(R.id.txtLattitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtAccuracy = (TextView) findViewById(R.id.txtAccuracy);
        txtProvider = (TextView) findViewById(R.id.txtProvider);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check gps is enabled or not
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Toast.makeText(getApplicationContext(), "Please Consider enabling gps", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    private void setup() {
        // 2 ta thikai data nibo then 2 tar modde jeita best oita nibo. jodi kono ta null ase tahole oita bad dia dibo
        Location gpsLocation = null;
        Location networkLocation = null;
        // age remove kore nilam
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(listener);
        gpsLocation = requestUpdateFromProvider(LocationManager.GPS_PROVIDER);
        networkLocation = requestUpdateFromProvider(LocationManager.NETWORK_PROVIDER);

        if (gpsLocation != null && networkLocation != null) {
            //find better one
            Location myLocation = getBetterLocation(gpsLocation, networkLocation);
            setValuesInUI(myLocation);
        } else if (gpsLocation != null) {
            setValuesInUI(gpsLocation);
            //use gps location
        } else if (networkLocation != null) {
            //use network location
            setValuesInUI(networkLocation);
        } else {
            //No Data
            txtLattitude.setText("No Data Available");
            txtLongitude.setText("No Data Available");
            txtAccuracy.setText("No Data Available");
            txtProvider.setText("No Data Available");
        }
    }

    private void setValuesInUI(Location location) {
        txtLattitude.setText("Lattitude: " + location.getLatitude());
        txtLongitude.setText("Longitude: " + location.getLongitude());
        txtAccuracy.setText("Accuracy: " + location.getAccuracy());
        txtProvider.setText("Provider: " + location.getProvider());
    }

    private Location getBetterLocation(Location newLocation, Location currentBestLocation) {
        //return best one
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return newLocation;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > 60000;//1 min
        boolean isSignificantlyOlder = timeDelta < 60000;
        boolean isNewer = timeDelta > 0;

        // If it's been more than one minutes since the current location, use
        // the new location
        // because the user has likely moved.
        if (isSignificantlyNewer) {
            return newLocation;
            // If the new location is more than one minutes older, it must be
            // worse
        } else if (isSignificantlyOlder) {
            return currentBestLocation;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
                .getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;

        // Determine location quality using a combination of timeliness and
        // accuracy
        if (isMoreAccurate) {
            return newLocation;
        } else if (isNewer && !isLessAccurate) {
            return newLocation;
        }
        return currentBestLocation;
    }

    private Location requestUpdateFromProvider(String provider) {
        Location location = null;
        //jodi enable thake then amra req korbo.
        if (locationManager.isProviderEnabled(provider)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            locationManager.requestLocationUpdates(provider, 1000, 10, listener);
            location = locationManager.getLastKnownLocation(provider);
        } else {
            Toast.makeText(getApplicationContext(), provider + " is not enabled", Toast.LENGTH_LONG).show();
        }
        return location;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //remove all update listner
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(listener);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setValuesInUI(location);
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
    };

}
