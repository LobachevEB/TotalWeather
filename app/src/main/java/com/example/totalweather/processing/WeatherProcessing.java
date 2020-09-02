package com.example.totalweather.processing;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class WeatherProcessing {
    public String getWeatherStub(String location){
        return "+35 C";
    }

    public WeatherData getWeatherByDate(String location, Date date,int day_increment){
        WeatherData result = new WeatherData();
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE,day_increment);
        Date newDate = cl.getTime();
        result.setDate(String.valueOf(cl.get(Calendar.MONTH)) + "/" + cl.get(Calendar.DAY_OF_MONTH) );
        result.setTemperature(calcWeatherByDate(location,newDate));
        return result;
    }

    private String calcWeatherByDate(String location,Date date){
        Random r = new Random();
        return String.valueOf(20 + r.nextInt(10));
    }
}
