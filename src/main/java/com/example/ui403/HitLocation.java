package com.example.ui403;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HitLocation extends AppCompatActivity {
    private Button dashboard4;
    TextView mhitLocation;
    TextView mAdvise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit_location);

        dashboard4 = findViewById(R.id.dashboard4);
        dashboard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboard4();
            }
        });

        mhitLocation = findViewById(R.id.hitLocation);
        mAdvise = findViewById(R.id.advice);
        Intent myLocalintent = getIntent();
        int location = myLocalintent.getIntExtra("key3", -1);
        if (location >= 1 && location <= 3) {  /* Bad-  region of bat*/
            mhitLocation.setText("Location " + location);
            //center of the bat
            if(location == 2) {
                mAdvise.setText("Bad, move down bat");
            }
            //lower edge
            else if (location == 1) {
                mAdvise.setText("Bad, move down the bat\n and swing lower");
            }
            //upper edge
            else if (location == 3) {
                mAdvise.setText("Bad, move down the bat\n and swing higher");
            }
        } else if (location >= 4 && location <= 6) { /* Ok- region of bat*/
            mhitLocation.setText("Location " + location);
            //center of bat
            if(location == 5) {
                mAdvise.setText("Ok, move down bat");
            }
            //lower edge
            else if (location == 4) {
                mAdvise.setText("Ok, move down the bat\n  and swing lower");
            }
            //upper edge
            else if (location == 6) {
                mAdvise.setText("Ok, move down the bat\n and swing higher");
            }
        } else if (location >= 7 && location <= 9) {  /* Perfect region of bat */
            mhitLocation.setText("Location " + location);
            //center of bat
            if(location == 8) {
                mAdvise.setText("Perfect Region");
            }
            //lower edge
            else if (location == 7) {
                mAdvise.setText("Pefect region but\n swing lower");
            }
            //upper edge
            else if (location == 9) {
                mAdvise.setText("Pefect region but\n and swing higher");
            }
        } else if (location >= 10 && location <= 12) {  /*Ok+ region of bat*/
            mhitLocation.setText("Location " + location);
            //center of bat
            if(location == 11) {
                mAdvise.setText("Ok, move down bat");
            }
            //lower edge
            else if (location == 10) {
                mAdvise.setText("Ok, move up the bat\n and swing lower");
            }
            //upper edge
            else if (location == 12) {
                mAdvise.setText("Ok, move up the bat\n and swing higher");
            }
        } else if (location >= 13 && location <= 15) {    /* Bad+ region of bat*/
            mhitLocation.setText("Location " + location);
            //center of bat
            if(location == 14) {
                mAdvise.setText("Bad, move down bat");
            }
            //lower edge
            else if (location == 13) {
                mAdvise.setText("Bad, move down the bat\n and swing lower");
            }
            //upper edge
            else if (location == 15) {
                mAdvise.setText("Bad, move down the bat\n and swing higher");
            }
        }

    }
    public void openDashboard4() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}