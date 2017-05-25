package com.example.chennasreenu.findintruderapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

public class IntruderService extends Service {
    public IntruderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                while (true){

                }
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();

       return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent call_to_restart = new Intent("com.android.findintruder.serviceRestart");
        sendBroadcast(call_to_restart);
        super.onDestroy();
    }
}
