package com.example.chennasreenu.findintruderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by chennasreenu on 3/31/2017.
 */

public class Database {
    private SharedPreferences sharedPreferences;
    private Context Db_Context;
    private String DB_NAME="FIND_INTRUDER_DATA";
    Database(Context context){
        Db_Context=context;
        sharedPreferences=Db_Context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
    }
    public boolean isRegistered(){
        if(sharedPreferences.getBoolean("registered",false)){
            return true;
        }
        return false;
    }
    public void storeDetails(String name,String phone,String email){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("name",name);
        editor.putString("phone",phone);
        editor.putString("email",email);
        editor.putBoolean("registered",true);
        editor.commit();
        Toast.makeText(Db_Context,"Details saved successfully!!",Toast.LENGTH_LONG).show();

    }

  /*  public void storeAddress(String address){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("address",address);
        editor.commit();
    }
    */



    public String getName(){
        return sharedPreferences.getString("name",null);
    }

    public String getPhone(){
        return sharedPreferences.getString("phone",null);
    }

    public String getEmail(){
        return sharedPreferences.getString("email",null);
    }


  /*  public String getAddress(){
        return sharedPreferences.getString("address",null);
    }
    */


}
