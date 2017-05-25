package com.example.chennasreenu.findintruderapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    private EditText var_edtxt_name;
    private EditText var_edtxt_phone;
    private EditText var_edtxt_email;
    private Button var_button_register;
    Handler handler;
    private String var_str_name,var_str_phone,var_str_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        load_all_items();
        register_for_clicks();
    }

    private void load_all_items(){
        handler=new Handler();
        var_edtxt_name=(EditText)findViewById(R.id.edittext_register_name);
        var_edtxt_phone=(EditText)findViewById(R.id.edittext_register_phone);
        var_edtxt_email=(EditText)findViewById(R.id.edittext_register_email);
        var_button_register=(Button)findViewById(R.id.button_register_register);
    }

    private void register_for_clicks(){
        var_button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_values();
                boolean is_valid=validation_check(var_str_name,var_str_phone,var_str_email);
                if(is_valid) {

                    Database database_obj=new Database(Registration.this.getApplicationContext());
                    database_obj.storeDetails(var_str_name,var_str_phone,var_str_email);
                    Intent goto_home_intent = new Intent(Registration.this, Homescreen.class);
                    startActivity(goto_home_intent);
                }
            }
        });
    }
    public void get_values(){
        var_str_name=var_edtxt_name.getText().toString();
        var_str_phone=var_edtxt_phone.getText().toString();
        var_str_email=var_edtxt_email.getText().toString();
    }

    public boolean validation_check(String name,String phone,String email){
        boolean result=true;
        name=name.trim();
        phone=phone.trim();
        email=email.trim();
        String toast_msg="";
        if(name.length()==0){
            toast_msg+="Invalid name";
            var_edtxt_name.setError("Invalid name (empty)");
            result=false;
        }
        if(phone.length()!=10){
                toast_msg += ",Invalid phone number";
                var_edtxt_phone.setError("Invalid phone number don't add country code");
                result = false;
        }
        if(!email.contains("@")||!email.contains(".")||email.length()<6){
            toast_msg+=",Invalid Email.";

            var_edtxt_email.setError("Invalid email");
            result=false;
        }
        if(!result){
            Toast.makeText(Registration.this.getApplicationContext(), toast_msg, Toast.LENGTH_LONG).show();
        }

        return result;
    }

}
