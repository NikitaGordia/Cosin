package com.nikitagordia.cosin.textAdapters;

import com.nikitagordia.cosin.Cosin;

/**
 * Created by nikitagordia on 05.03.18.
 */

public class WordTextAdapter implements Cosin.TextAdapter {

    private String word;

    public WordTextAdapter(String word) {
        this.word = word;
    }

    @Override
    public char getString(int numOfRect) {
        if (word.isEmpty()) return ' ';
        return word.charAt(numOfRect % word.length());
    }
}
