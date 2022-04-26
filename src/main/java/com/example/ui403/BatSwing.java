package com.example.ui403;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BatSwing extends AppCompatActivity {
    private Button dashboard;
    TextView mSwingSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_swing);

        dashboard = findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboard();
            }
        });

        mSwingSpeed = findViewById(R.id.swingspeed);
        //getting data from main activity
        Intent myLocalintent = getIntent();
        double speedSwing = myLocalintent.getDoubleExtra("key1", -1);

        mSwingSpeed.setText(speedSwing + " mph");
    }
    public void openDashboard() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}