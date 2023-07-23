package com.sbdev.mvvm;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<String> weatherData = new MutableLiveData<>();
    private WeatherModel weatherModel;
    private boolean dataLoaded = false;

    public LiveData<String> getWeatherData() {
        return weatherData;
    }

    public void fetchWeatherData(String apiKey, String location) {
        // Load data only once if not already loaded
        if (!dataLoaded) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    {
                        try {
                            String url = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + location;
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();

                            // Perform the network request asynchronously
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseData = response.body().string();

                                    // Parse JSON response and extract temperature
                                    try {
                                        JSONObject jsonResponse = new JSONObject(responseData);
                                        JSONObject currentObj = jsonResponse.getJSONObject("current");
                                        double temperatureCelsius = currentObj.getDouble("temp_c");
                                        String temperatureString = String.valueOf(temperatureCelsius) + " °C";

                                        // Create the WeatherModel instance
                                        weatherModel = new WeatherModel(temperatureString);

                                        // Cache the data and update LiveData
                                        weatherData.postValue(temperatureString);
                                        dataLoaded = true;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        // Handle JSON parsing error if needed
                                    }
                                }

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                    // Handle network request failure if needed
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Handle other exceptions if needed
                        }
                    }

                }
            });
        }
    }
}
