package com.learn.guessinggame;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.learn.guessinggame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private EditText txtGuess;
    private TextView lblOutput;
    private int theNumber;
    private int numberOfTries;
    private String toast = "";
    private int triesLeft = 7;
    private int range = 100;
    private TextView lblRange;
    private int maxTries;

    public void checkGuess() {
        String guessText = txtGuess.getText().toString();
        String message = "";
        try {
            int guess = Integer.parseInt(guessText);
            numberOfTries++;
            if (guess < theNumber) {
                message = guess + " is too low. You have " +(maxTries-numberOfTries)+ " tries left.";
                //numberOfTries += 1;
                //maxTries -= 1;
                toast = "You have " +(maxTries-numberOfTries)+ " " + "tries left!";
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            } else if (guess > theNumber) {
                message = guess + " is too high. You have " +(maxTries-numberOfTries)+ " tries left.";
                //numberOfTries += 1;
                //maxTries -= 1;
                toast = "You have " +(maxTries-numberOfTries)+ " " + "tries left!";
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            } else {
                message = guess + " is correct. You win after: " + numberOfTries + " tries!" + " Let's play again!";
                toast = "You won! " + theNumber + "was the number, congratulations!";
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = preferences.getInt("gamesWon", 0) + 1;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("gamesWon", gamesWon);
                editor.apply();
                newGame();
                /*
                btnPlayAgain.setVisible(true);
                btnNewButton.setBounds(85, 145, 89, 23);
                 */
            } /*else if (guess > theNumber && numberOfTries == maxTries) {
                message = guess + " is too high. Try again.";
                //numberOfTries += 1;
                maxTries -= 1;
                toast = "Its your last try!";
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            } else if (guess < theNumber && numberOfTries == maxTries) {
                message = guess + " is too high. Try again.";
                //numberOfTries += 1;
                maxTries -= 1;
                toast = "Its your last try!";
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            } else {
                message = "You run out of tries! Try next time";
                newGame();
            }*/
        } catch (Exception e) {
            message = "Enter a whole number between 1 and " + range + ", and try again.";
        } finally {
            if (numberOfTries >= maxTries)
            {
                message = "Sorry, you're out of tries. "+theNumber+" was the number.";
                Toast.makeText(MainActivity.this, message,
                        Toast.LENGTH_LONG).show();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesLost = preferences.getInt("gamesLost", 0) + 1;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("gamesLost", gamesLost);
                editor.apply();
                newGame();
            }
            lblOutput.setText(message);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }

    public void newGame() {
        theNumber = (int)(Math.random() * range + 1);
        maxTries = (int)(Math.log(range)/Math.log(2)+1);
        lblRange.setText(getString(R.string.range, 1, range));
        txtGuess.setText(getString(R.string.settext2, range/2));
        txtGuess.requestFocus();
        txtGuess.selectAll();
        //lblOutput.setText("Enter a number above and click Guess!");
        //message = "Guess a number between 1 and 100:";
        numberOfTries = 0;
        //btnPlayAgain.setVisible(false);
        //btnNewButton.setBounds(172, 145, 89, 23);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.learn.guessinggame.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txtGuess = findViewById(R.id.txtGuess);
        Button btnGuess = (Button) findViewById(R.id.btnGuess);
        lblOutput = (TextView) findViewById(R.id.lblOutput);
        btnGuess.setOnClickListener(view -> checkGuess());
        lblRange = (TextView) findViewById(R.id.TextView2);
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        range = preferences.getInt("range", 100);
        maxTries = (int)(Math.log(range)/Math.log(2)+1);
        newGame();

        txtGuess.setOnEditorActionListener((textView, i, keyEvent) -> {
            checkGuess();
            return true;
        });



        setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();
        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case R.id.action_settings:
                final CharSequence[] items = {"1 to 10", "1 to 100", "1 to 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select the Range:");
                builder.setItems(items, (dialog, item1) -> {
                    switch(item1) {
                        case 0:
                            range = 10;
                            storeRange(10);
                            newGame();
                            break;

                        case 1:
                            range = 100;
                            storeRange(100);
                            newGame();
                            break;

                        case 2:
                            range = 1000;
                            storeRange(1000);
                            newGame();
                            break;
                    }
                    dialog.dismiss();
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_newgame:
                newGame();
                return true;
            case R.id.action_gamestats:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = preferences.getInt("gamesWon", 0);
                int gamesLost = preferences.getInt("gamesLost", 0);
                //int gamesTotal = preferences.getInt("gamesTotal", 0);
                int gamesTotal = gamesWon + gamesLost;
                int percent = Math.round((gamesWon * 100)/(gamesTotal));
                AlertDialog statDialog = new AlertDialog.Builder(MainActivity.this).create();
                statDialog.setTitle("Guessing Game Stats");
                statDialog.setMessage("You have won " + gamesWon +" out of " + gamesTotal + " games." + "\r\n" +
                        "\r\n" +
                        "Win/loss ratio: " + percent + "%! \nWay to go!");
                statDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                statDialog.show();
                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage("Made by Patryk Kryszczuk in 2023y." + "\r\n" +
                        "With much love and respect, thank you for playing my game." + "\r\n" +
                        "This game was created for learning android studio, completely in Java.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) -> dialog.dismiss());
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void storeRange(int newRange) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("range", newRange);
        editor.apply();
    }

    //@Override
    //public boolean onSupportNavigateUp() {
    //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    //return NavigationUI.navigateUp(navController, appBarConfiguration)
    //|| super.onSupportNavigateUp();
    //}
}
