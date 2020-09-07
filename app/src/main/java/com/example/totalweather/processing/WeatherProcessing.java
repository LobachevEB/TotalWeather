package com.example.totalweather.processing;

import android.os.Build;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.RequiresApi;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.totalweather.model.WeatherRequest;
import com.google.gson.Gson;

public class WeatherProcessing {


    public String getCurrentWeather(String location){
        WeatherData result = getWeatherByDate(location,new Date(),0);
        return result.getTemperature();
    }

    public WeatherData getWeatherByDate(String location, Date date,int day_increment){
        WeatherData result = new WeatherData();
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE,day_increment);
        Date newDate = cl.getTime();
        result.setDate(String.valueOf(cl.get(Calendar.MONTH)) + "/" + cl.get(Calendar.DAY_OF_MONTH) );
        final WeatherRequest[] weatherRequest = {null};
        Processing processing = new Processing(location,weatherRequest);
        Thread proc = new Thread(processing);
        proc.start();
        try {
            proc.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(weatherRequest != null && weatherRequest.length > 0){
            String temper = processing.getTemperature();
            result.setTemperature(temper);
        }
        return result;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    private String calcWeatherByDate(String location,Date date){
        Random r = new Random();
        return String.valueOf(20 + r.nextInt(10));
    }

    public class Processing implements Runnable{
        String location;
        private static final String WEATHER_URL =
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
        private static final String API = "ee8c34b442aabdd5537de3268520e03e";
        private static final String TAG = "WEATHER";
        WeatherRequest[] weatherRequest;

        public Processing(String location,WeatherRequest[] weatherRequest) {
            this.location = location;
            this.weatherRequest = weatherRequest;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            HttpsURLConnection urlConnection = null;
            try {
                final URL uri = new URL(String.format(WEATHER_URL,location,API));
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                String result = getLines(in);
                // преобразование данных запроса в модель
                Gson gson = new Gson();
                weatherRequest[0] = gson.fromJson(result, WeatherRequest.class);
                // Возвращаемся к основному потоку
            }catch (MalformedURLException e) {
                //Log.e(TAG, "Fail URI", e);
                e.printStackTrace();
            }
            catch (Exception e) {
                //Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
            } finally {
                if (null != urlConnection) {
                    urlConnection.disconnect();
                }
            }

        }

        public String getTemperature(){
            try {
                return String.format("%.2f",weatherRequest[0].getMain().getTemp()-273);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "";
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private String getLines(BufferedReader in) {
            return in.lines().collect(Collectors.joining("\n"));
        }

    }
}
