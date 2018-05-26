package com.nikitagordia.cosin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.nikitagordia.cosin.colorAdapters.DefaultColorAdapterGB;
import com.nikitagordia.cosin.textAdapters.DefaultBinaryTextAdapter;


/**
 * Created by nikitagordia on 04.03.18.
 */

public class Cosin extends View {

    private static final double DOUBLE_PI = Math.PI * 2d;

    private ColorAdapter colorAdapter;
    private TextAdapter textAdapter;
    private OnEnd callback;

    private int count, alphaText;
    private double angle, part, intervalRad, heightMid, endingPart;
    private Paint paint, paintBack, paintText;
    private double[] cosBuff;
    private char[] textBuff;
    private float[] widthBuff;
    private float[] peekBuff;
    private long tm;

    private int rectWidth = 60;
    private double period = Math.PI;
    private double speed = 0.005d;
    private double offset = 1.5d;
    private double endingSpeed = 0.0008d;
    private boolean directionRight = false;

    public Limit<Integer> limRectWidth = new Limit<Integer>(5, 100);
    public Limit<Double> limPeriod = new Limit<Double>(0d, Math.PI * 10);
    public Limit<Double> limSpeed = new Limit<Double>(0.0001d, 0.05d);
    public Limit<Integer> limLayoutWidth= new Limit<Integer>(0, 1000);
    public Limit<Integer> limLayoutHeight = new Limit<Integer>(0, 1000);
    public Limit<Double> limOffset = new Limit<Double>(0d, 10d);

    private boolean isLoadingData;
    private boolean isEnding;

    public Cosin(Context context, AttributeSet attributesSet) {
        super(context, attributesSet);
        angle = 0;
        endingPart = 1d;
        isEnding = false;
        paint = new Paint();
        paintBack = new Paint();
        paintText = new Paint();
        paintText.setTextSize(rectWidth / 2);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paintBack.setColor(Color.argb(255, 255, 255, 255));
        paint.setColor(Color.GREEN);
        colorAdapter = new DefaultColorAdapterGB();
        textAdapter = new DefaultBinaryTextAdapter();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateProp();
    }

    private void updateProp() {

        paintText.setTextSize(rectWidth / 2);
        count = getWidth() / rectWidth;
        intervalRad = period / count;
        heightMid = getHeight() / 2d;

        cosBuff = new double[count];
        textBuff = new char[count];
        widthBuff = new float[count];
        peekBuff = new float[count + 1];

        for (int i = 0; i <= count; i++)
            peekBuff[i] = rectWidth * i;

        for (int i = 0; i < count; i++) {
            cosBuff[i] = (intervalRad * i + intervalRad * (i + 1)) / 2d;

            updateText(i);

            widthBuff[i] = peekBuff[i] + rectWidth / 2f - paintText.measureText(textBuff[i] + "") / 2f;
        }

    }

    private void updateText(int pos) {
        textBuff[pos] = textAdapter.getString(pos);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(colorAdapter.getBackgroundColor());

        if (!directionRight) angle += ((double)System.currentTimeMillis() - tm) * speed;
        else angle -= ((double)System.currentTimeMillis() - tm) * speed;
        if (angle > DOUBLE_PI) angle -= DOUBLE_PI;
        if (angle < -DOUBLE_PI) angle += DOUBLE_PI;

        if (isEnding) {
            endingPart -= ((double)System.currentTimeMillis() - tm) * endingSpeed;
            if (endingPart < 0 && callback != null) callback.onEnd();
        }

        tm = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            part = (Math.cos(cosBuff[i] + angle) + offset) / (offset + 1d);
            paint.setColor(colorAdapter.calcColor(i, part));
            canvas.drawRect(peekBuff[i], (float)(getHeight() * (1d - part * endingPart)), peekBuff[i + 1], getHeight(), paint);
            if (isLoadingData) {
                alphaText = (int)(Math.max(0, 1000d * part - 750d));
                paintText.setColor(Color.argb(alphaText, 255, 255, 255));
                if (alphaText == 0) updateText(i);
                canvas.drawText(textBuff[i] + "", widthBuff[i], (float) heightMid, paintText);
            }
        }
        invalidate();
    }

    public void setEnd(OnEnd callback) {
        isEnding = true;
        this.callback = callback;
    }

    public void setEnd() {
        isEnding = true;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if (!limSpeed.inLimit(speed)) throw new IndexOutOfBoundsException(speed + " not in limit " + limSpeed);
        this.speed = speed;
        updateProp();
    }

    public boolean isLoadingData() {
        return isLoadingData;
    }

    public void setLoadingData(boolean loadingData) {
        isLoadingData = loadingData;
        updateProp();
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(int rectWidth) {
        if (!limRectWidth.inLimit(rectWidth)) throw new IndexOutOfBoundsException(rectWidth + " not in limit " + limRectWidth);
        this.rectWidth = rectWidth;
        updateProp();
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        if (!limPeriod.inLimit(period)) throw new IndexOutOfBoundsException(period + " not in limit " + limPeriod);
        this.period = period;
        updateProp();
    }

    public int getViewWidth() {
        return getLayoutParams().width;
    }

    public void setLayoutWidth(int width) {
        if (!limLayoutWidth.inLimit(width)) throw new IndexOutOfBoundsException(width + " not in limit " + limLayoutWidth);
        getLayoutParams().width = width;
        requestLayout();
        updateProp();
    }

    public int getViewHeight() {
        return getLayoutParams().height;
    }

    public void setLayoutHeight(int height) {
        if (!limLayoutHeight.inLimit(height)) throw new IndexOutOfBoundsException(height + " not in limit " + limLayoutHeight);
        getLayoutParams().height = height;
        requestLayout();
        updateProp();
    }

    public ColorAdapter getColorAdapter() {
        return colorAdapter;
    }

    public void setColorAdapter(ColorAdapter colorAdapter) {
        this.colorAdapter = colorAdapter;
        requestLayout();
        updateProp();
    }

    public double getOffset() {
        return offset;
    }

    public TextAdapter getTextAdapter() {
        return textAdapter;
    }

    public void setTextAdapter(TextAdapter textAdapter) {
        this.textAdapter = textAdapter;
    }

    public void setOffset(double offset) {
        this.offset = offset;
        updateProp();
    }

    public boolean isDirectionRight() {
        return directionRight;
    }

    public void setDirectionRight(boolean directionRight) {
        this.directionRight = directionRight;
    }

    public class Limit<T extends Comparable<T>> {
        public T min, max;

        public Limit(T min, T max) {
            this.min = min;
            this.max = max;
        }

        public boolean inLimit(T t) {
            return min.compareTo(t) < 1 && max.compareTo(t) > -1;
        }

        @Override
        public String toString() {
            return "[" + min + ", " + max + "]";
        }
    }

    public interface OnEnd {
        void onEnd();
    }

    public  interface ColorAdapter {

        int getBackgroundColor();

        int calcColor(int numOfRect, double percentOfHeight);

    }

    public interface TextAdapter {

        char getString(int numOfRect);

    }
}
