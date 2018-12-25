package com.example.dell.light;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Parameter;

public class MainActivity extends AppCompatActivity {

    private android.hardware.Camera camera;
    ToggleButton button;
    private boolean onflash;
    private boolean hasFlash;
    private android.hardware.Camera.Parameters parameters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ToggleButton) findViewById(R.id.toggleButton2);



        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash){
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
            dialog.setTitle("Alert");
            dialog.setMessage("No flash light in your device !Sorry");
            dialog.setButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.show();
            return;
        }


         getCamera();

        // displaying button image
        toggleButtonImage();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (((ToggleButton) v).isChecked() && !onflash){
                       flashOn();
               }
               else {
                   flashOff();

               }
            }
        });



    }

    private void getCamera() {
        if (camera == null) {
            try {
                camera = android.hardware.Camera.open();
                parameters = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }

    private void toggleButtonImage() {
    }
    private void flashOn(){
        parameters = camera.getParameters();
        parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        onflash=true;
        Toast.makeText(getApplicationContext(),"Flash On" , Toast.LENGTH_SHORT).show();
        button.setBackgroundColor(Color.BLUE);
    }
    private void flashOff(){
        parameters = camera.getParameters();
        parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
        onflash=false;
        Toast.makeText(getApplicationContext(),"Flash Off",Toast.LENGTH_SHORT).show();
        button.setBackgroundColor(Color.WHITE);
    }
}
