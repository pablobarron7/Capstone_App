package com.example.ui403;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Efficiency extends AppCompatActivity {
    private Button dashboard3;
    TextView mEffic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efficiency);

        dashboard3 = findViewById(R.id.dashboard3);
        dashboard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboard3();
            }
        });
        mEffic = findViewById(R.id.effic);
        Intent myLocalintent = getIntent();
        double efficiency = myLocalintent.getDoubleExtra("key4", -1);

        mEffic.setText(efficiency + "%");
    }
    public void openDashboard3() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}