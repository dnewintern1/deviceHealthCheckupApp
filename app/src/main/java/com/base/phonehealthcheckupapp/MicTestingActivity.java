package com.base.phonehealthcheckupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;


import android.os.Environment;
import android.view.View;


import android.widget.Toast;

import java.io.File;

public class MicTestingActivity extends AppCompatActivity {

    MediaRecorder mediaRecorder;

    MediaPlayer mediaPlayer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic_testing);


        if(isMicrophonePresent()){
            Toast.makeText(MicTestingActivity.this,"Mic Present",Toast.LENGTH_SHORT).show();
        }





    }

    public void btnRecordPressed(View v) {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // Choose one output format
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(getRecordingFilePath());
            mediaRecorder.prepare();
            mediaRecorder.start();

            Toast.makeText(MicTestingActivity.this, "Recording is started", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnStopPressed(View v) {
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            Toast.makeText(MicTestingActivity.this, "Recording is stopped", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnPlayPressed(View v) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getRecordingFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(MicTestingActivity.this, "Recording is Playing", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRecordingFilePath() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "testRecordingFiles.3gp"); // Use the correct file extension
        return file.getPath();
    }

    private Boolean isMicrophonePresent() {
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }
}


