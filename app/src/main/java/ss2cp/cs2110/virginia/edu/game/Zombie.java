package ss2cp.cs2110.virginia.edu.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by ShaoranSun on 4/7/15.
 */
public class Zombie extends GameObject {

    private int score;
    private int speed;
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private int alt = 0;

    public Zombie(Bitmap res, int x, int y, int w, int h, int score, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        this.score = score;

        speed = 7 + (int) (rand.nextDouble() * score / 30);

        //cap zombie speed
        if (speed > 30) speed = 30;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(40);

    }

    public void update() {
        x -= speed;
        animation.update();
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        } catch (Exception e) {
        }
    }

    public void danger(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(-65536);
        paint.setFakeBoldText(true);

        Rect rectTop = new Rect(0, 0, GamePanel.WIDTH, 30);
        Rect rectLeft = new Rect(GamePanel.WIDTH - 30, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        Rect rectRight = new Rect(0, 0, 30, GamePanel.HEIGHT);
        Rect rectBot = new Rect(0, GamePanel.HEIGHT - 30, GamePanel.WIDTH, GamePanel.HEIGHT);

        canvas.drawRect(rectTop, paint);
        canvas.drawRect(rectBot, paint);
        canvas.drawRect(rectLeft, paint);
        canvas.drawRect(rectRight, paint);
        if (alt % 2 == 0) {

            paint.setTextSize(30);
        }
        canvas.drawText("DANGER", super.x, super.y, paint);
        paint.setTextSize(20);


    }

    @Override
    public int getWidth() {
        //offset slightly for more realistic collision detection

        return width - 10;
    }

    public int getSpeed() {
        return speed;
    }
    public void altAdd(){
        alt++;
    }

}
