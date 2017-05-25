package com.example.chennasreenu.findintruderapp;

import android.content.Context;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by chennasreenu on 4/4/2017.
 */

public class SMSModule {
    private Context SMS_Context;
    private SmsManager smsManager;
    private String phone;
    private Database database_obj;
    private String message_for_loc,message_for_non_loc;

    SMSModule(Context context){
        SMS_Context=context;
        database_obj=new Database(SMS_Context);
        phone=database_obj.getPhone();
    }

    public void sendSMS_Without_Location() {
        message_for_non_loc = "Someone tried to unlock ur mobile. Sorry cant find location";
        try {
            smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message_for_non_loc, null, null);
           /* Alerts alerts_obj=new Alerts(SMS_Context);
            alerts_obj.sms_sent_notification();*/
        } catch (Exception e) {
            Alerts alerts_obj=new Alerts(SMS_Context);
            alerts_obj.sms_failed_notification();
        }
    }
    public void sendSMS_With_Location(String address) {
        message_for_loc = "Someone tried to unlock ur mobile.";
        message_for_loc += address;
        try {
            try {
                ArrayList<String> parts = null;
                parts = smsManager.divideMessage(message_for_loc);
                smsManager = SmsManager.getDefault();
                smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
               /* Alerts alerts_obj=new Alerts(SMS_Context);
                alerts_obj.sms_sent_notification();*/
            } catch (Exception e) {
                smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, message_for_loc, null, null);
               /* Alerts alerts_obj=new Alerts(SMS_Context);
                alerts_obj.sms_sent_notification();*/
            }
        }catch (Exception e){
            Alerts alerts_obj=new Alerts(SMS_Context);
            alerts_obj.sms_failed_notification();
        }
    }
}
