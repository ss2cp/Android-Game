package ss2cp.cs2110.virginia.edu.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by ShaoranSun on 4/27/15.
 */
public class Coin extends GameObject {
    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private boolean show;
    private long startTime;
    private long elapse;
    private long createTime;


    public Coin(Bitmap res, int x, int y, int w, int h, int numFrames, long createTime) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        show = false;
        this.createTime = createTime;
        elapse = 0;


        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(120);

    }

    public void update() {

        animation.update();
    }

    public void draw(Canvas canvas) {
        try {

            canvas.drawBitmap(animation.getImage(), x, y, null);

        } catch (Exception e) {
        }
    }

    public void draw10(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(20);
        paint.setColor(-65536);
        canvas.drawText("+Gold", super.x, super.y + super.getHeight(), paint);
    }

    public void setShow10(boolean b) {
        show = b;
    }

    public void setStartTime(long s) {
        startTime = s;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getElapse() {
        return elapse;
    }

    public void setElapse(long e) {
        elapse = e;
    }

    public boolean getShow10() {
        return show;
    }

    public long getCreateTime(){
        return createTime;
    }

}


