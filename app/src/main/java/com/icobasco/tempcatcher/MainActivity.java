package com.icobasco.tempcatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int REQUEST_ENABLE_BT = 101;
    private TextView tvLog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLog = findViewById(R.id.tvLog);
        setupBluetooth();
    }

    public void printLog(String mex) {
        Log.d(TAG, mex);
        tvLog.append(mex + "\n");
    }

    private void setupBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                /*
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                */
                printLog("BT da abilitare!");
                // TODO: gestire abilitazione BT
            }
            else {
                printLog("Parsing Paired Devices...");
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        printDeviceInfo(device);
                    }
                    if (pairedDevices.size()>0)
                        printLog("-----------------");
                }
                printLog("Parsing done!");
            }
        }
        else {
            Log.e(TAG, "ERROR: Bluetooth not supported!");
        }

    }

    public void printDeviceInfo(BluetoothDevice device) {printLog("-----------------");
        printLog("\tName: ["+ device.getName() + "]");
        printLog("\tAddress: [" + device.getAddress() + "]");

        int bondState = device.getBondState();
        String bondStateStr = "--unk--";
        switch (bondState) {
            case BluetoothDevice.BOND_BONDED:   bondStateStr = "BONDED";    break;
            case BluetoothDevice.BOND_BONDING:  bondStateStr = "BONDING";   break;
            case BluetoothDevice.BOND_NONE:     bondStateStr = "NONE";      break;
        }
        printLog("\tBond State: (" + bondState + ")["+ bondStateStr + "]");

        int type = device.getType();
        String typeStr = "--unk--";
        switch (type) {
            case BluetoothDevice.DEVICE_TYPE_CLASSIC:   typeStr = "CLASSIC";    break;
            case BluetoothDevice.DEVICE_TYPE_DUAL:      typeStr = "DUAL";       break;
            case BluetoothDevice.DEVICE_TYPE_LE:        typeStr = "LE";         break;
            case BluetoothDevice.DEVICE_TYPE_UNKNOWN:   typeStr = "UNKNOWN";    break;
        }
        printLog("\tType: (" + type + ")["+ typeStr + "]");
    }
}
