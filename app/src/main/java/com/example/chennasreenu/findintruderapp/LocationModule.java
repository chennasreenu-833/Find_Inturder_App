package com.example.chennasreenu.findintruderapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

/**
 * Created by chennasreenu on 4/4/2017.
 */

public class LocationModule extends AsyncTask<Void, Void, Void> implements LocationListener {
    Context location_module_context;
    LocationManager locationManager;
    private double latitude_location_module=0,longitude_location_module=0;
    private boolean GPS_status, Network_status;
    private boolean can_get_location;

    LocationModule(Context context) {
        location_module_context = context;
        getLocationStatus();
    }

    private void getLocationStatus() {
        locationManager = (LocationManager) location_module_context.getSystemService(Context.LOCATION_SERVICE);
        GPS_status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Network_status = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!GPS_status && !Network_status) {
            can_get_location = false;
        } else {
            can_get_location = true;
        }
    }

    public boolean returnLocationStatus() {
        return can_get_location;
    }

    @Override
    protected void onPreExecute() {
        if (locationManager != null) {
            Location gps_location, network_location;
            if (ActivityCompat.checkSelfPermission(location_module_context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(location_module_context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gps_location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if((gps_location != null) && (gps_location.getTime() > System.currentTimeMillis()-2*60*1000)){
                latitude_location_module=gps_location.getLatitude();
                longitude_location_module=gps_location.getLongitude();
            }else if(network_location!=null&&network_location.getTime()>System.currentTimeMillis()-2*60*1000){
                latitude_location_module=network_location.getLatitude();
                longitude_location_module=network_location.getLongitude();
            }
            else if(Network_status){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,LocationModule.this);
            }
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,LocationModule.this);
            }
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        while(this.latitude_location_module==0){

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        GeocoderAddress geocoderAddress_obj=new GeocoderAddress(location_module_context);
        ArrayList<Double> coordinates=new ArrayList<Double>();
        coordinates.add(latitude_location_module);
        coordinates.add(longitude_location_module);
        geocoderAddress_obj.execute(coordinates);
        stopUsingGPS();

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            latitude_location_module=location.getLatitude();
            longitude_location_module=location.getLongitude();
        }
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

    private void stopUsingGPS(){
        if(locationManager!=null){
            locationManager.removeUpdates(LocationModule.this);
        }
    }
}
