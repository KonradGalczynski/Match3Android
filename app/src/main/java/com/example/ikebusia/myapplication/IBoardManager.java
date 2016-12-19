package com.example.ikebusia.myapplication;

import android.util.Pair;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ikebusia on 2016-11-25.
 */
public interface IBoardManager {
    int getLength();

    int getImageAt(int position);

    boolean isColumnTheSame(int position1, int position2);

    boolean isRowTheSame(int position1, int position2);

    boolean isFinished();

    boolean arePointsVerticallyOrHorizontallyAligned(int currTouch, Integer last);

    boolean isImageTheSame(int currTouch, int firstTouch);

    void removeAndRefillFields(ArrayDeque<Integer> touchList);
}
