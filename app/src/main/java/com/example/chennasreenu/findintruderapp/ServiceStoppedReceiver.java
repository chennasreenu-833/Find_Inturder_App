package com.example.chennasreenu.findintruderapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by chennasreenu on 4/10/2017.
 */

public class ServiceStoppedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent restart_Service_intent= new Intent(context,IntruderService.class);
        context.startService(restart_Service_intent);
        Toast.makeText(context,"Service Restart initiated",Toast.LENGTH_SHORT).show();
    }
}
