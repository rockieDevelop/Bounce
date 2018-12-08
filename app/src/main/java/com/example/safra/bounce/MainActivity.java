package com.example.safra.bounce;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
    }

}
