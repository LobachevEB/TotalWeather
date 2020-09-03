package com.example.totalweather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.totalweather.common.Singleton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;

public class ChooseLocation extends AppCompatActivity {

    public Singleton singleton = com.example.totalweather.common.Singleton.getInstance();
    TextInputEditText LocationTxt;
    final int MIN_TEXT_LENGTH = 2;


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

        LocationTxt = findViewById(R.id.editTextLocation);
        //LocationTxt.setOnEditorActionListener(ActionListener.newInstance(this));
/*
        LocationTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) return;
                TextView tv = (TextView) v;
                if(!FindLocation(tv.getText().toString())){
                    showError(tv,getString(R.string.loc_not_found));
                }
                else {
                    hideError(tv);
                }
            }
        });
*/

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    if(v.getId() == R.id.editTextLocation){
                        Snackbar.make(
                                v,
                                getString(R.string.loc_not_found),
                                Snackbar.LENGTH_LONG
                        ).show();
                        if(!FindLocation(((EditText) v).getText().toString())){
                          showError((EditText)v,getString(R.string.loc_not_found));
                          return false;
                        }
                        else {
                            hideError((EditText)v);
                        }
                    }
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        String txt = LocationTxt.getText().toString();
        singleton.setLocation(txt);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        LocationTxt.setText(singleton.getLocation());
    }

    protected void returnCity(){
        Intent intentResult = new Intent();
        intentResult.putExtra("Location", LocationTxt.getText().toString());
        setResult(RESULT_OK, intentResult);

    }

    private boolean shouldShowError() {
        int textLength = LocationTxt.getText().length();
        return textLength > 0 && textLength < MIN_TEXT_LENGTH;
    }


    private boolean FindLocation(String loc){
        // Stub return
        return (loc.length() > 1 || loc.length() == 0);
    }

    private void showError(TextView view, String message) {
        view.setError(message);
    }

    // спрятать ошибку
    private void hideError(TextView view) {
        view.setError(null);
    }

/*
    private static final class ActionListener implements TextView.OnEditorActionListener {
        private final WeakReference<ChooseLocation> ChooseLocationWeakReference;

        public static ActionListener newInstance(ChooseLocation chooseLocation) {
            WeakReference<ChooseLocation> ChooseLocationWeakReference = new WeakReference<>(chooseLocation);
            return new ActionListener(ChooseLocationWeakReference);
        }

        private ActionListener(WeakReference<ChooseLocation> chooseLocationWeakReference) {
            this.ChooseLocationWeakReference = chooseLocationWeakReference;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            ChooseLocation chooseLocation = ChooseLocationWeakReference.get();
            if (chooseLocation != null) {
                if (actionId == EditorInfo.IME_ACTION_GO && chooseLocation.shouldShowError()) {
                    chooseLocation.showError(chooseLocation.LocationTxt,chooseLocation.getString(R.string.loc_not_found));
                } else {
                    chooseLocation.hideError(chooseLocation.LocationTxt);
                }
            }
            return true;
        }
    }
*/

}

