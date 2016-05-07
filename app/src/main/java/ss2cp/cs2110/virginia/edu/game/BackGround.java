package ss2cp.cs2110.virginia.edu.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by ShaoranSun on 4/7/15.
 */
public class BackGround {

    private Bitmap image;
    private int x, y, dx;

    public BackGround(Bitmap res) {
        image = res;
        dx=GamePanel.MOVESPEED;
    }

    public void update() {

        x += dx;
        if (x < -GamePanel.WIDTH) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
        }
    }



}
