package com.example.bs148.androidmaputilsdemo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by BS148 on 8/11/2016.
 */
public class ClusterMarkerLocation implements ClusterItem {
    private final LatLng mPosition;

    public ClusterMarkerLocation(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

}
