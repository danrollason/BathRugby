package com.ipl.bathrugby;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import com.ipl.bathrugby.models.Seat;
import com.ipl.bathrugby.models.Stadium;

/**
 * Created by local.user on 13/12/2014.
 */
public class BitmapFlashLogic implements Runnable {
    private static final int COUNTER_START_VALUE = 0;
    private static int counter = COUNTER_START_VALUE;
    private Stadium stadium;
    private Context context;
    private Bitmap flashImage;

    public BitmapFlashLogic(Stadium stadium, Context context){
        this.stadium = stadium;
        this.context = context;
        loadData();
    }
    @Override
    public void run() {
        for(int r = 0; r< Stadium.ROWS; r++) {
            for (int c = 0; c < Stadium.COLUMNS; c++) {
                Seat seat = stadium.getSeat(r, c);
                if(seat.isTaken()) {
                    int cOffsetted = c + counter;
                    if(cOffsetted < Stadium.COLUMNS) {
                        seat.setPixelColour(flashImage.getPixel(cOffsetted, r));
                    } else {
                        seat.setPixelColour(0xff000000);
                    }
                }
            }
        }
        counter++;
        if(counter > Stadium.COLUMNS) {
            counter = COUNTER_START_VALUE;
        }
    }

    private void loadData() {
        flashImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bath_rugby_logo);
        flashImage = resizeImage(flashImage, Stadium.ROWS, Stadium.COLUMNS );
    }

    private Bitmap resizeImage(Bitmap bitmapOrg, int newWidth, int newHeight)  {

        Matrix matrix = new Matrix();
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();

        // calculate the scale - in this case = 0.4f
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
                width, height, matrix, true);

        return resizedBitmap;
    }
}
