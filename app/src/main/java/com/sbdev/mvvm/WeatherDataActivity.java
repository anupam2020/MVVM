package com.sbdev.mvvm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_data_layout);

        // Get the weather data from the intent extra
        String weatherData = getIntent().getStringExtra("weather_data");

        // Display the weather data in a TextView
        TextView textViewWeatherData = findViewById(R.id.textViewWeatherData);
        textViewWeatherData.setText(weatherData);
    }
}
