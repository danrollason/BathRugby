package com.ipl.bathrugby.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.ipl.bathrugby.models.Seat;

public class PixelFlashView extends ViewGroup {
    private int width = 0;
    private int height = 0;
    private Seat userSeat = new Seat();

    public void setUserSeat(Seat userSeat) {
        this.userSeat = userSeat;
    }

    public PixelFlashView(Context context) {
        super(context);
        init();
    }

    public PixelFlashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint seatPaint = new Paint();
        seatPaint.setColor(userSeat.getPixelColour());
        canvas.drawRect(0, 0, width, height, seatPaint);
    }
}
