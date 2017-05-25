package com.example.chennasreenu.findintruderapp;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by chennasreenu on 3/31/2017.
 */

public class Permissions {
    private Context Permissions_Context;

    Permissions(Context context){
        Permissions_Context=context;
    }

    public boolean check_device_admin_permissions(){
        ComponentName componentName=new ComponentName(Permissions_Context,Administration_Receiver.class);
        DevicePolicyManager devicePolicyManager=(DevicePolicyManager)Permissions_Context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if(devicePolicyManager.isAdminActive(componentName)){
            return true;
        }
        return false;
    }

    public boolean check_location_permissions(){
        int result_location= ContextCompat.checkSelfPermission(Permissions_Context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(result_location == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

  /*  public boolean check_contacts_permissions(){
        int result_contacts=ContextCompat.checkSelfPermission(Permissions_Context,Manifest.permission.READ_CONTACTS);
        if (result_contacts == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }*/

    public boolean check_internet_permissions(){
        int result_internet=ContextCompat.checkSelfPermission(Permissions_Context,Manifest.permission.INTERNET);
        if (result_internet ==PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    public boolean check_storage_permissions(){
        int result_readstorage=ContextCompat.checkSelfPermission(Permissions_Context,Manifest.permission.READ_EXTERNAL_STORAGE);
        int result_writestorage=ContextCompat.checkSelfPermission(Permissions_Context,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result_readstorage==PackageManager.PERMISSION_GRANTED && result_writestorage==PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }
    public boolean check_camera_permissions(){
        int result_camera=ContextCompat.checkSelfPermission(Permissions_Context,Manifest.permission.CAMERA);
        if(result_camera==PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }
    public boolean check_sms_permissions(){
        int result_sms=ContextCompat.checkSelfPermission(Permissions_Context,Manifest.permission.SEND_SMS);
        if(result_sms==PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }
}
