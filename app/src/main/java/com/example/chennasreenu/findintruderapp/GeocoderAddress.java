package com.example.chennasreenu.findintruderapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by chennasreenu on 4/4/2017.
 */

public class GeocoderAddress extends AsyncTask<ArrayList<Double>,Void,String> {
    Context geocoder_context;
    double latitude_geocoder,longitude_geocoder;
    Database database;
    GeocoderAddress(Context context){

        geocoder_context=context;
    }
    @Override
    protected String doInBackground(ArrayList<Double>... params) {
        Geocoder geocoder;
        String address=" Your Mobile is at Location:(lat,long) ";
        String address_msg;
        ArrayList<Double>coordinates=params[0];
        latitude_geocoder=coordinates.get(0);
        longitude_geocoder=coordinates.get(1);
        address+=latitude_geocoder+","+longitude_geocoder+". ";
        address_msg=address;
        List<Address> addressList;
        geocoder=new Geocoder(geocoder_context, Locale.getDefault());
        try {
            addressList=geocoder.getFromLocation(latitude_geocoder,longitude_geocoder,1);
            if(addressList!=null){
                String area = addressList.get(0).getAddressLine(0);
                String city = addressList.get(0).getLocality();
                String district = addressList.get(0).getSubAdminArea();
                String state = addressList.get(0).getAdminArea();
                String country = addressList.get(0).getCountryName();
                String postalCode = addressList.get(0).getPostalCode();
              //  address+=area+","+city+","+district+","+state+","+country+","+postalCode;
                address_msg+=area+","+city+","+postalCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address_msg;
    }

    @Override
    protected void onPostExecute(String s) {
        SMSModule smsModule_obj=new SMSModule(geocoder_context);
        smsModule_obj.sendSMS_With_Location(s);
        EmailModule emailModule_obj=new EmailModule(geocoder_context,s);
        emailModule_obj.execute();
        super.onPostExecute(s);
    }
}
