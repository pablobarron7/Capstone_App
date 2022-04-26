package com.example.ui403;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BTConnection extends AppCompatActivity {
    //declare initial variables

    Button dashboard6;
    /*
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    private static final String TAG = "BTConnection";

    private static final String[] BLUE_PERMISSIONS = {
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT};

     */

    //universal ID for Bluetooth board
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    //Button mOnBtn, mOffBtn, mDiscoverBtn
    Button mDiscoverBtn;
    Button mPairedBtn;

    BluetoothAdapter mBlueAdapter;


    //create an activity launcher to execute an intent

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Log.e("Activity Result", "OK");
                Intent data = result.getData();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btconnection);

        //to navigate back to dashboard
        dashboard6 = findViewById(R.id.dashboard6);
        dashboard6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboard6();
            }
        });

        //initializing each variable to be found by its ID
        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);

        mBlueIv = findViewById(R.id.bluetoothIv);

        //mOnBtn = findViewById(R.id.onBtn);
        //mOffBtn = findViewById(R.id.offBtn);
        mDiscoverBtn = findViewById(R.id.dicoverableBtn);
        mPairedBtn = findViewById(R.id.pairedBtn);

        //adapter
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        //System.out.println(mBlueAdapter.getBondedDevices());

        //establishes/declares bluno device to be able to connect to a socket for communication
        BluetoothDevice blunoMCU = mBlueAdapter.getRemoteDevice("00:21:07:34:D9:34");

        //output device name to console to make sure we are working with Bluno Device
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                    BTConnection.this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                    1
            );
        }
        System.out.println(blunoMCU.getName());

        //creating a socket for communication
        BluetoothSocket bluetoothSocket = null;
        int count = 0;
        do {
            try {
                bluetoothSocket = blunoMCU.createRfcommSocketToServiceRecord(mUUID);
                //System.out.println("Should be 1,2 or 3"+bluetoothSocket.getConnectionType()); to make sure socket was created
                System.out.println(bluetoothSocket);
                bluetoothSocket.connect();
                System.out.println(bluetoothSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
        }while (!bluetoothSocket.isConnected() && count < 3);
        //to send 0 char to bluno to send data
        try {
            assert bluetoothSocket != null;
            OutputStream outputStream = bluetoothSocket.getOutputStream();
            System.out.println("oStream");
            outputStream.write(48);
            System.out.println("oStream");
            System.out.println(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        try {

            inputStream = bluetoothSocket.getInputStream();
            System.out.println("iStream");
            inputStream.skip(inputStream.available());
            //byte[] buffer = new byte[30];
            System.out.println("iStream");
            //read alphabet from bluno
            for (int i = 0; i < 26; i++) {
                int b = inputStream.read();
                System.out.println((char)b);
            }
            System.out.println("iStream");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //close socket once communication is complete
        try {
            bluetoothSocket.close();
            System.out.println("closeing");
            System.out.println(bluetoothSocket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //check if bluetooth is available or not

        //set image according to bluetooth status
        if (mBlueAdapter.isEnabled()) {
            mBlueIv.setImageResource(R.drawable.ic_action_on);
        } else {
            mBlueIv.setImageResource(R.drawable.ic_action_off);
        }
        /*
        //on btn click
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBlueAdapter.isEnabled()) {
                    showToast("Turning on Bluetooth...");
                    //intent to on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    activityResultLauncher.launch(intent);
                } else {
                    showToast("Bluetooth is already on");
                }

            }
        });
         */

        //discover bluetooth btn
        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(BTConnection.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(
                            BTConnection.this,
                            new String[]{Manifest.permission.BLUETOOTH_SCAN},
                            1
                    );
                }
                if (!mBlueAdapter.isDiscovering()) {  //blu_SCN
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    activityResultLauncher.launch(intent);
                }
            }
        });


        /*
        //off btn click
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            //@SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (mBlueAdapter.isEnabled()) {
                    if (ActivityCompat.checkSelfPermission(BTConnection.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(
                                BTConnection.this,
                                new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                                1
                        );
                    }
                    mBlueAdapter.disable(); //blue_CNT
                    showToast("Turning off Bluetooth");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                } else {
                    showToast("Bluetooth already off");
                }
            }
        });
        */
        //get paired devices btn click
        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            //@SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (mBlueAdapter.isEnabled()) {
                    mPairedTv.setText("Paired Device");
                    if (ActivityCompat.checkSelfPermission(BTConnection.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(
                                BTConnection.this,
                                new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                                1
                        );
                    }
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices(); //blu_CNT
                    for (BluetoothDevice device: devices) {
                        mPairedTv.append("\nDevice: " + device.getName() + "," + device);
                    }
                }
                else {
                    showToast("Turn on Bluetooth to get Paired Devices");
                }
            }
        });

    }

    //to check if permission was granted by the user
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("SCAN Permission Granted");

            }
            else {
                showToast("SCAN Permission Denied");
            }
        }
    }
     */


    //toast
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void openDashboard6() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}