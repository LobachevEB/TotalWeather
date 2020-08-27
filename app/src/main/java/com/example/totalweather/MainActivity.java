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

    private final static int REQUEST_CODE = 1;

    final private String SAVE_TEMP_KEY = "SAVE_TEMP_KEY";

    private WeatherProcessing weatherProcessing = null;
    private String temperatureS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onCreate");
        setContentView(R.layout.activity_main);
        weatherProcessing = new WeatherProcessing();

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setDate(System.currentTimeMillis(),false,true);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView,int year,int month,int day) {
                temperatureS = weatherProcessing.getWeatherStub("");
                setValues(SAVE_TEMP_KEY,temperatureS);
            }
        });

        Button citi_btn = findViewById(R.id.citi_btn);
        citi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLocation();
            }
        });

        Button wiki_btn = findViewById(R.id.wiki);
        wiki_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView location = findViewById(R.id.location);
                String url = getString(R.string.wiki_url) + (String) location.getText();
                Uri uri = Uri.parse(url);
                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browser);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        temperatureS = saveInstanceState.getString(SAVE_TEMP_KEY);
        setValues(SAVE_TEMP_KEY,temperatureS);
        //Toast.makeText(getApplicationContext(), "Повторный запуск!! - onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putString(SAVE_TEMP_KEY,temperatureS);
        //Toast.makeText(getApplicationContext(), "onSaveInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("#TestState","onDestroy");
    }


    private void selectLocation(){
        Intent intent = new Intent(this,ChooseLocation.class);
        startActivityForResult(intent, REQUEST_CODE);
        //startActivity(intent);
    }

    private void setValues(String Key,String Value){
        switch (Key){
            case SAVE_TEMP_KEY:
                final TextView temperature = findViewById(R.id.temperature);
                temperature.setText(Value);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode == RESULT_OK){
            TextView location = findViewById(R.id.location);
            location.setText(data.getStringExtra("Location"));
        }
    }

}