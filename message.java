package com.example.trackme.tm.trackme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.telephony.SmsManager;
import android.widget.Toast;

public class message extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }
    public void send(View view){
        EditText phone = findViewById(R.id.e1);
        EditText mess = findViewById(R.id.e2);
        Button se = findViewById(R.id.b1);
        String nu=phone.getText().toString().trim();
        String me=mess.getText().toString().trim();
        if(TextUtils.isDigitsOnly(nu)){
            SmsManager sm= SmsManager.getDefault();
            sm.sendTextMessage(nu,null,me,null,null);
            Toast.makeText(this,"Message send sucessfully",Toast.LENGTH_LONG).show();
        }
    }
}
