package com.sbdev.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sbdev.mvvm.WeatherViewModel;

public class WeatherActivity extends AppCompatActivity {

    private WeatherViewModel weatherViewModel;

    private EditText editTextLocation;
    private Button buttonFetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Set up the ViewModel
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // Find the views from the layout
        editTextLocation = findViewById(R.id.editTextLocation);
        buttonFetchData = findViewById(R.id.buttonFetchData);

        // Set click listener for the button to fetch data
        buttonFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiKey = "ceea495be7374dc6a39174422222906";
                String location = editTextLocation.getText().toString().trim();
                weatherViewModel.fetchWeatherData(apiKey, location);
            }
        });

        // Observe the LiveData for changes
        weatherViewModel.getWeatherData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String responseData) {
                if (responseData != null) {
                    // Display weather data in a new activity
                    Intent intent = new Intent(WeatherActivity.this, WeatherDataActivity.class);
                    intent.putExtra("weather_data", responseData);
                    startActivity(intent);
                }
            }
        });
    }
}
