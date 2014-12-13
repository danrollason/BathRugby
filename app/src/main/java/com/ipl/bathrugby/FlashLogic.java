package com.ipl.bathrugby;

import com.ipl.bathrugby.models.Seat;
import com.ipl.bathrugby.models.Stadium;

/**
 * Created by local.user on 13/12/2014.
 */
public class FlashLogic implements Runnable {
    private static int counter = 0;
    private Stadium stadium;

    public FlashLogic(Stadium stadium){
        this.stadium = stadium;
    }

    @Override
    public void run() {
        for(int r = 0; r< Stadium.ROWS; r++) {
            for (int c = 0; c < Stadium.COLUMNS; c++) {
                Seat seat = stadium.getSeat(r, c);
                if(seat.isTaken()) {
                    if(counter == seat.getColumn()) {
                        seat.setPixelColour(0xffffffff);
                    } else {
                        seat.setPixelColour(0xff000000);
                    }
                }
            }
        }
        counter++;
        if(counter > Stadium.COLUMNS) {
            counter = 0;
        }
    }
}
