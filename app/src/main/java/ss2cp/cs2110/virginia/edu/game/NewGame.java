package ss2cp.cs2110.virginia.edu.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class NewGame extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    TextView mainTextView;
    Button mainButton;
    EditText mainEditText;
    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    ArrayList mNameList = new ArrayList();
    ShareActionProvider mShareActionProvider;
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    SharedPreferences mSharedPreferences;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);


        // 1. Access the TextView defined in layout XML
        // and then set its text
        mainTextView = (TextView) findViewById(R.id.main_textview);
        mainTextView.setText("Greetings! Your Name:");

        // 2. Access the Button defined in layout XML
        // and listen for it here
        mainButton = (Button) findViewById(R.id.main_button);
        mainButton.setOnClickListener((android.view.View.OnClickListener) this);

        // 3. Access the EditText defined in layout XML
        mainEditText = (EditText) findViewById(R.id.main_edittext);

        // 4. Access the ListView
        mainListView = (ListView) findViewById(R.id.main_listview);

        //Create an ArrayAdapter for the ListView
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mNameList);

        // Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(mArrayAdapter);

        // 5. Set this activity to react to list items being pressed
        mainListView.setOnItemClickListener(this);


//        // 7. Greet the user, or ask for their name if new
//        displayWelcome();
    }


    @Override
    public void onClick(View v) {
        //hide keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);


        // Take what was typed into the EditText
        // and use in TextView
        name=mainEditText.getText().toString();
        mainTextView.setText("Welcome to Ghost Hunter, " + name + "!"
        );


        // Also add that value to the list shown in the ListView
        String first = ("Enter " + mainEditText.getText().toString() + "'s DAWN Hunted World!").toUpperCase();
//        String city = ("Enter " + mainEditText.getText().toString() + "'s CITY Hunted World!").toUpperCase();
//        String night = ("Enter " + mainEditText.getText().toString() + "'s Dark Hunted World!").toUpperCase();
//        String normal = ("Enter " + mainEditText.getText().toString() + "'s Bright Hunted World!").toUpperCase();
        mNameList.add(first);
//        mNameList.add(city);
//        mNameList.add(night);
//        mNameList.add(normal);
        mNameList.add("High Score");
//        mNameList.add("Credits");

        mArrayAdapter.notifyDataSetChanged();

        //make buttons and textEdit disappear after button clicked
        mainEditText.setVisibility(View.GONE);
        mainButton.setVisibility((View.GONE));


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Log the item's position and contents
        // to the console in Debug
        Log.d("omg android", position + ": " + mNameList.get(position));

        if (position == 0) {
            Toast.makeText(this, "Welcome, " + name + "!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(NewGame.this, Game.class);
            NewGame.this.startActivity(intent);
            NewGame.this.finish();

        }

        if (position == 1) {
            Intent intent = new Intent(NewGame.this, HighScore.class);
            NewGame.this.startActivity(intent);
            NewGame.this.finish();
        }
        if (position == 2) {
            Intent intent = new Intent(NewGame.this, Credits.class);
            NewGame.this.startActivity(intent);
            NewGame.this.finish();
        }

    }

//    public void displayWelcome() {
//
//        // Access the device's key-value storage
//        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
//
//        // Read the user's name,
//        // or an empty string if nothing found
//        String name = mSharedPreferences.getString(PREF_NAME, "");
//
//        if (name.length() > 0) {
//
//            // If the name is valid, display a Toast welcoming them
//            Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_LONG).show();
//        } else {
//
//            // otherwise, show a dialog to ask for their name
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            alert.setTitle("Hello!");
//            alert.setMessage("What is your name?");
//
//            // Create EditText for entry
//            final EditText input = new EditText(this);
//            alert.setView(input);
//
//            // Make an "OK" button to save the name
//            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//                public void onClick(DialogInterface dialog, int whichButton) {
//
//                    // Grab the EditText's input
//                    String inputName = input.getText().toString();
//
//                    // Put it into memory (don't forget to commit!)
//                    SharedPreferences.Editor e = mSharedPreferences.edit();
//                    e.putString(PREF_NAME, inputName);
//                    e.commit();
//
//                    // Welcome the new user
//                    Toast.makeText(getApplicationContext(), "Welcome, " + inputName + "!", Toast.LENGTH_LONG).show();
//                }
//            });
//
//            // Make a "Cancel" button
//            // that simply dismisses the alert
//            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//                public void onClick(DialogInterface dialog, int whichButton) {
//                }
//            });
//
//            alert.show();
//        }
//    }
}
