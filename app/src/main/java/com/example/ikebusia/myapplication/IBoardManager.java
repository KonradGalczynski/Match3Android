package com.example.ikebusia.myapplication;

import android.util.Pair;

import java.util.List;

/**
 * Created by Ikebusia on 2016-11-25.
 */
public interface IBoardManager {
    int GetLength();

    int GetImageAt(int position);

    void RemoveFields(int startPosition, int endPosition);

    boolean CanRemoveFields(int prevTouch, int currTouch);

    void RefillBlanks(int startPosition, int endPosition);
}
