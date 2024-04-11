package com.cwtstudio.flashlightappjava.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cwtstudio.flashlightappjava.R;
import com.cwtstudio.flashlightappjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private boolean isOn = false;
    private CameraManager cameraManager;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init camera manager
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        checkPermission();


        binding.imgTorchState.setOnClickListener(v -> {
            if (!isOn) {
                try {
                    id = cameraManager.getCameraIdList()[0];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cameraManager.setTorchMode(id, true);
                    }
                    isOn = true;
                    binding.imgTorchState.setImageResource(R.drawable.torch_on);
                    binding.imgTorchBodyOff.setVisibility(View.GONE);
                    binding.imgTorchBodyOn.setVisibility(View.VISIBLE);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                    checkPermission();
                }


            } else {
                try {
                    id = cameraManager.getCameraIdList()[0];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cameraManager.setTorchMode(id, false);
                    }
                    isOn = false;
                    binding.imgTorchState.setImageResource(R.drawable.torch_off);
                    binding.imgTorchBodyOff.setVisibility(View.VISIBLE);
                    binding.imgTorchBodyOn.setVisibility(View.GONE);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                    checkPermission();
                }
            }
        });


    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //Request permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 10);

        } else {
            //Already granted
        }
    }


}