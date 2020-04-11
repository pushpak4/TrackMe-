package com.example.trackme.tm.trackme;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class accelerometer extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "accelerometer";
   private SensorManager sm;
   Sensor acc;
   TextView t2,t4,t6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        TextView x = findViewById(R.id.t2);
        TextView y = findViewById(R.id.t4);
        TextView z = findViewById(R.id.t6);
        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sm= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc= sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener( accelerometer.this, acc, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer Listener");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: x" + event.values[0] + "y" + event.values[1] + "z" + event.values[2]);
        t2.setText("x-axis" +event.values[0]);
        t4.setText("y-axis" +event.values[1]);
        t6.setText("z-axis" +event.values[2]);
        String x =t2.getText().toString();
        String y =t4.getText().toString();
        String z =t6.getText().toString();

        db data= new db(this);
        SQLiteDatabase database = data.getWritableDatabase();
        ContentValues val= new ContentValues();
        val.put("x",x);
        val.put("y",y);
        val.put("z",z);
        database.insert("Readings", null, val);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
