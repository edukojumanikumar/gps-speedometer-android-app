package com.example.speedometergps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayDeque;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity2 extends AppCompatActivity implements LocationListener {

    private TextView speedTextView;
    private LocationManager locationManager;
    private Switch unitSwitch;
    private static final double SPEED_LIMIT_KMPH = 50.0; // Speed limit in km/h
    private static final double SPEED_LIMIT_MPH = 31.07; // Speed limit in mph (approx)
    private boolean isMPH = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView nametextView = findViewById(R.id.textView2);
        String name = getIntent().getStringExtra("USER_NAME");
        nametextView.setText("Hello "+name);

        Button exit=findViewById(R.id.button2);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        speedTextView = findViewById(R.id.speedTextView);

        unitSwitch = findViewById(R.id.unitSwitch);

        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMPH = isChecked;
                unitSwitch.setText(isMPH ? "Switch to KMPH" : "Switch to MPH");
            }
        });

        // Get the location manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                }
            } else {
                // Permission was denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static final int SPEED_SMOOTHING_FACTOR = 5;
    private ArrayDeque<Float> speedBuffer = new ArrayDeque<>(SPEED_SMOOTHING_FACTOR);

    @Override
    public void onLocationChanged(Location location) {
        // Update speed buffer with current speed and calculate average
        if (speedBuffer.size() >= SPEED_SMOOTHING_FACTOR) {
            speedBuffer.poll(); // Remove the oldest speed
        }
        speedBuffer.offer(location.getSpeed()); // Add the latest speed

        float sum = 0;
        for (float speed : speedBuffer) {
            sum += speed;
        }
        float averageSpeed = sum / speedBuffer.size();

        // Convert m/s to km/h and update the TextView
        double speedKMH = averageSpeed * 3.6*4;
        double speedMPH = speedKMH / 1.609; // Convert km/h to mph
        double displaySpeed = isMPH ? speedMPH : speedKMH;
        double speedLimit = isMPH ? SPEED_LIMIT_MPH : SPEED_LIMIT_KMPH;

        speedTextView.setText(String.format(Locale.getDefault(), "Speed: %.2f %s", displaySpeed, isMPH ? "mph" : "km/h"));
        if (displaySpeed > speedLimit) {
            showSpeedAlert();
        }
    }

    private void showSpeedAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Speed Alert");
        builder.setMessage("You are exceeding the speed limit!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();
    }


}
