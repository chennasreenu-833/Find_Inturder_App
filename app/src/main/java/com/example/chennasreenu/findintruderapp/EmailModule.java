package com.example.chennasreenu.findintruderapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by chennasreenu on 4/5/2017.
 */

public class EmailModule extends AsyncTask<Void,Void,Void> {
    Context context_emailModule;
    private Session session;
    Database database_obj;
    private String name,To_email,body_of_letter="",location_details;
    private String subject="ALERT_FIND_INTRUDER_APP";

    EmailModule(Context context,String address){
        context_emailModule=context;
        location_details=address;
        load_data();
        build_letter_body();
    }

    private void build_letter_body(){
        body_of_letter+="Hi "+name+","+"\n\n";
        body_of_letter+="\t Someone tried to unlock your mobile. ";
        body_of_letter+=location_details+". "+"\n\n";
        body_of_letter+="\t Please find the attachment below for the image of intruder. \n\n";
        body_of_letter+="Regards\n";
        body_of_letter+="Find Intruder Application";
    }

    private void load_data(){
        database_obj=new Database(context_emailModule);
        name=database_obj.getName();
        To_email=database_obj.getEmail();
    }


    @Override
    protected Void doInBackground(Void... params) {

        Properties props = new Properties();

        //Configuring properties for gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Configuration.EMAIL, Configuration.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(Configuration.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(To_email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(body_of_letter);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            String file = "intruder.jpg";
            String fileName = baseDir+ File.separator+file;
            DataSource source = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            mm.setContent(multipart);

            //Sending email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
