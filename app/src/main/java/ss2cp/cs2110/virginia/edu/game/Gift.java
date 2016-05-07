package ss2cp.cs2110.virginia.edu.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by ShaoranSun on 4/23/15.
 */
public class Gift extends GameObject {
    private int speed;
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public Gift(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;

        speed = 1;
        //cap gift speed

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);

        }

        animation.setFrames(image);
        animation.setDelay(120);

    }

    public void update() {
        y += speed;
        animation.update();
    }

    public void draw(Canvas canvas) {
        try {
            Paint paint = new Paint();
            paint.setTextSize(20);
            paint.setColor(-65536);
            canvas.drawBitmap(animation.getImage(), x, y, null);
            canvas.drawText("EAT ME!",super.x,super.y+super.getHeight(),paint);
        } catch (Exception e) {
        }
    }

    public void giftReceived(Canvas canvas){
        Paint paint = new Paint();

        paint.setColor(-65536);
        paint.setFakeBoldText(true);
        paint.setTextSize(40);


        canvas.drawText("CLEAR!", 200, 100, paint);
    }



}


