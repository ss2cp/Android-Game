package ss2cp.cs2110.virginia.edu.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    private long smokeStartTime;
    private MainThread thread;
    private BackGround bg;
    private Player player;
    private ArrayList<Smokepuff> smoke;
    private ArrayList<Zombie> zombies;
    private ArrayList<ChopperMissile> chopperMissiles;
    private ArrayList<Explosion> explosions;
    private ArrayList<Gift> gifts;
    private ArrayList<Coin> coins;
    private long missileStartTime;
    private long giftStartTime;
    private Random rand = new Random();
    private boolean newGameCreated;
    boolean hit;
    private long startClickTime = 0;
    private long clickDuration = 0;
    private boolean firstGame = true;
    private boolean died = false;


    public GamePanel(Context context) {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);


        //make gamePanel focusable so it can handle events
        setFocusable(true);


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

//        if ( this.getResources().getString(R.string.bg).equals("1")){
        bg = new BackGround(BitmapFactory.decodeResource(getResources(), R.drawable.g));

//        }
//        if ( bgName == 1){
//            bg = new BackGround(BitmapFactory.decodeResource(getResources(), R.drawable.bg2));
//        }
//        if ( bgName == 2){
//            bg = new BackGround(BitmapFactory.decodeResource(getResources(), R.drawable.bg3));
//        }
//        if ( bgName == 3){
//            bg = new BackGround(BitmapFactory.decodeResource(getResources(), R.drawable.g));
//        }

        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.bird), 50, 58, 8);
        smoke = new ArrayList<Smokepuff>();
        smokeStartTime = System.nanoTime();
        zombies = new ArrayList<Zombie>();
        missileStartTime = System.nanoTime();
        giftStartTime = System.nanoTime();
        chopperMissiles = new ArrayList<ChopperMissile>();
        explosions = new ArrayList<Explosion>();
        gifts = new ArrayList<Gift>();
        coins = new ArrayList<Coin>();
        hit = false;


        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {

//        if (event.getPointerCount() == 1) {
            if (!player.getPlaying()) {
                System.out.println("ACTIONDOWN & Player is not playing");
                player.resetScore();
                newGame();
                player.setPlaying(true);
            }
            startClickTime = Calendar.getInstance().getTimeInMillis();
            firstGame = false;
            died = false;

            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
            if (clickDuration < 200 && event.getX() > WIDTH / 4 * 3) {
                clickDuration = 0;
                if (player.getTotalCoin() > 0) {
                    chopperMissiles.add(new ChopperMissile((BitmapFactory.decodeResource(getResources(), R.drawable.fireball)),
                            player.getX() - 10, player.getY(), 40, 45, 5));
                    player.minusCoin();
                }
            } else {

                player.setY((int) event.getY() - player.height);
                player.setX((int) event.getX() - player.width);

                if (event.getPointerCount() > 1) {
                    if (player.getTotalCoin() > 0) {
                        chopperMissiles.add(new ChopperMissile((BitmapFactory.decodeResource(getResources(), R.drawable.fireball)),
                                player.getX() - 10, player.getY(), 40, 45, 5));
                        player.minusCoin();
                    }
                }
            }
            return true;

        }


        if (event.getAction() == MotionEvent.ACTION_UP) {

            return true;
        }


        return super.onTouchEvent(event);
    }

    public void update() {
        if (player.getPlaying()) {

            bg.update();
            player.update();


            //add zombies on timer
            long missileElapsed = (System.nanoTime() - missileStartTime) / 1000000;
            //score higher, launch faster
            if (missileElapsed > (2000 - player.getScore() / 4)) {

                //first zombie always goes down the middle
                if (zombies.size() == 0) {
                    zombies.add(new Zombie((BitmapFactory.decodeResource(getResources(), R.drawable.ghosts)),
                            WIDTH + 10, HEIGHT / 2, 77, 98, player.getScore(), 7));

                } else {
                    //cap y so that it wont go under the screen
                    double y = (int) (rand.nextDouble() * (HEIGHT));
                    if (y + 100 > HEIGHT) {
                        y = HEIGHT - 100;
                    }
                    zombies.add(new Zombie(BitmapFactory.decodeResource(getResources(), R.drawable.ghosts),
                            WIDTH + 10, (int) y, 77, 98, player.getScore(), 7))
                    ;


                }
                //reset timer
                missileStartTime = System.nanoTime();
            }


            long giftElapsed = (System.nanoTime() - giftStartTime) / 1000000;

            //every 8000 nanoseconds, add a new parachute of gift
            if (giftElapsed > (8000)) {

                gifts.add(new Gift(BitmapFactory.decodeResource(getResources(), R.drawable.parachutes),
                        (int) (rand.nextDouble() * WIDTH), -10, 50, 63, 3))
                ;

                //reset timer
                giftStartTime = System.nanoTime();
            }


            //loop through every choppermissile
            for (int i = 0; i < chopperMissiles.size(); i++) {
                chopperMissiles.get(i).update();

                //if missile meets zombie, both gone
                for (int j = 0; j < zombies.size(); j++) {
                    if (collision(chopperMissiles.get(i), zombies.get(j))) {
                        player.setScore(player.getScore() + 20);
                        hit = true;
                        explosions.add(new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion1), zombies.get(j), 150, 150, 2));
                        coins.add(new Coin(BitmapFactory.decodeResource(getResources(), R.drawable.coin),
                                zombies.get(j).getX(), zombies.get(j).getY(), 44, 40, 10, System.nanoTime()));

                        zombies.remove(j);
                        chopperMissiles.remove(i);

//                        break;

                    }
                }

                if (chopperMissiles.get(i).getX() > GamePanel.WIDTH + 100) {
                    chopperMissiles.remove(i);
//                    break;
                }
            }


            //loop through every coin
            for (int i = 0; i < coins.size(); i++) {
                //update coins
                coins.get(i).update();

                //if player receive coin
                if (collision((coins.get(i)), player)) {

                    player.addCoin();

                    //start counting time
                    coins.get(i).setStartTime(System.nanoTime());

                    //hide coin and show +10
                    coins.get(i).setShow10(true);
                }
                //update coin i on timer
                coins.get(i).setElapse((System.nanoTime() - coins.get(i).getStartTime()) / 1000000);

                //eat coin after 500 nanoSeconds, or coin hanging there for more than 10000 nano, destroy coin
                if (coins.get(i).getElapse() / 1000000 > 200 || (System.nanoTime() - coins.get(i).getCreateTime()) / 1000000 > 2000) {
                    coins.remove(i);
                }

            }

            //loop through every zombie
            for (int i = 0; i < zombies.size(); i++) {
                //update missile
                zombies.get(i).update();
                if (collision(zombies.get(i), player)) {
                    zombies.remove(i);
                    player.minusLife();
                    if (player.getLife() == -1) {
                        died = true;
                        clearEverything();
                        player.setPlaying(false);
                    }

                }
                //remove zombie if it is way out of the screen
                if (zombies.get(i).getX() < -100) {
                    zombies.remove(i);
//                    break;
                }

            }


            //loop through every gift
            for (int i = 0; i < gifts.size(); i++) {

                if (collision(gifts.get(i), player)) {

                    gifts.remove(i);
                    double tempForGoodOrBad = rand.nextDouble();

                    //50% chance clear all ghosts or adding 5 more
                    if (tempForGoodOrBad > 0.5) {
                        for (int j = 0; j < HEIGHT; j = j + 20) {
                            chopperMissiles.add(new ChopperMissile((BitmapFactory.decodeResource(getResources(), R.drawable.fireball)),
                                    20, j, 40, 45, 5));
                        }
                    } else {

                        for (int k = 0; k < 5; k++) {
                            double y = (int) (rand.nextDouble() * (HEIGHT));
                            if (y + 100 > HEIGHT) {
                                y = HEIGHT - 100;
                            }
                            zombies.add(new Zombie(BitmapFactory.decodeResource(getResources(), R.drawable.ghosts),
                                    WIDTH + 10, (int) y, 77, 98, player.getScore(), 7))
                            ;

                        }
                    }
                }
                if (gifts.get(i).getY() > GamePanel.HEIGHT + 10) {
                    gifts.remove(i);
                }

                gifts.get(i).update();
            }


            if (hit) {
                for (int k = 0; k < explosions.size(); k++) {
                    if (explosions.get(k).getOriginalX() - explosions.get(k).getX() > 17 * explosions.get(k).getSpeed()) {
                        explosions.remove(k);
                    }
                    explosions.get(k).update();
                }
            }


            //add smoke puffs
            long elapsed = (System.nanoTime() - smokeStartTime) / 1000000;
            if (elapsed > 120) {
                smoke.add(new Smokepuff(player.getX(), player.getY() + 10));
                smokeStartTime = System.nanoTime();
            }

            for (int i = 0; i < smoke.size(); i++) {
                smoke.get(i).update();
                if (smoke.get(i).getX() < -10) {
                    smoke.remove(i);
                }


            }
        } else

        {


            newGameCreated = false;
            if (!newGameCreated) {
                gameOver(player.getScore());
            }
        }

    }

    public void gameOver(int score) {


    }

    public boolean collision(GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / ((HEIGHT) * 1.f);

        if (canvas != null) {
            final int savedState = canvas.save();


            canvas.scale(scaleFactorX, scaleFactorY);


            //draw background
            bg.draw(canvas);


            //draw gifts
            for (Gift g : gifts) {
                g.draw(canvas);
            }


            //draw player
            player.draw(canvas);


            //draw score
            if (!died) {
                player.resetPaint();
                player.drawScore(canvas);
            }


            if (!player.getPlaying() && !firstGame) {
                player.showScore(canvas);
            }

            //draw smokepuffs
            for (Smokepuff sp : smoke) {
                sp.draw(canvas);
            }

            //draw zombies
            for (Zombie m : zombies) {
                if ((m.getX() - player.getX() < 100) && (m.getX() - player.getX() > 100 - player.width) && (Math.abs(m.getY() - player.getY())) < 100) {
                    m.danger(canvas);
                    m.altAdd();
                }
                m.draw(canvas);
            }

            //draw choppermissles
            for (ChopperMissile cm : chopperMissiles) {
                cm.draw(canvas);
            }

            //draw explosions
            for (Explosion e : explosions) {
                e.draw(canvas);
            }

            //draw coins or +10
            for (Coin c : coins) {
                if (c.getShow10()) {
                    c.draw10(canvas);
                } else {
                    c.draw(canvas);
                }
            }


            canvas.restoreToCount(savedState);


        }
    }

    //Game over and reset everything
    public void newGame() {
        //TODO: make sure this runs after player dies
        player.setY((HEIGHT / 2));
        player.setX(100);
        newGameCreated = true;
        player.resetLife();
        System.out.println("newGame is called");

    }

    public void clearEverything(){
        zombies.clear();
        smoke.clear();
        chopperMissiles.clear();
        explosions.clear();
        gifts.clear();
        coins.clear();
        player.setY(10000);
        player.setX(10000);
        player.resetLife();
    }


}