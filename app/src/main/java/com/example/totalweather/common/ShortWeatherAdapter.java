package com.example.totalweather.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.totalweather.R;

public class ShortWeatherAdapter extends RecyclerView.Adapter<ShortWeatherAdapter.ShortWeatherHolder>{
    private List<String> days;
    private List<String> temperatures;

    public void setParams(List<String> days,List<String> temperatures) {
        this.days = days;
        this.temperatures = temperatures;
        notifyDataSetChanged();
    }

    public void justSetCities(List<String> cities,List<String> temperatures) {
        this.days = days;
        this.temperatures = temperatures;
    }

    @NonNull
    @Override
    public ShortWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShortWeatherHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.short_weather, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ShortWeatherAdapter.ShortWeatherHolder holder, int position) {
        holder.bind(days.get(position),temperatures.get(position));
    }

    @Override
    public int getItemCount() {
        if (days == null) return 0;
        return days.size();
    }

    static class ShortWeatherHolder extends RecyclerView.ViewHolder{
        private final TextView weatherViewDay;
        private final TextView weatherViewTemperature;
        public ShortWeatherHolder(@NonNull View itemView) {
            super(itemView);
            weatherViewDay = itemView.findViewById(R.id.weatherElemDay);
            weatherViewTemperature = itemView.findViewById(R.id.weatherElemTemperature);

        }
        void bind(final String day,final String temper){
            weatherViewDay.setText(day);
            weatherViewTemperature.setText(temper);
        }
    }
}
