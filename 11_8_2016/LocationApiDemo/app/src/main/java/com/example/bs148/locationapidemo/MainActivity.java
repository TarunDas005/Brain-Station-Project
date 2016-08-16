package com.example.bs148.locationapidemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView txtOutput;
    private LocationManager locationManager;
    String bestProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtOutput = (TextView) findViewById(R.id.txtOutput);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();
        for (String provider : providers) {
            txtOutput.append("\nProvider: " + provider);
        }
        // jei network gula enable state a ase tader modde best provider select korte chaile second parameter true hobe
        // first parameter holo criteria ami kiser respect a best ber korbo
        //1. Accuracy 2. Battery life 3. Cost(je provider thika data nile tk lagbe na) 4. Oltitude required 5. Speed required
        //cost allow na korle network provider select korbe na
        // OnResume ar somoi amra listen kora suru koro
        // OnPause a jabe tokon listen kora bondho kore dibo
        Criteria criteria = new Criteria();
        //criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        //criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setPowerRequirement(Criteria.ACCURACY_LOW);
        criteria.setCostAllowed(true);
        bestProvider = locationManager.getBestProvider(criteria, true);
        txtOutput.append("\nBest Provider: " + bestProvider);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //last data paoar jnno
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if(location!=null){
            txtOutput.append("\nLast Known: "+location.getLatitude()+" "+location.getLongitude());
        }else{
            txtOutput.append("\nLocation data not available for best provider");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 1st parameter kon provider ar kas theke listen korbe
        //2nd parameter hosse koto second por por poll korte thakbe. jodi 0 dei tahole continuously listen korte thakbe
        // battery besi consuming hobe. 1000 means 1 s por por poll korbe
        // 3rd parameter koto tuku distance change hoile amader onLocationChanged call hobe. here 10 means 10 meter change hoilei on location changed call hobe
        // 4th parameter hosse listner. that's mean current activity
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(bestProvider, 1000, 10, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // jokon onpause call hobe tokoni liseten kora bondho kore dibe
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this,"Location Changed: "+location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_LONG).show();
        txtOutput.append("\nLocation Changed: "+location.getLatitude()+" "+location.getLongitude()+" Accuracy: "+location.getAccuracy());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this,"Provider Enabled "+provider,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this,"Provider Disabled "+provider,Toast.LENGTH_LONG).show();
    }
}
