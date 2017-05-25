package com.example.chennasreenu.findintruderapp;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {
    Permissions permissions_obj;
    Database database_obj;
    ComponentName componentName;
    private Button var_but_proceed;
    int permissions_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent service_intent=new Intent(this,IntruderService.class);
        startService(service_intent);
        permissions_obj=new Permissions(Welcome.this);
        database_obj=new Database(Welcome.this);
        boolean permission_status=check_for_permissions();
        if(!permission_status){
            request_for_permissions();
        }else{
            Toast.makeText(Welcome.this,"Permissions already set",Toast.LENGTH_SHORT).show();
        }
        load_all_items();
        register_for_clicks();
    }

    //to load all components in layout
    private void load_all_items(){
        var_but_proceed=(Button)findViewById(R.id.welcome_but_proceed);
    }

    // register for click listeners
    private void register_for_clicks(){
        var_but_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permissions_obj.check_device_admin_permissions()){
                    if(database_obj.isRegistered()){
                        Alerts alerts_obj=new Alerts(Welcome.this);
                        alerts_obj.setNotification();
                        Intent goto_home_intent=new Intent(Welcome.this,Homescreen.class);
                        startActivity(goto_home_intent);
                    }
                    else{
                        Alerts alerts_obj=new Alerts(Welcome.this);
                        alerts_obj.setNotification();
                        Intent goto_registration=new Intent(Welcome.this,Registration.class);
                        startActivity(goto_registration);
                    }
                }
                else{
                    componentName=new ComponentName(Welcome.this,Administration_Receiver.class);
                    Intent goto_adminopt_intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    goto_adminopt_intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
                    goto_adminopt_intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"To Access LockScreen Only");
                    startActivity(goto_adminopt_intent);
                }
            }
        });
    }

    //to check for permission status
    public boolean check_for_permissions(){
        if(permissions_obj.check_location_permissions()&&permissions_obj.check_internet_permissions()
                &&permissions_obj.check_storage_permissions()&&permissions_obj.check_camera_permissions()&&permissions_obj.check_sms_permissions()){
            return true;
        }
        return false;
    }

    //to request permissions
    public void request_for_permissions(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET,
        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.SEND_SMS},permissions_code);
    }

    //to update permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==permissions_code){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Thankyou all permissions set!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    //to exit from app
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
