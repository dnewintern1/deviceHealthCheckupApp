package com.base.phonehealthcheckupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;



import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MicTestingActivity extends AppCompatActivity {

    private Button startRecordingButton;
    private Button stopRecordingButton;
    private AudioRecord audioRecorder;

    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private AudioTrack audioPlayer;

    private boolean isRecording = false;

    private static final int RECORDING_DURATION_MS = 3000; // 3 seconds
    private String audioFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic_testing);

        startRecordingButton = findViewById(R.id.startRecordingButton);
        stopRecordingButton = findViewById(R.id.stopRecordingButton);

        startRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        stopRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_audio.pcm";
    }

    @SuppressLint("MissingPermission")
    private void startRecording() {
        if (!isRecording) {
            int minBufferSize = AudioRecord.getMinBufferSize(44100, MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT);
            audioRecorder = new AudioRecord(
                    MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize
            );

            int bufferSize = 4096;
            byte[] audioData = new byte[bufferSize];

            audioRecorder.startRecording();
            audioPlayer = new AudioTrack(
                    android.media.AudioManager.STREAM_MUSIC, 44100,
                    AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize,
                    AudioTrack.MODE_STREAM
            );
            audioPlayer.play();

            isRecording = true;
            startRecordingButton.setEnabled(false);
            stopRecordingButton.setEnabled(true);

            while (isRecording) {
                int bytesRead = audioRecorder.read(audioData, 0, bufferSize);
                if (bytesRead > 0) {
                    audioPlayer.write(audioData, 0, bytesRead);
                }
            }
        }
    }

    private void stopRecording() {
        if (isRecording) {
            audioPlayer.stop();
            audioPlayer.release();

            isRecording = false;
            startRecordingButton.setEnabled(true);
            stopRecordingButton.setEnabled(false);
        }
    }
}