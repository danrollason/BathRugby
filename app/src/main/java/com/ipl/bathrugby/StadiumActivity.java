package com.ipl.bathrugby;

import android.app.Dialog;
import android.content.DialogInterface;
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

public class StadiumActivity extends ActionBarActivity {

    private StadiumView stadiumView;
    Dialog pixelFlashDialog;
    private Stadium stadium;
    private Seat userSeat;
    private ScheduledThreadPoolExecutor flashLogicScheduler;
    private ScheduledThreadPoolExecutor updateScheduler;
    private boolean isFlashing;
    private boolean imageMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium);
        stadiumView = (StadiumView) findViewById(R.id.stadium_view_id);
        imageMode = getIntent().getBooleanExtra("flashMode", true);

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
        startSchedules();
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

        isFlashing = false;
    }

    private void startSchedules() {
        flashLogicScheduler = new ScheduledThreadPoolExecutor(1);
        updateScheduler = new ScheduledThreadPoolExecutor(1);
        Runnable updateRunnable = new Runnable(){
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(null != pixelFlashDialog) {
                            if(!isFlashing) {
                                View stopwatch = pixelFlashDialog.findViewById(R.id.pixel_flash_stopwatch_id);
                                if (null != stopwatch) {
                                    stopwatch.setVisibility(View.GONE);
                                }
                            }

                            View pixelFlashView = pixelFlashDialog.findViewById(R.id.pixel_flash_view_id);
                            if (null != pixelFlashView) {
                                pixelFlashView.invalidate();
                            }
                        }

                        stadiumView.invalidate();
                        isFlashing = true;
                    }
                });
            }
        };
        Runnable flashRunnable = imageMode ? new BitmapFlashLogic(stadium, this) : new FlashLogic(stadium);
        new StartSchedule(stadium, flashLogicScheduler, updateScheduler, updateRunnable, flashRunnable).execute();
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
        pixelFlashDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pixelFlashDialog.setContentView(R.layout.pixel_flash);
        PixelFlashView pixelFlashView = (PixelFlashView) pixelFlashDialog.findViewById(R.id.pixel_flash_view_id);
        pixelFlashView.setUserSeat(userSeat);

        View stopwatch = pixelFlashDialog.findViewById(R.id.pixel_flash_stopwatch_id);
        if(null != stopwatch && isFlashing) {
            stopwatch.setVisibility(View.GONE);
        }

        pixelFlashDialog.show();
        pixelFlashDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                pixelFlashDialog = null;
            }
        });
    }
}
