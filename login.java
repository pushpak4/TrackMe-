package com.example.trackme.tm.trackme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void b1(View view) {
        EditText email = findViewById(R.id.em);
        EditText pass = findViewById(R.id.ps);
        db data = new db(this);
        SQLiteDatabase database = data.getReadableDatabase();
        String[] colu = {"email", "password"};
        String[] cv = {email.getText().toString(), pass.getText().toString()};
        Cursor cu = database.query("User", colu, " email = ? AND password = ? ", cv, null, null, null);
        if (cu != null) {
            if(cu.moveToFirst()) {
                Toast.makeText(this, "Login Sucessfully", Toast.LENGTH_LONG).show();
                Intent in= new Intent(this, accelerometer.class);
                startActivity(in);
            }
        }
        else {
            Toast.makeText(this, "Unsucessfull", Toast.LENGTH_LONG).show();
        }
    }

    public void b2(View view) {
        Intent inte = new Intent(this, register.class);
        startActivity(inte);
    }
}
