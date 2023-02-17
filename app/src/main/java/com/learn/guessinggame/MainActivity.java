package com.learn.guessinggame;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.learn.guessinggame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private EditText txtGuess;
    private TextView lblOutput;
    private int theNumber;
    private int numberOfTries = 1;
    private String toast = "";
    private int triesLeft = 7;

    public void checkGuess() {
        String guessText = txtGuess.getText().toString();
        String message = "";
        try {
            int guess = Integer.parseInt(guessText);
            if (guess < theNumber && triesLeft != 0) {
                message = guess + " is too low. Try again.";
                numberOfTries += 1;
                triesLeft -= 1;
                toast = "You have " + triesLeft + " " + "tries left!";
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_LONG).show();
            } else if (guess > theNumber && triesLeft != 0) {
                message = guess + " is too high. Try again.";
                numberOfTries += 1;
                triesLeft -= 1;
                toast = "You have " + triesLeft + " " + "tries left!";
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_LONG).show();
            } else {
                message = guess + " is correct. You win after: " + numberOfTries + " tries!" + " Let's play again!";
                newGame();
                /*
                btnPlayAgain.setVisible(true);
                btnNewButton.setBounds(85, 145, 89, 23);
                 */
            }
        } catch (Exception e) {
            message = "Enter a whole number between 1 and 100, and try again.";
        } finally {
            lblOutput.setText(message);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }

    public void newGame() {
        theNumber = (int) (Math.random() * 100 + 1);
        //lblOutput.setText("Enter a number above and click Guess!");
        //message = "Guess a number between 1 and 100:";
        numberOfTries = 1;
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
        newGame();
        btnGuess.setOnClickListener(view -> checkGuess());

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //@Override
    //public boolean onSupportNavigateUp() {
    //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    //return NavigationUI.navigateUp(navController, appBarConfiguration)
    //|| super.onSupportNavigateUp();
    //}
}
