package com.ipl.bathrugby;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

public class StadiumView extends ActionBarActivity {

    private final int ROWS = 26;
    private final int COLUMNS = 100;

    private GridView gridView;
    private int userR = 5;
    private int userC = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
    }

    private void setupView() {
        setContentView(R.layout.stadium);
        gridView = (GridView) findViewById(R.id.stadium_view_id);
        gridView.setNumColumns(COLUMNS);

        LayoutInflater inflater = LayoutInflater.from(gridView.getContext());
        for(int r = 0; r<ROWS; r++) {
            for(int c = 0; c<COLUMNS; c++) {
                View view;
                if(userR == r && userC == c) {
                    view = inflater.inflate(R.layout.seat, gridView, false);
                } else {
                    view = inflater.inflate(R.layout.seat_user, gridView, false);
                }

                gridView.addView(view);
            }
        }
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
