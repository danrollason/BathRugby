package com.ipl.bathrugby.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.ipl.bathrugby.models.Seat;

public class StadiumView extends ViewGroup {
    public static final int ROWS = 20;
    public static final int COLUMNS = 40;

    private int width = 0;
    private int height = 0;
    private int seatW = 0;
    private int leftOffset = 0;
    private Paint seatTaken;
    private Paint seatUser;
    private Paint seatEmpty;
    private Paint border;


    private Seat[][] seats = new Seat[0][0];

    public void setSeats(Seat[][] seats) {
        this.seats = seats;
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
        seatTaken = new Paint();
        seatTaken.setARGB(255, 0, 255, 0);

        seatUser = new Paint();
        seatUser.setARGB(255, 0, 0, 0);

        seatEmpty = new Paint();
        seatEmpty.setARGB(255, 80, 80, 80);

        border = new Paint();
        border.setARGB(255, 0,0,0);
        border.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        setMeasuredDimension(width, height);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        seatW = (int) Math.floor(Math.min(((double)height)/ROWS, ((double)width)/COLUMNS));
        leftOffset = (int)(0.5 * (width - seatW * COLUMNS));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Row 0 is at the bottom
        // Column 0 is on the left
        for(int r = 0; r<ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                drawSeat(leftOffset + c * seatW, r * seatW, leftOffset + (c + 1) * seatW, (r + 1) * seatW, seats[r][c], canvas);
            }
        }

    }

    private void drawSeat(int left, int top, int right, int bottom, Seat seat, Canvas canvas) {
        Paint seatPaint = seat.isTaken() ? seatTaken : seatEmpty;
        canvas.drawRect(left, top, right, bottom, seatPaint);
        canvas.drawRect(left, top, right, bottom, border);

        if(seat.isUser()) {
            canvas.drawText("x", (float)(0.5*(left + right)), (float)(0.5*(left + right)), seatUser);
        }
    }
}
