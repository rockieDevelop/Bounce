package com.example.safra.bounce;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LoadMapJson {

    private Context ctx;

    public int[] map;
    public int[] enemies;
    public int lx, ly;
    public int playerX, playerY;
    public int gateX, gateY;

    int lvlID= 0;
    String levelURL= "https://homel.vsb.cz/~saf0068/maps.json";

    public LoadMapJson(Context ctx){
        this.ctx = ctx;
        this.lvlID = 0;
        new DownloadLevelJSON(ctx, ++lvlID).execute(levelURL);
    }

    private class DownloadLevelJSON extends AsyncTask<String, Void, LevelData> {

        Context context;
        int lvlID;

        public DownloadLevelJSON(Context context, int levelID) {
            this.context= context;
            this.lvlID= levelID;
            Log.d("kek", "soiadflkj 1");
        }

        @Override
        protected LevelData doInBackground(String... urls) {
            try {
                Log.d("kek", "soiadflkj 1"+urls[0]);
                URL url = new URL(urls[0]);
                Log.d("kek", "soiadflkj 2"+url);
                URLConnection urlConnection = url.openConnection();
                Log.d("kek", "soiadflkj 3");
                urlConnection.setConnectTimeout(1000);
                InputStream is=  urlConnection.getInputStream();
                Log.d("kek", "soiadflkj 4");

                JSONObject obj= ReadJSON(is);
                obj= obj.getJSONObject("level" + Integer.toString(lvlID));
                Log.d("kek", "soiadflkj 1");
                LevelData d= new LevelData(obj.getInt("lx"), obj.getInt("ly"), obj.getInt("playerX"), obj.getInt("playerY"), obj.getInt("gateX"), obj.getInt("gateY"));
                Log.d("kek", "soiadflkj 1");
                d.generateEnemies(obj.getJSONArray("enemies"));
                d.fillMap(obj.getJSONArray("map"));
                return d;

            } catch (Exception ex) {
                Log.d("kek", ex.getMessage());
                ex.printStackTrace();
                Log.d("kek", "Failed to load level data...");
                return null;
            }
        }

        private JSONObject ReadJSON(InputStream is) throws Exception {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            Log.d("kek", "lulw1");
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            Log.d("kek", "lulw1");

            Log.d("kek", responseStrBuilder.toString());
            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
            Log.d("kek", "lulw1");

            //returns the json object
            return jsonObject;
        }

    }

    public class LevelData {

        public LevelData(int lx1, int ly1, int playerX1, int playerY1, int gateX1, int gateY1) {
            Log.d("testik",""+lx1);
            lx = lx1;
            ly = ly1;
            playerX = playerX1;
            playerY = playerY1;
            gateX = gateX1;
            gateY = gateY1;
        }

        public void fillMap(JSONArray arr) throws JSONException {
            map= new int[arr.length()];
            for(int i= 0; i < map.length; i++) {
                map[i]= arr.getInt(i);
            }
        }

        public void generateEnemies(JSONArray arr) throws JSONException {
            enemies= new int[arr.length()];
            for(int i= 0; i < map.length; i++) {
                enemies[i]= arr.getInt(i);
            }
        }

    }


}
