package com.example.ui403;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    //define button variables
    private Button speed;
    private Button angle;
    private Button efficiency;
    private Button hit_location;
    private Button calibration;
    private Button blue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dashboard integration

        //action of the speed button to navigate to a different page

        speed = findViewById((R.id.speed));
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBatSwing();
            }
        });

        //action of the angle button to navigate to a different page
        angle = findViewById((R.id.angle));
        angle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSwingAngle();
            }
        });

        //action of the efficiency button to navigate to a different page
        efficiency = findViewById((R.id.efficiency));
        efficiency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEfficiency();
            }
        });

        //action of the location button to navigate to a different page
        hit_location = findViewById((R.id.location));
        hit_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLocation();
            }
        });

        //action of the calibration button to navigate to a different page
        calibration = findViewById((R.id.calibration));
        calibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalibration();
            }
        });

        //action of the bluetooth button to navigate to a different page
        blue = findViewById((R.id.blue));
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBTConnection();
            }
        });


    }

    public void openBatSwing() {
        //sending data to individual pages of dashboard
        double spd = 90.0; //will later be defined by the input received

        Intent intent = new Intent(this, BatSwing.class);
        intent.putExtra("key1", spd);
        startActivity(intent);
    }
    public void openSwingAngle() {
        double angle = 32.0;
        Intent intent = new Intent(this, SwingAngle.class);
        intent.putExtra("key2", angle);
        startActivity(intent);
    }

    public void openEfficiency() {
        double effic = 78.9;
        Intent intent = new Intent(this, Efficiency.class);
        intent.putExtra("key4", effic);
        startActivity(intent);
    }

    public void openLocation() {
        int location = 3;
        Intent intent = new Intent(this, HitLocation.class);
        intent.putExtra("key3", location);
        startActivity(intent);
    }

    public void openCalibration() {
        Intent intent = new Intent(this, Calibration.class);
        startActivity(intent);
    }

    public void openBTConnection() {
        Intent intent = new Intent(this, BTConnection.class);
        startActivity(intent);
    }


}