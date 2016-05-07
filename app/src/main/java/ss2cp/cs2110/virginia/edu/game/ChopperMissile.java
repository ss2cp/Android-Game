package ss2cp.cs2110.virginia.edu.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by ShaoranSun on 4/7/15.
 */
public class ChopperMissile extends GameObject {

    private int speed;
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public ChopperMissile(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;

        speed = -17;
        //cap missile speed

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);

        }

        animation.setFrames(image);
        animation.setDelay(30 - speed);

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

    @Override
    public int getWidth() {
        //offset slightly for more realistic collision detection

        return width - 10;
    }

}
