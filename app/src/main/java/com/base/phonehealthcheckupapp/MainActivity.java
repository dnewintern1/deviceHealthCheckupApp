package com.base.phonehealthcheckupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {




    String[] permissions = {
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.BLUETOOTH, // Assuming this is the correct Bluetooth permission
            android.Manifest.permission.BLUETOOTH_ADMIN, // Assuming this is the correct Bluetooth permission
            android.Manifest.permission.BLUETOOTH_CONNECT, // Assuming this is the correct Bluetooth permission
            android.Manifest.permission.BODY_SENSORS // Add this if you intend to request BODY_SENSORS permission
    };

    int requestCode = 123; // You can choose any unique request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int contactsPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS);
        int locationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int microphonePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO);
        int storagePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int blueToothPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);
        int accelerometerPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS);
        int gyroscopePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS);
        ActivityCompat.requestPermissions(this, permissions, requestCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123) { // Check if this is the result of your permission request
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, you can proceed with the corresponding functionality
                } else {
                    // Permission denied, handle accordingly (e.g., show a message to the user)
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

