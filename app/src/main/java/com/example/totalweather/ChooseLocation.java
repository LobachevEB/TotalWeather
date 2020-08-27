package com.example.totalweather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.totalweather.common.Singleton;

public class ChooseLocation extends AppCompatActivity {

    public Singleton singleton = com.example.totalweather.common.Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_location);
        Button done_btn = findViewById(R.id.done);
        done_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    returnCity();
                    finish();
                }
            }

        );
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        EditText LocationTxt = findViewById(R.id.editTextLocation);
        String txt = LocationTxt.getText().toString();
        singleton.setLocation(txt);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        EditText LocationTxt = findViewById(R.id.editTextLocation);
        LocationTxt.setText(singleton.getLocation());
    }

    protected void returnCity(){
        EditText editText = (EditText) findViewById(R.id.editTextLocation);
        Intent intentResult = new Intent();
        intentResult.putExtra("Location", editText.getText().toString());
        setResult(RESULT_OK, intentResult);

    }

}

