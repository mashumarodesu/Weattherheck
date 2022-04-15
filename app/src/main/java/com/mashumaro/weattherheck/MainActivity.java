package com.mashumaro.weattherheck;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout homeCL;
    private ImageView backgroundIV, iconIV, windDirectionIV;
    private ProgressBar progressBar;
    private RecyclerView forecastRV, todayForecastRV;
    private TextView cityTV, conditionTV, temperatureTV;
    private ArrayList<HourlyWeather> todayForecastArrayList;
    private ArrayList<DailyWeather> forecastArrayList;
    private TodayForecastAdapter todayForecastAdapter;
    private ForecastAdapter forecastAdapter;
    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main);

        homeCL = findViewById(R.id.home);
        backgroundIV = findViewById(R.id.background);
        iconIV = findViewById(R.id.icon);
        progressBar = findViewById(R.id.progressBar);
        forecastRV = findViewById(R.id.forecast);
        todayForecastRV = findViewById(R.id.today_forecast);
        cityTV = findViewById(R.id.city);
        conditionTV = findViewById(R.id.condition);
        temperatureTV = findViewById(R.id.temperature);
        todayForecastArrayList = new ArrayList<>();
        todayForecastAdapter = new TodayForecastAdapter(this, todayForecastArrayList);
        todayForecastRV.setAdapter(todayForecastAdapter);
        forecastArrayList = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(this, forecastArrayList);
        forecastRV.setAdapter(forecastAdapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        city = getCity(location.getLatitude(), location.getLongitude());

        getWeatherInfo(city, location.getLatitude(), location.getLongitude());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please grant us the permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private String getCity(double latitude, double longitude) {

        String cityName = "";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 10);

            for (int i = 0; i < addresses.size(); i++) {
                if (addresses.size() > 0) {
                    String city = addresses.get(i).getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                    }
                } else {
                    Log.d("TAG", "CITY NOT FOUND");
                    Toast.makeText(this, "City not found", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityName;
    }



    private void getWeatherInfo(String city, double latitude, double longitude) {
        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longitude + "&units=metric&exclude=minutely,alerts&appid=6fa6a7756fee621cb965024b0f72e132";

        cityTV.setText(city);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                homeCL.setVisibility(View.VISIBLE);
                todayForecastArrayList.clear();
                forecastArrayList.clear();

                try {
                    // Current weather information
                    String temperature = response.getJSONObject("current").getString("temp");
                    temperatureTV.setText(temperature.concat("°C"));
                    String condition = response.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("main");
                    String iconURL = response.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getString("icon");
                    conditionTV.setText(condition);
                    String timezone = response.getString("timezone");
                    iconIV.setImageResource(getResources().getIdentifier("drawable/i" + iconURL, null, getPackageName()));

                    // Change background image according to time
                    long currentTime = response.getJSONObject("current").getLong("dt");
                    long sunrise = response.getJSONObject("current").getLong("sunrise");
                    long sunset = response.getJSONObject("current").getLong("sunset");

                    if (currentTime >= sunrise && currentTime < sunset) {
                        Picasso.get().load("https://cdn.dribbble.com/users/481070/screenshots/2679807/attachments/539902/08___-____1.png").into(backgroundIV);
                    } else {
                        Picasso.get().load("https://cdn.dribbble.com/users/481070/screenshots/2679807/attachments/539901/08___-____2.jpg").into(backgroundIV);
                    }

                    // Hourly forecast
                    JSONArray hourArray = response.getJSONArray("hourly");
                    for (int j = 0; j < 24; j++) {
                        JSONObject hourIndex = hourArray.getJSONObject(j);
                        String time = hourIndex.getString("dt");
                        String temp = hourIndex.getString("temp");
                        String condIconURL = hourIndex.getJSONArray("weather").getJSONObject(0).getString("icon");
                        String windSpeed = hourIndex.getString("wind_speed");
                        String windDirection = hourIndex.getString("wind_deg");

                        todayForecastArrayList.add(new HourlyWeather(time, temp, condIconURL, windSpeed, windDirection, timezone));
                    }

                    todayForecastAdapter.notifyDataSetChanged();

                    // Daily forecast
                    JSONArray forecastArray = response.getJSONArray("daily");
                    for (int i = 0; i < 5; i++) {
                        JSONObject forecastIndex = forecastArray.getJSONObject(i);
                        String date = forecastIndex.getString("dt");
                        String maxTemp = forecastIndex.getJSONObject("temp").getString("max");
                        String minTemp = forecastIndex.getJSONObject("temp").getString("min");
                        String condIconURL = forecastIndex.getJSONArray("weather").getJSONObject(0).getString("icon");
                        String cond = forecastIndex.getJSONArray("weather").getJSONObject(0).getString("main");

                        forecastArrayList.add(new DailyWeather(date, maxTemp, minTemp, cond, condIconURL, timezone));
                    }

                    forecastAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}