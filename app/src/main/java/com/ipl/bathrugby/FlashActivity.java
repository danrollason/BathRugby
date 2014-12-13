package com.ipl.bathrugby;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import com.ipl.bathrugby.models.Seat;
import com.ipl.bathrugby.util.SystemUiHider;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FlashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flash);

        StartSchedule startSched = new StartSchedule((Seat) (getIntent().getSerializableExtra("userSeat")),this);
        startSched.execute();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getActionBar().hide();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class StartSchedule extends AsyncTask {

        private Seat mySeat;
        private Activity myActivity;

        public StartSchedule(Seat mySeat, Activity myActivity){
            this.mySeat = mySeat;
            this.myActivity = myActivity;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            SntpClient client = new SntpClient();
            long now = 0;
            if (client.requestTime("pool.ntp.org",10000)) {
                now = client.getNtpTime() + SystemClock.elapsedRealtime() - client.getNtpTimeReference();
            }


            Log.i("Bath Rugby", "Now " + now);

            long offset = ((( now / 10000) * 10000) + 10000) - now;

            Log.i("Bath Rugby", "Offset " + offset);

            ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
            FlashTask task = new FlashTask(mySeat,4,myActivity);
            scheduler.scheduleAtFixedRate(task,offset,1000, TimeUnit.MILLISECONDS);

            return null;
        }
    }

}
