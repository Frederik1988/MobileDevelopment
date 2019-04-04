package com.example.assignment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String wristbandUrl = "https://berthawristbandrestprovider.azurewebsites.net/api/wristbanddata";
    public static final String USERNAME = "USERNAME";
    private static String measurementsUrl;
    private String user;
    private ListView measurementListView;
    private Intent logInIntent;
    public static final int MY_PERMISSION_REQUEST_CODE = 1234;
    int delay = 0;
    int period = 10000;
    Timer timer = new Timer();

    LocationManager locationManager;
    String provider;
    private double latitude, longitude;

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("GPSSTART", "Must ask for permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_CODE);
        } else {
            Log.d("GPSSTART", "Permission already given");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("GPSPERMISSION", "permission given");
                } else {
                    Log.d("GPSPERMISSION", "Permission not given");
                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                return true;
            case R.id.bluetooth:
                return true;
            case R.id.logout:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInIntent = getIntent();
        user = logInIntent.getStringExtra(USERNAME);
        measurementsUrl = "https://berthabackendrestprovider.azurewebsites.net/api/data/" + user;
        TextView listHeader = new TextView(this);
        listHeader.setTextColor(Color.argb(255, 0, 255, 0));
        listHeader.setTextSize(28);
        listHeader.setText("Measurements");
        measurementListView = findViewById(R.id.mainMeasurementList);
        measurementListView.addHeaderView(listHeader);
        measurementListView.invalidateViews();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                final MeasurementReadTask measurementsTask = new MeasurementReadTask();
                measurementsTask.execute(measurementsUrl);

                final WristbandReadTask wristbandTask = new WristbandReadTask();
                wristbandTask.execute(wristbandUrl);
            }
        }, delay, period);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);

            locationManager.requestLocationUpdates(provider, 20000, 1, this);

            if(location!=null)
                onLocationChanged(location);

        }
    }


    private class MeasurementReadTask extends ReadHTTPTask{
        @Override
        protected void onPostExecute(CharSequence jsonString){
            Gson gson = new GsonBuilder().create();
            final Measurement[] measurements = gson.fromJson(jsonString.toString(), Measurement[].class);

            MeasurementListItemAdapter adapter = new MeasurementListItemAdapter(getBaseContext(), R.layout.measurementlistitem, measurements);
            measurementListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            measurementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), MeasurementActivity.class);
                    Measurement measurement = (Measurement) parent.getItemAtPosition(position);
                    intent.putExtra(MeasurementActivity.MEASUREMENT, measurement);
                    startActivity(intent);
                }
            });
        }

        @Override
        protected void onCancelled(CharSequence message) {
            super.onCancelled();
            TextView view = findViewById(R.id.mainMessageTextView);
            view.setText(message);
        }
    }

    private class WristbandReadTask extends ReadHTTPTask{
        @Override
        protected void onPostExecute(CharSequence jsonString){
            Gson gson = new GsonBuilder().create();
            TextView messageView = findViewById(R.id.mainMessageTextView);
            final Wristband wristband = gson.fromJson(jsonString.toString(), Wristband.class);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", wristband.getDeviceId());
                jsonObject.put("pm25", wristband.getPm25());
                jsonObject.put("pm10", wristband.getPm10());
                jsonObject.put("co2", wristband.getCo2());
                jsonObject.put("o3", wristband.getO3());
                jsonObject.put("pressure", wristband.getPressure());
                jsonObject.put("temperature", wristband.getTemperature());
                jsonObject.put("humidity", wristband.getHumidity());
                jsonObject.put("utc", new Date().getTime());
                jsonObject.put("latitude", latitude);
                jsonObject.put("longitude", longitude);
                jsonObject.put("noise", 2);
                jsonObject.put("userId", user);
                String jsonDocument = jsonObject.toString();
                PostMeasurementTask task = new PostMeasurementTask();
                task.execute("https://berthabackendrestprovider.azurewebsites.net/api/data", jsonDocument);
            } catch (JSONException ex) {
                messageView.setText(ex.getMessage());
            }
        }

        @Override
        protected void onCancelled(CharSequence message) {
            super.onCancelled();
            TextView view = findViewById(R.id.mainMessageTextView);
            view.setText(message);
        }
    }

    private class PostMeasurementTask extends AsyncTask<String, Void, CharSequence> {
        @Override
        protected CharSequence doInBackground(String... params) {
            String urlString = params[0];
            String jsonDocument = params[1];
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(jsonDocument);
                osw.flush();
                osw.close();
                int responseCode = connection.getResponseCode();
                if (responseCode / 100 != 2) {
                    String responseMessage = connection.getResponseMessage();
                    throw new IOException("HTTP response code: " + responseCode + " " + responseMessage);
                }
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                return line;
            } catch (MalformedURLException ex) {
                cancel(true);
                String message = ex.getMessage() + " " + urlString;
                Log.e("Measurement", message);
                return message;
            } catch (IOException ex) {
                cancel(true);
                Log.e("Measurement", ex.getMessage());
                return ex.getMessage();
            }
        }
        @Override
        protected void onPostExecute(CharSequence charSequence) {
            super.onPostExecute(charSequence);
            TextView messageView = findViewById(R.id.mainMessageTextView);
            messageView.setText(charSequence);
            Log.e("POSTMEASUREMENT", charSequence.toString());
        }

        @Override
        protected void onCancelled(CharSequence charSequence) {
            super.onCancelled(charSequence);
            TextView messageView = findViewById(R.id.mainMessageTextView);
            messageView.setText(charSequence);
            Log.e("POSTMEASUREMENT", charSequence.toString());
            finish();
        }
    }
}
