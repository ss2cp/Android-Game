package ss2cp.cs2110.virginia.edu.game;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SplashScreen extends ActionBarActivity {

    public void switchToNewGame (View view){
        //switch to new game entry screen when newGame button clicked
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }

    public void switchToGame (View view){
        //switch to game screen when button clicked
        Intent intent = new Intent(this,Game.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Button newGame = (Button) findViewById(R.id.newGame);
        Typeface t = Typeface.createFromAsset(getAssets(),"splashFont.ttf");
        newGame.setText("New Game");
        newGame.setTypeface(t);
        Button loadGame = (Button) findViewById(R.id.LoadGame);
        loadGame.setText("Load Game");
        loadGame.setTypeface(t);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
