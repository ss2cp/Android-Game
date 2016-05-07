package ss2cp.cs2110.virginia.edu.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by ShaoranSun on 4/8/15.
 */
public class Explosion extends GameObject {


    private int speed;
    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private int originalX;
    private Paint paint = new Paint();

    public Explosion(Bitmap res, Zombie z, int w, int h, int numFrames) {
        super.x = z.getX();
        super.y = z.getY();
        width = w;
        height = h;
        originalX = z.getX();
        this.speed = z.getSpeed();
        paint.setTextSize(40);
        paint.setColor(-65536);


        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i * height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100);

    }

    public void update() {
        x -= speed;
        animation.update();
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
            canvas.drawText("+20", 150, 75, paint);
        } catch (Exception e) {
        }
    }

    public int getOriginalX() {
        return originalX;
    }

    public int getSpeed() {
        return speed;
    }


}
