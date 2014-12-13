package com.ipl.bathrugby;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ipl.bathrugby.models.Seat;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextButton = (Button) findViewById(R.id.seatSelectionNext);
        nextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                EditText rowSelection = (EditText) findViewById(R.id.rowSelection);
                int rowNumber = convertRowLetterToNumber(rowSelection.getText().toString().charAt(0));

                EditText seatSelection = (EditText) findViewById(R.id.seatSelection);
                int seatNumber = Integer.parseInt(seatSelection.getText().toString());

                // Create a seat object to pass to the next activity
                Seat mySeat = new Seat(rowNumber,seatNumber,true,true);

                startStadiumViewActivity(mySeat);

            }
        });
    }

    private int convertRowLetterToNumber(Character rowLetter){
        // Get the ascii code of the character and subtract 65 (A = 65)
        return ((int) rowLetter) - 65;
    }

    private void startStadiumViewActivity(Seat mySeat) {
        Intent myIntent = new Intent(this,StadiumView.class);
        myIntent.putExtra("userSeat", mySeat);
        startActivity(myIntent);
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
}
