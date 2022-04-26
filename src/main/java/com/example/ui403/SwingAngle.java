package com.example.ui403;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SwingAngle extends AppCompatActivity {
    private Button dashboard2;

    TextView mSwingAngle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swing_angle);

        dashboard2 = findViewById(R.id.dashboard2);
        dashboard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboard2();
            }
        });

        mSwingAngle = findViewById(R.id.swingangle);
        Intent myLocalintent = getIntent();
        double angle = myLocalintent.getDoubleExtra("key2", -1);

        mSwingAngle.setText(angle + " deg");
    }

    public void openDashboard2() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}