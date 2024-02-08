package com.cwtstudio.flashligtapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    private MaterialSwitch btnSwitch;
    private TextView txtStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSwitch = findViewById(R.id.btnSwitch);
        txtStates = findViewById(R.id.txtState);

        checkCamPermission();

        btnSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            manageSwitchStates(isChecked);


        });


    }

    private void manageSwitchStates(boolean isFlashOn) {
        try {
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            String cameraID = cameraManager.getCameraIdList()[0].toString();
            cameraManager.setTorchMode(cameraID, isFlashOn);
            if (isFlashOn) {
                txtStates.setText("TorchLight : On");
            } else {
                txtStates.setText("TorchLight : Off");
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


    }

    private void checkCamPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();


    }
}