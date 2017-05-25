package com.example.chennasreenu.findintruderapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Homescreen extends AppCompatActivity {
    private Button var_button_change;
    private TextView var_textview_name,var_textview_phone,var_textview_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        load_all_items();
        register_for_clicks();
        set_all_items();
    }

    private void load_all_items(){
        var_button_change=(Button)findViewById(R.id.button_home_change);
        var_textview_name=(TextView)findViewById(R.id.textview_home_name);
        var_textview_phone=(TextView)findViewById(R.id.textview_home_phone);
        var_textview_email=(TextView)findViewById(R.id.textview_home_email);
    }

    private void register_for_clicks(){
        var_button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_registration=new Intent(Homescreen.this,Registration.class);
                startActivity(goto_registration);
            }
        });
    }
    private void set_all_items(){
        Database database=new Database(Homescreen.this);
        var_textview_name.setText(database.getName());
        var_textview_phone.setText(database.getPhone());
        var_textview_email.setText(database.getEmail());
    }

    @Override
    public void onBackPressed() {
      /*  Intent gotowelcome=new Intent(Homescreen.this,Welcome.class);
        startActivity(gotowelcome);*/
        showAlertDialog();
    }

    public void showAlertDialog(){
        final AlertDialog.Builder alertdialogue=new AlertDialog.Builder(Homescreen.this,R.style.DialogTheme);
        alertdialogue.setTitle("ALERT");
        alertdialogue.setMessage("Do you want to exit?");
        alertdialogue.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alertdialogue.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertdialogue.show();
    }
}
