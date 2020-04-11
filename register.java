package com.example.trackme.tm.trackme;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void b1(View view){
        EditText name= findViewById(R.id.na);
        EditText code= findViewById(R.id.co);
        EditText email= findViewById(R.id.em);
        EditText pass= findViewById(R.id.ps);
        EditText phone= findViewById(R.id.ph);

        String nam =name.getText().toString();
        String cd =code.getText().toString();
        String ema =email.getText().toString();
        String pss =pass.getText().toString();
        String pho =phone.getText().toString();

        db data= new db(this);
        SQLiteDatabase database = data.getWritableDatabase();
        ContentValues val= new ContentValues();
                val.put("name",nam);
                val.put("code",cd);
                val.put("email",ema);
                val.put("password",pss);
                val.put("phone",pho);
        database.insert("User", null, val);
        Toast.makeText(this,"Data Inserted", Toast.LENGTH_LONG).show();
    }
}
