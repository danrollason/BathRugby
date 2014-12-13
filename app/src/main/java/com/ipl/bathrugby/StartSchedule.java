package com.ipl.bathrugby;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.ipl.bathrugby.models.Stadium;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StartSchedule extends AsyncTask {

    private Stadium stadium;
    private ScheduledThreadPoolExecutor scheduler;

    public StartSchedule(Stadium stadium, ScheduledThreadPoolExecutor scheduler){
        this.stadium = stadium;
        this.scheduler = scheduler;
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

        FlashLogic flashLogic = new FlashLogic(stadium);
        scheduler.scheduleAtFixedRate(flashLogic,offset,1000, TimeUnit.MILLISECONDS);

        return null;
    }
}