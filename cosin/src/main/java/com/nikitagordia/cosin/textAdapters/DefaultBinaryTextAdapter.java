package com.nikitagordia.cosin.textAdapters;

import com.nikitagordia.cosin.Cosin;

import java.util.Date;
import java.util.Random;

/**
 * Created by nikitagordia on 05.03.18.
 */

public class DefaultBinaryTextAdapter implements Cosin.TextAdapter {

    private static final char ZERO = '0';
    private static final char ONE = '1';

    private Random random;

    public DefaultBinaryTextAdapter() {
        random = new Random(new Date().getTime());
    }

    @Override
    public char getString(int numOfRect) {
        if (random.nextBoolean()) return ONE; else return ZERO;
    }
}
