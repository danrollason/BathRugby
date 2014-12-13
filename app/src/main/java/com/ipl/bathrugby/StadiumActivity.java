package com.ipl.bathrugby;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ipl.bathrugby.models.Seat;
import com.ipl.bathrugby.views.StadiumView;

public class StadiumActivity extends ActionBarActivity {

    private StadiumView stadiumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium);
        stadiumView = (StadiumView) findViewById(R.id.stadium_view_id);
        setupSeats();
    }

    private void setupSeats() {
        Seat userSeat = (Seat) getIntent().getSerializableExtra("userSeat");

        Seat[][] seats = new Seat[StadiumView.ROWS][StadiumView.COLUMNS];
        for(int r = 0; r<StadiumView.ROWS; r++) {
            for (int c = 0; c <StadiumView.COLUMNS; c++) {
                seats[r][c] = new Seat(r, c, false, false);
            }
        }
        seats[userSeat.getRow()][userSeat.getColumn()] = userSeat;

        stadiumView.setSeats(seats);
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
