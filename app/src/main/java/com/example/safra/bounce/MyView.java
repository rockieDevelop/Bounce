package com.example.safra.bounce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.DropBoxManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kru13 on 12.10.16.
 */
public class MyView extends View{

    public static Bitmap[] BMP;

    BitmapFactory.Options optionsLeft = new BitmapFactory.Options();
    BitmapFactory.Options optionsRight = new BitmapFactory.Options();
    BitmapFactory.Options optionsUp = new BitmapFactory.Options();

    public static int MAX_LVL = 2;
    public static int WIDTH, HEIGHT;
    public static int WIDTH_OF_SCREEN, HEIGHT_OF_SCREEN;

    //LoadMapJson load;


    int levelId = 0;
    private int bLeftX, bLeftY;
    private int bRightX, bRightY;
    private int bUpX, bUpY;

    private boolean firstUse = true;
    boolean isReady = false;
    boolean isLoading = false;
    private boolean restart = false;
    private boolean restartHelp = false;

    private List<Enemy> enemies = new ArrayList<Enemy>();
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();

    private int[][] tiles;
    private Player player;
    private Gate gate;

    int level[] = new int[400];/* = {
            12,12,1,10,7,7,         //lx, ly, playerX, playerY, gateX, gateY
            2,10,1,3,1,             //number of enemies, enemy1_X, enemy1_Y, enemy2_X, enemy2_Y, ...
            1,1,1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,1,1,0,0,0,1,
            1,0,0,0,0,0,0,1,0,0,0,1,
            1,0,0,0,0,0,2,0,1,0,0,1,
            1,0,1,1,1,1,1,0,1,1,0,1,
            1,0,0,0,0,1,1,1,1,0,0,1,
            1,0,0,2,0,0,0,0,1,0,1,1,
            1,1,1,1,1,1,1,1,1,0,0,1,
            1,0,0,0,0,0,0,0,0,1,0,1,
            1,0,0,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,1,1
    };*/
    int lx = 1;// = level[0];
    int ly = 1;// = level[1];
    int playerX;// = level[2];
    int playerY;// = level[3];
    int gateX;// = level[4];
    int gateY;// = level[5];
    int numOfEnemies;// = level[6];

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {

        BMP = new Bitmap[9];

        BMP[0] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        BMP[1] = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
        BMP[2] = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle);
        BMP[3] = BitmapFactory.decodeResource(getResources(), R.drawable.gate);
        BMP[4] = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        BMP[5] = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        BMP[6] = BitmapFactory.decodeResource(getResources(), R.drawable.arrowleft);
        BMP[7] = BitmapFactory.decodeResource(getResources(), R.drawable.arrowright);
        BMP[8] = BitmapFactory.decodeResource(getResources(), R.drawable.arrowup);

        optionsLeft.inJustDecodeBounds =  optionsRight.inJustDecodeBounds = optionsUp.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.drawable.arrowleft, optionsLeft);
        BitmapFactory.decodeResource(getResources(),R.drawable.arrowright, optionsRight);
        BitmapFactory.decodeResource(getResources(),R.drawable.arrowup, optionsUp);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float xDown = event.getX();
        float yDown = event.getY();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                player.setxMove(0);
                player.setyMove(0);
                return true;
            }

            case MotionEvent.ACTION_DOWN:
            {
                if( xDown > bLeftX && xDown < bLeftX + optionsLeft.outWidth*2 && yDown > bLeftY && yDown < bLeftY + optionsLeft.outHeight*2 )
                {
                    player.setxMove(-player.getSpeed());
                    //Toast.makeText(getContext(), "Left " , Toast.LENGTH_SHORT).show();
                }
                if( xDown > bRightX && xDown < bRightX + optionsRight.outWidth*2 && yDown > bRightY && yDown < bRightY + optionsRight.outHeight*2 )
                {
                    player.setxMove(player.getSpeed());
                    //Log.d("test", "2 player xMove" + player.getxMove());
                    //Toast.makeText(getContext(), "Right " , Toast.LENGTH_SHORT).show();
                }

                if( xDown > bUpX && xDown < bUpX + optionsUp.outWidth*2 && yDown > bUpY && yDown < bUpY + optionsUp.outHeight*2 && !player.jumping)
                {
                    player.setDistanceJumped(0);
                    player.jumping = true;
                    //Toast.makeText(getContext(), "Up " , Toast.LENGTH_SHORT).show();
                }
                return true;
            }

        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        WIDTH_OF_SCREEN = w;
        HEIGHT_OF_SCREEN = h;

        WIDTH = WIDTH_OF_SCREEN / ly;
        HEIGHT = HEIGHT_OF_SCREEN / lx;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected void update(){
        if(firstUse){
            /*Log.d("testik","neco3");
            load = new LoadMapJson(getContext());
            Log.d("testik",""+load.lx);
            lx = load.lx;
            ly = load.ly;*/

            enemies.clear();
            for(int i = 0; i < numOfEnemies; i++){
                boolean ver = false, hor = false;
                if(levelId == 0){
                    if(i == 0)
                        ver = true;
                    if(i == 1 || i == 2)
                        hor = true;
                }
                if(levelId == 1)
                    ver = true;
                if(levelId == 2)
                    ver = true;
                enemies.add(new Enemy(level[7+(i*2)],level[8+(i*2)], 3, this, hor, ver));
                //enemies.add(new Enemy(load.enemies[i*2],load.enemies[i*2+1],3,this,false,true));
            }

            obstacles.clear();
            tiles = new int[lx][ly];
            for (int i = 0; i < lx; i++) {
                for (int j = 0; j < ly; j++) {
                    ////tiles.add(new Tile(level[(i*12 + j)+7+(numOfEnemies*2)], j*WIDTH, (j+1)*WIDTH, i*HEIGHT, (i+1)*HEIGHT));
                    tiles[j][i] = level[(i*lx + j)+7+(numOfEnemies*2)];
                    //tiles[j][i] = load.map[i*lx + j];
                    if(tiles[j][i] == 2){
                        obstacles.add(new Obstacle(j, i));
                    }
                }
            }

            player = new Player(playerX, playerY, 10, this);
            gate = new Gate(gateX, gateY);
            firstUse = false;
            isLoading = false;
        }
        for(Enemy e : enemies)
            e.update();
        player.update();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isReady) {
            if (restartHelp) {
                long now = 0;
                long time = System.nanoTime();
                while (now - time < 2000000000) {
                    now = System.nanoTime();
                }
                restartHelp = false;
                restart = false;
            }
            update();
            loadMap(canvas);

            for (Enemy e : enemies) {
                e.render(canvas);
            }
            gate.render(canvas);
            player.render(canvas);
            drawButtons(canvas);
        /*for(Obstacle o : obstacles)
            o.render(canvas);*/

            if (restart) {
                Paint paint = new Paint();
                //paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.WHITE);
                paint.setTextSize(60);
                canvas.drawText("Restart", WIDTH_OF_SCREEN / 3, HEIGHT_OF_SCREEN / 2, paint);
                restartHelp = true;
            }
        }
        else if(isLoading){
            Paint paint = new Paint();
            //paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            canvas.drawText("Loading", WIDTH_OF_SCREEN / 3, HEIGHT_OF_SCREEN / 2, paint);
        }
        else{
            String levelUrl = "https://homel.vsb.cz/~saf0068/map" + levelId + ".txt";
            new LoadLevel().execute(levelUrl);
            Paint paint = new Paint();
            //paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            canvas.drawText("Loading", WIDTH_OF_SCREEN / 3, HEIGHT_OF_SCREEN / 2, paint);
            isLoading = true;
        }
        invalidate();
    }

    protected void drawButtons(Canvas canvas){
        bLeftX = WIDTH/2;
        bLeftY = (canvas.getHeight() - HEIGHT/2) - optionsLeft.outHeight;
        canvas.drawBitmap(MyView.BMP[6], null,
                new Rect( bLeftX, bLeftY, bLeftX + (optionsLeft.outWidth), bLeftY + optionsLeft.outHeight ), null);

        bRightX = bLeftX + optionsLeft.outWidth + WIDTH/2;
        bRightY = (canvas.getHeight() - HEIGHT/2) - optionsRight.outHeight;
        canvas.drawBitmap(MyView.BMP[7], null,
                new Rect( bRightX, bRightY, bRightX + (optionsRight.outWidth), bRightY + optionsRight.outHeight), null);

        bUpX = (canvas.getWidth() - WIDTH/2) - optionsUp.outWidth;
        bUpY = (canvas.getHeight() - HEIGHT/2) - optionsUp.outHeight;
        canvas.drawBitmap(MyView.BMP[8], null,
                new Rect( bUpX, bUpY, bUpX + (optionsUp.outWidth), bUpY + optionsUp.outHeight), null);
    }

    protected void loadMap(Canvas canvas){
        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                //canvas.drawBitmap(BMP[tiles[j][i]], null,
                //        new Rect(j*WIDTH, i*HEIGHT,(j+1)*WIDTH, (i+1)*HEIGHT), null);
                getTile(j,i).render(canvas, j*WIDTH, (j+1)*WIDTH, i*HEIGHT, (i+1)*HEIGHT);
            }
        }
    }

    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight())
            return Tile.empty;

        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null)
            return Tile.wall;

        return t;
    }

    private class LoadLevel extends AsyncTask<String, Void, LevelData> {

        public LoadLevel() {
        }
        @Override
        protected LevelData doInBackground(String... strings) {
            URL data = null;
            try {
                data = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            Log.d("game", "neco" + data);
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(data.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            Log.d("game", "neco5");

            String levelData = "";
            String inputLine;
            try{
                while ((inputLine = in.readLine()) != null) {
                levelData += inputLine;
            }
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            levelData = levelData.replaceAll("\\s+", "");
            Log.d("Game", "Level"+lx+" "+ly);
            int pos = 0;
            for (String item : levelData.split(",")) {
                if (item.length() > 0)
                    level[pos] = Integer.valueOf(item);
                pos++;
            }
            lx = level[0];
            ly = level[1];
            playerX = level[2];
            playerY = level[3];
            gateX = level[4];
            gateY = level[5];
            numOfEnemies = level[6];
            WIDTH = WIDTH_OF_SCREEN / ly;
            HEIGHT = HEIGHT_OF_SCREEN / lx;
            LevelData lvl = new LevelData(level);
            isReady = true;
            return lvl;
        }
    }

    public class LevelData{
        int[] l;
        public LevelData(int[] l){this.l = l;}
    }

    public void restart(){
        restart = true;
        firstUse = true;
    }

    public void victory(){
        levelId++;
        if(levelId > MAX_LVL)
            levelId = MAX_LVL;
        isReady = false;
        restart();
        Toast.makeText(getContext(), "VICTORY!!! " , Toast.LENGTH_SHORT).show();
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public Gate getGate() {
        return gate;
    }
}
