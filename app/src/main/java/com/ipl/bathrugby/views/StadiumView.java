package com.ipl.bathrugby.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.ipl.bathrugby.models.Seat;
import com.ipl.bathrugby.models.Stadium;

public class StadiumView extends ViewGroup {
    private int width = 0;
    private int height = 0;
    private int seatW = 0;
    private int leftOffset = 0;
    private Paint seatUser;
    private Paint seatEmpty;
    private Paint border;

    private Stadium stadium = new Stadium();

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public StadiumView(Context context) {
        super(context);
        init();
    }

    public StadiumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        seatEmpty = new Paint();
        seatEmpty.setARGB(255, 80, 80, 80);

        border = new Paint();
        border.setARGB(255, 0,0,0);
        border.setStyle(Paint.Style.STROKE);

        seatUser = new Paint();
        seatUser.setARGB(255, 0, 0, 255);
        seatUser.setStyle(Paint.Style.STROKE);
        seatUser.setStrokeWidth(5);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        setMeasuredDimension(width, height);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        seatW = (int) Math.floor(Math.min(((double)height)/Stadium.ROWS, ((double)width)/Stadium.COLUMNS));
        leftOffset = (int)(0.5 * (width - seatW * Stadium.COLUMNS));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Row 0 is at the bottom
        // Column 0 is on the left
        for(int r = 0; r< Stadium.ROWS; r++) {
            for (int c = 0; c < Stadium.COLUMNS; c++) {
                drawSeat(leftOffset + c * seatW, r * seatW, leftOffset + (c + 1) * seatW, (r + 1) * seatW, stadium.getSeat(r,c), canvas);
            }
        }
    }

    private void drawSeat(int left, int top, int right, int bottom, Seat seat, Canvas canvas) {
        Paint seatPaint;

        if(seat.isTaken()) {
            seatPaint = new Paint();
            seatPaint.setColor(seat.getPixelColour());
        } else {
            seatPaint = seatEmpty;
        }

        canvas.drawRect(left, top, right, bottom, seatPaint);
        canvas.drawRect(left, top, right, bottom, border);

        if(seat.isUser()) {
            canvas.drawRect(left, top, right, bottom, seatUser);
        }
    }
}
