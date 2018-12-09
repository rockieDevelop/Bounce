package com.example.safra.bounce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class options extends Activity {

    private boolean soundsEnabled = true;
    private boolean gyroscopeEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        menu.mySharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);

        soundsEnabled = menu.mySharedPref.getBoolean("sounds", true);
        Switch switch1 = (Switch)findViewById(R.id.switch1);
        switch1.setChecked(soundsEnabled);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    soundsEnabled = true;
                    menu.mySharedEditor = menu.mySharedPref.edit();
                    menu.mySharedEditor.putBoolean("sounds",soundsEnabled);
                    menu.mySharedEditor.apply();
                }
                else{
                    soundsEnabled = false;
                    menu.mySharedEditor = menu.mySharedPref.edit();
                    menu.mySharedEditor.putBoolean("sounds",soundsEnabled);
                    menu.mySharedEditor.apply();
                }
            }
        });


        gyroscopeEnabled = menu.mySharedPref.getBoolean("gyroscope", false);
        Switch switch2 = (Switch)findViewById(R.id.switch2);
        switch2.setChecked(gyroscopeEnabled);

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    gyroscopeEnabled = true;
                    menu.mySharedEditor = menu.mySharedPref.edit();
                    menu.mySharedEditor.putBoolean("gyroscope",gyroscopeEnabled);
                    menu.mySharedEditor.apply();
                }
                else{
                    gyroscopeEnabled = false;
                    menu.mySharedEditor = menu.mySharedPref.edit();
                    menu.mySharedEditor.putBoolean("gyroscope",gyroscopeEnabled);
                    menu.mySharedEditor.apply();
                }
            }
        });


        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(view.getContext(), menu.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

}
