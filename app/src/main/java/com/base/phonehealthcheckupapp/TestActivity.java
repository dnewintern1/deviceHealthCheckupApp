package com.base.phonehealthcheckupapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageCapture;
import androidx.core.content.ContextCompat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.core.Preview;
import androidx.camera.core.CameraSelector;
import android.util.Log;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import com.base.phonehealthcheckupapp.databinding.ActivityTestBinding;
import java.nio.ByteBuffer;


public class TestActivity extends AppCompatActivity {
     boolean isUsingFrontCamera = false;
    private ActivityTestBinding binding;
    private ExecutorService cameraExecutor;
    private ImageCapture imageCapture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        toggleCamera();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backvam();

            }
        }, 3000);





        cameraExecutor = Executors.newSingleThreadExecutor();
    }





    private void toggleCamera() {
        ProcessCameraProvider.getInstance(this).addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = ProcessCameraProvider.getInstance(this).get();
                Preview preview = new Preview.Builder().build();

                CameraSelector cameraSelector;
                cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();


                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();
                imageCapture = new ImageCapture.Builder().build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis, imageCapture);
                preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());

                takePhoto();
                isUsingFrontCamera = true;
            } catch (Exception e) {
                Log.e("CameraXApp", "Failed to start camera", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void backvam() {
        ProcessCameraProvider.getInstance(this).addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = ProcessCameraProvider.getInstance(this).get();
                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector;
                    cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().build();
                imageCapture = new ImageCapture.Builder().build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis, imageCapture);
                preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());

                takePhoto();

            } catch (Exception e) {
                Log.e("CameraXApp", "Failed to start camera", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }



    private void takePhoto() {
        if (imageCapture == null) {
            return;
        }

        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(ImageProxy image) {

                super.onCaptureSuccess(image);


                // Extract image data and create a Bitmap
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                Bitmap photoBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                // Display the captured image as a thumbnail in an ImageView
                ImageView thumbnailImageView = findViewById(R.id.thumbnailImageView); // Replace with the ID of your ImageView
                thumbnailImageView.setImageBitmap(photoBitmap);




                if(isUsingFrontCamera){
                    thumbnailImageView.setVisibility(View.INVISIBLE);
                    binding.viewFinder.setVisibility(View.VISIBLE);
                }
                else {
                    binding.viewFinder.setVisibility(View.INVISIBLE);
                    thumbnailImageView.setVisibility(View.VISIBLE);


                }

                // Close the ImageProxy
                image.close();
            //    reconfigureImageCapture();
            }

//            private void initializeImageCapture() {
//                imageCapture = new ImageCapture.Builder().build();
//            }
//
//            private void reconfigureImageCapture() {
//                initializeImageCapture(); // Recreate and reconfigure the imageCapture instance
//            }





            @Override
            public void onError(ImageCaptureException exception) {
                Log.e("CameraXApp", "Photo capture failed", exception);
            }
        });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}




















