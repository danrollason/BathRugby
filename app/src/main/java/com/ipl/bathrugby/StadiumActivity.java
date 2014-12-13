package com.ipl.bathrugby;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ipl.bathrugby.models.Seat;
import com.ipl.bathrugby.models.Stadium;
import com.ipl.bathrugby.views.PixelFlashView;
import com.ipl.bathrugby.views.StadiumView;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StadiumActivity extends ActionBarActivity {

    private StadiumView stadiumView;
    private PixelFlashView pixelFlashView;
    private Stadium stadium;
    private Seat userSeat;
    private ScheduledThreadPoolExecutor flashLogicScheduler;
    private ScheduledThreadPoolExecutor updateScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium);
        stadiumView = (StadiumView) findViewById(R.id.stadium_view_id);

        Button nextButton = (Button) findViewById(R.id.go_to_mex_wave_id);
        nextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startFlashActivity();
            }
        });

        setupStadium();
}

    @Override
    protected void onResume() {
        super.onResume();
        startFlashLogic();
        startUpdater();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != flashLogicScheduler) {
            flashLogicScheduler.shutdownNow();
        }

        if(null != updateScheduler) {
            updateScheduler.shutdownNow();
        }
    }

    private void startFlashLogic() {
        flashLogicScheduler = new ScheduledThreadPoolExecutor(1);
        new StartSchedule(stadium, flashLogicScheduler).execute();
    }

    private void startUpdater() {
        updateScheduler = new ScheduledThreadPoolExecutor(1);
        Runnable flashRunnable = new Runnable(){
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stadiumView.invalidate();
                        if(null != pixelFlashView) {
                            pixelFlashView.invalidate();
                        }
                    }
                });
            }
        };
        updateScheduler.scheduleAtFixedRate(flashRunnable,0,100, TimeUnit.MILLISECONDS);
    }

    private void setupStadium() {
        userSeat = (Seat) getIntent().getSerializableExtra("userSeat");
        stadium = new Stadium(userSeat);
        stadiumView.setStadium(stadium);
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

    private void startFlashActivity() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.pixel_flash);
        pixelFlashView = (PixelFlashView) dialog.findViewById(R.id.pixel_flash_view_id);
        pixelFlashView.setUserSeat(userSeat);
        dialog.show();
    }
}
