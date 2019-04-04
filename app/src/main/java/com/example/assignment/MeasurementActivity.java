package com.example.assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MeasurementActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    public static final String MEASUREMENT = "MEASUREMENT";
    private Measurement measurement;
    private TextView pM10, pM25, o3, cO2, noise, latitude, longitude, temperature, humidity, pressure, userId, deviceId;
    private GestureDetector gestureDetector;
    private static final String TAG = "GESTURES";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTuch: " + event);
        boolean eventHandlingFinished = true;
        //return eventHandlingFinished;
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //Toast.makeText(this, "onDown", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //Toast.makeText(this, "onShowPress", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //Toast.makeText(this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onSingleTapUp");
        return true; // done
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //Toast.makeText(this, "onScroll", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //Toast.makeText(this, "onLongPress", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //Toast.makeText(this, "onFling", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onFling " + e1.toString() + "::::" + e2.toString());

        boolean leftSwipe = e1.getX() > e2.getX();
        Log.d(TAG, "onFling left: " + leftSwipe);

        if (leftSwipe) {
            finish();
        }
        return true; // done

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        gestureDetector = new GestureDetector(this, this);

        Intent intent = getIntent();
        measurement = (Measurement) intent.getSerializableExtra(MEASUREMENT);

        Long date = measurement.getUtc();
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        TextView headingView = findViewById(R.id.measurementHeadingTV);
        headingView.setText("Measurement from " + formatter.format(calendar.getTime()));

        pM10 = findViewById(R.id.measurementPM10);
        pM10.setText(measurement.getPm10() + " ");

        pM25 = findViewById(R.id.measurementPM25);
        pM25.setText(measurement.getPm25() + " ");

        o3 = findViewById(R.id.measurementO3);
        o3.setText(measurement.getO3()+ " ");

        cO2 = findViewById(R.id.measurementCO2);
        cO2.setText(measurement.getCo2() + " ");

        noise = findViewById(R.id.measurementNoise);
        noise.setText(measurement.getNoise() + " ");

        latitude = findViewById(R.id.measurementLatitude);
        latitude.setText(measurement.getLatitude() + " ");

        longitude = findViewById(R.id.measurementLongitude);
        longitude.setText(measurement.getLongitude() + " ");

        temperature = findViewById(R.id.measurementTemperature);
        temperature.setText(measurement.getTemperature() + " ");

        humidity = findViewById(R.id.measurementHumidity);
        humidity.setText(measurement.getHumidity() + " ");

        pressure = findViewById(R.id.measurementPressure);
        pressure.setText(measurement.getPressure() + " ");

        userId = findViewById(R.id.measurementUserId);
        userId.setText(measurement.getUserId());

        deviceId = findViewById(R.id.measurementDevice);
        deviceId.setText(measurement.getDeviceId() + " ");
    }


    public void backOnClick(View view) {
        finish();
    }


}
