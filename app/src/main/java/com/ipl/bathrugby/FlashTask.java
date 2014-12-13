package com.ipl.bathrugby;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.ipl.bathrugby.models.Seat;

/**
 * Created by local.user on 13/12/2014.
 */
public class FlashTask implements Runnable {

    private Seat mySeat;
    private int totalNumberOfSeats;
    private static int counter = 0;
    private Activity flashActivity;


    public FlashTask(Seat mySeat, int totalNumberOfSeats, Activity flashActivity){
        this.mySeat = mySeat;
        this.totalNumberOfSeats = totalNumberOfSeats;
        this.flashActivity = flashActivity;
    }

    @Override
    public void run() {
        counter++;
        if (counter == mySeat.getColumn()) {
            // Turn on
            Log.i("BathRugby", "Turned on");
            flashActivity.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    flashActivity.findViewById(R.id.activity_flash_id).setBackgroundColor(0xffffffff);
                }
            });
        } else {
            // Turn off
            Log.i("BathRugby","Turned off");
            flashActivity.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    flashActivity.findViewById(R.id.activity_flash_id).setBackgroundColor(0xff000000);
                }
            });
        }
        if (counter == totalNumberOfSeats) {
            counter = -1;
        }
    }
}
