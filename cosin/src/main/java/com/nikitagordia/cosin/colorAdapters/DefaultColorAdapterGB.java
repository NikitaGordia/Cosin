package com.nikitagordia.cosin.colorAdapters;

import android.graphics.Color;

import com.nikitagordia.cosin.Cosin;

/**
 * Created by nikitagordia on 05.03.18.
 */

public class DefaultColorAdapterGB implements Cosin.ColorAdapter {

    @Override
    public int getBackgroundColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public int calcColor(int numOfRect, double percentOfHeght) {
        return Color.argb(150, 0, (int)(255 * percentOfHeght), (int)(255 * (1d - percentOfHeght)));
    }
}
