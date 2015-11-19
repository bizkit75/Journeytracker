package com.mayer.lucas.journeytracker;

/**
 * Created by lulz on 29/10/2015.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class GraphView extends View {

    private Paint paint;
    protected static ArrayList<Float> ArrayListSpeed = new ArrayList<Float>();
    float[] axisX = new float[100];

    float average;


    public GraphView(Context context) {
        super(context);
        paint = new Paint();

    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(Color.BLACK);
        init();
        float border = 20;
        float startline = border * 2;
        float height = getHeight();
        float width = getWidth() - 1;
        float[] arrayRedline = {(float) (height * 0.1666), (float) (height * 0.1666), (float) (height * (0.1666 * 2)), (float) (height * (0.1666 * 3)), (float) (height * (0.1666 * 4)), (float) (height * (0.1666 * 5))};


        paint.setColor(Color.WHITE);
        for (int y = 0; y < arrayRedline.length; y++) {
            canvas.drawLine(0, arrayRedline[y], width, arrayRedline[y], paint);
            canvas.drawLine(0, arrayRedline[y] + 1, width, arrayRedline[y] + 1, paint);
        }

        if (!ArrayListSpeed.isEmpty()) {
            paint.setColor(Color.GREEN);
            System.out.println("geth height" + axisX[1]);
            for (int i = 0; i < ArrayListSpeed.size(); i++) {
                if (i == 0) {
                } else {
                    canvas.drawLine(axisX[i - 1], (float) (getHeight() * ((60 - ArrayListSpeed.get(i - 1)) * 1.666) / 100), axisX[i], (float) (getHeight() * ((60 - ArrayListSpeed.get(i)) * 1.666) / 100), paint);
                }
            }
            paint.setColor(Color.RED);
            float count = 0;
            float average = 0;
            for (int i = 0; i < ArrayListSpeed.size(); i++) {
                count += ArrayListSpeed.get(i);
            }
            average = count / ArrayListSpeed.size();
            canvas.drawLine(0, (float) (getHeight() * ((60 - average) * 1.666) / 100), getWidth(), (float) (getHeight() * ((60 - average) * 1.666) / 100), paint);
            MainActivity.UpdateAverage(average);
        }
        ArrayListSpeed.clear();
    }

    protected void init() {
        float size = getWidth() / 100;
        for (int i = 0; i < axisX.length; i++) {
            axisX[i] = size * i;
        }
    }

    public void CleartArrayListSpeed() {
        ArrayListSpeed.clear();
    }
}
