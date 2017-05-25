package com.example.chennasreenu.findintruderapp;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by chennasreenu on 4/1/2017.
 */

public class Administration_Receiver extends DeviceAdminReceiver {
    private static int wrong_pwd_count=0;
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        ComponentName componentName=new ComponentName(context,Administration_Receiver.class);
        DevicePolicyManager devicePolicyManager=(DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        devicePolicyManager.setPasswordMinimumLength(componentName,4);
        Alerts alerts=new Alerts(context);
        alerts.setNotification();
        onPasswordChanged(context,intent);
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        super.onPasswordChanged(context, intent);
        DevicePolicyManager devicePolicyManager=(DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if(devicePolicyManager.isActivePasswordSufficient()){
            Toast.makeText(context,R.string.password_sufficient,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,R.string.password_not_sufficient,Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        super.onPasswordSucceeded(context, intent);
        wrong_pwd_count=0;
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        super.onPasswordFailed(context, intent);
        wrong_pwd_count++;
        if(wrong_pwd_count==4){
            Intent camera_intent=new Intent(context,CameraModule.class);
            camera_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(camera_intent);
        }
        if(wrong_pwd_count==5){
            wrong_pwd_count=0;
           inform_details(context);
        }
    }

    private void inform_details(Context inform_context){
        LocationModule locationModule_obj=new LocationModule(inform_context);

        boolean can_track_location=locationModule_obj.returnLocationStatus();
        if(can_track_location){
            locationModule_obj.execute();
        }else{
            SMSModule smsModule_obj=new SMSModule(inform_context);
            smsModule_obj.sendSMS_Without_Location();
            String msg="Sorry Cant find location details.";
            EmailModule emailModule_obj=new EmailModule(inform_context,msg);
            emailModule_obj.execute();
        }
    }
}
