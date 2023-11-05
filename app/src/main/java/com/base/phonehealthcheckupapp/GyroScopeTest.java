package com.base.phonehealthcheckupapp;

import androidx.appcompat.app.AppCompatActivity;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class GyroScopeTest extends AppCompatActivity implements  SensorEventListener{


    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro_scope_test);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Check if the gyroscope sensor is available on the device
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscopeSensor != null) {
            // Register the gyroscope sensor listener
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, "Gyroscope sensor working great!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Gyroscope sensor is not available on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Handle gyroscope sensor data here
            float gyroX = event.values[0];
            float gyroY = event.values[1];
            float gyroZ = event.values[2];
            // You can add your logic to check gyroscope values if needed.
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the sensor listener when the activity is destroyed
        sensorManager.unregisterListener(this);
    }
}

