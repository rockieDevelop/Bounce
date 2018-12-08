package com.example.safra.bounce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class menu extends Activity {

    public static SharedPreferences mySharedPref;
    public static SharedPreferences.Editor mySharedEditor;
    int x,y,l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mySharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        x = mySharedPref.getInt("pX",-1);
        y = mySharedPref.getInt("pY",-1);
        l = mySharedPref.getInt("level",-1);
        Log.d("test", "xyl "+x +" "+y+" "+l);

        Button game = (Button) findViewById(R.id.new_game);
        game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(x != -1) {
                    mySharedEditor = mySharedPref.edit();
                    mySharedEditor.remove("pX");
                    //Log.d("test", "xko "+x);
                    mySharedEditor.commit();
                }
                if(y != -1) {
                    mySharedEditor = mySharedPref.edit();
                    mySharedEditor.remove("pY");
                    //Log.d("test", "yps "+y);
                    mySharedEditor.commit();
                }
                if(l != -1) {
                    mySharedEditor = mySharedPref.edit();
                    mySharedEditor.remove("level");
                    mySharedEditor.commit();
                }
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button cont = (Button) findViewById(R.id.cont);
        if(x == -1 && y == -1 && l == -1){
            View b = findViewById(R.id.cont);
            b.setVisibility(View.GONE);
        }
        cont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button options = (Button) findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(view.getContext(), options.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mySharedEditor = mySharedPref.edit();
                mySharedEditor.clear();
                mySharedEditor.commit();
                finish();
                //System.exit(0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }
}
