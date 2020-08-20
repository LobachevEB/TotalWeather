package com.example.totalweather;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.totalweather.processing.WeatherProcessing;


public class MainActivity extends AppCompatActivity {

    private WeatherProcessing weatherProcessing = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherProcessing = new WeatherProcessing();

        final TextView temperature = findViewById(R.id.temperature);
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setDate(System.currentTimeMillis(),false,true);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView,int year,int month,int day) {
                temperature.setText(weatherProcessing.getWeatherStub(""));
            }
        });

        Button citi_btn = findViewById(R.id.citi_btn);
        citi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLocation();
            }
        });
    }
    private void selectLocation(){
        startActivity(new Intent(this,ChooseLocation.class));
    }
}