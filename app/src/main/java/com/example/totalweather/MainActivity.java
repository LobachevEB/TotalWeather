package com.example.totalweather;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.totalweather.processing.WeatherProcessing;


public class MainActivity extends AppCompatActivity {

    main_screen MainScreen;
    private final static int REQUEST_CODE = 1;

    final private String SAVE_TEMP_KEY = "SAVE_TEMP_KEY";

    private WeatherProcessing weatherProcessing = null;
    private String temperatureS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainScreen = new main_screen();
    }



}