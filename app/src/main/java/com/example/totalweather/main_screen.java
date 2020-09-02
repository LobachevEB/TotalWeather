package com.example.totalweather;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.totalweather.common.ShortWeatherAdapter;
import com.example.totalweather.processing.WeatherData;
import com.example.totalweather.processing.WeatherProcessing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class main_screen extends Fragment {
    private final static int REQUEST_CODE = 1;

    final private String SAVE_TEMP_KEY = "SAVE_TEMP_KEY";

    private final String[] days = {"","",""};
    private final String[] temperatures = {"","",""};
    final ShortWeatherAdapter adapter = new ShortWeatherAdapter();

    private WeatherProcessing weatherProcessing = null;
    private String temperatureS = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.main_screen_fragment, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.short_time_weather);
        LinearLayoutManager mgr = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        mgr.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(mgr);
        recyclerView.setAdapter(adapter);


        return view;
    }

    public void updateWeatherForward(Date dt,int interval) {
        List<String> temperatures = new ArrayList<String>();
        List<String> days = new ArrayList<String>();
        TextView loc = getView().findViewById(R.id.location);
        String location = loc.getText().toString();
        WeatherProcessing wp = new WeatherProcessing();
        for (int i = 0; i < interval; i++) {
            WeatherData data = wp.getWeatherByDate(location,dt,i + 1);
            days.add(data.getDate());
            temperatures.add(data.getTemperature());
        }
        adapter.setParams(days,temperatures);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherProcessing = new WeatherProcessing();

        CalendarView calendarView = (CalendarView) getView().findViewById(R.id.calendarView);
        calendarView.setDate(System.currentTimeMillis(), false, true);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Calendar cl = Calendar.getInstance();
                cl.set(year,month,day);
                updateWeatherForward(cl.getTime(),3);
                temperatureS = weatherProcessing.getWeatherStub("");
                setValues(SAVE_TEMP_KEY, temperatureS);
            }
        });

        Button citi_btn = (Button) getView().findViewById(R.id.citi_btn);
        citi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLocation();
            }
        });

        Button wiki_btn = (Button) getView().findViewById(R.id.wiki);
        wiki_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView location = (TextView) getView().findViewById(R.id.location);
                String url = getString(R.string.wiki_url) + (String) location.getText();
                Uri uri = Uri.parse(url);
                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browser);
            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putString(SAVE_TEMP_KEY,temperatureS);
        Log.d("#TestState","onSaveInstanceState");
    }

    private void selectLocation(){
        Intent intent = new Intent(getActivity(), ChooseLocation.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void setValues(String Key,String Value){
        switch (Key){
            case SAVE_TEMP_KEY:
                final TextView temperature = (TextView) getView().findViewById(R.id.temperature);
                temperature.setText(Value);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode == Activity.RESULT_OK){
            TextView location = (TextView) getView().findViewById(R.id.location);
            location.setText(data.getStringExtra("Location"));
        }
    }

}
