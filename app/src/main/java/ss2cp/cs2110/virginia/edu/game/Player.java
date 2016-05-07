package ss2cp.cs2110.virginia.edu.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Player extends GameObject {
    private Bitmap spritesheet;
    private int score;

    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private Paint paint = new Paint();
    private int life;
    private int totalCoin;


    public Player(Bitmap res, int w, int h, int numFrames) {

        x = 100;
        y = GamePanel.HEIGHT / 2;
        dy = -10;
        score = 0;
        height = h;
        width = w;
        paint.setTextSize(20);
        life = 3;
        totalCoin = 10;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(50);
        startTime = System.nanoTime();

    }


    public void update() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();


    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);


    }

    public void drawScore(Canvas canvas) {
        canvas.drawText("Score: " + score, 100, 100, paint);
        canvas.drawText("Level " + score / 50 + "  Life: " + life + " Bullets: " + totalCoin, 400, 100, paint);


    }

    public void showScore(Canvas canvas) {
        paint.setTextSize(30);
        paint.setColor(-65536);
        canvas.drawText("GAME OVER! Score: " + score + " Level: " + score / 50+" Coins: "+totalCoin, GamePanel.HEIGHT / 2, GamePanel.WIDTH / 2, paint);

    }

    public int getScore() {
        return score;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void resetScore() {
        score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void resetPaint() {
        paint.setTextSize(20);
        paint.setColor(-16777216);
    }

    public void minusLife() {
        life--;
    }

    public int getLife() {
        return life;
    }

    public void addCoin() {
        totalCoin = totalCoin + 1;
    }

    public int getTotalCoin() {
        return totalCoin;
    }

    public void resetLife(){
        life=3;
    }

    public void minusCoin(){
        totalCoin--;
    }
}