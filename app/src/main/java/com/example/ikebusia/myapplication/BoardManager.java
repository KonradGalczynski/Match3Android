package com.example.ikebusia.myapplication;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Ikebusia on 2016-11-25.
 */

public class BoardManager implements IBoardManager {

    private static final int BoardSize = 8;
    private static final int NumOfSymbols = 5;
    private Integer[][] _board;
    private Integer[] _symbols = { R.drawable.bomb,
            R.drawable.brightness, R.drawable.danger,
            R.drawable.favourites, R.drawable.flower,
            R.drawable.hint
    };
    private int bombColumn;

    public static IBoardManager Create()
    {
        return new BoardManager();
    }

    private BoardManager()
    {
        _board = new Integer[BoardSize][BoardSize];
        Random random = new Random();
        bombColumn = random.nextInt(BoardSize);
        for (int row = 0; row < BoardSize; row++)
        {
            for (int col = 0; col < BoardSize; col++) {
                if (row == 0 && col == bombColumn) {
                    _board[row][col] = _symbols[0];
                } else {
                    _board[row][col] = _symbols[random.nextInt(NumOfSymbols) + 1];

                }
            }
        }
    }

    @Override
    public int getLength() {
        return BoardSize * BoardSize;
    }

    @Override
    public int getImageAt(int position) {
        return _board[getRowFor(position)][getColumnFor(position)];
    }

    public boolean isColumnTheSame(int position1, int position2){
        return getColumnFor(position1) == getColumnFor(position2);
    }

    public boolean isRowTheSame(int position1, int position2){
        return getRowFor(position1) == getRowFor(position2);
    }

    private int getColumnFor(int position) {
        return position % BoardSize;
    }

    private int getRowFor(int position) {
        return (int)Math.floor(position / BoardSize);
    }

    @Override
    public void removeAndRefillFields(ArrayDeque<Integer> touchList) {
        for (Object element:  touchList) {
            _board[getRowFor((int)element)][getColumnFor((int)element)] = R.drawable.blank;
            Log.v("BoardManager", "removed " + (int)element);
        }

        refillBlanks(touchList);

    }

    private void refillBlanks(ArrayDeque<Integer> list){

        Integer[] tmpArray = list.toArray(new Integer[0]);
        Arrays.sort(tmpArray);

        for(int i = 0; i<tmpArray.length; i++){
            int row = getRowFor(tmpArray[i]);
            int col = getColumnFor(tmpArray[i]);
            refillField(row,col);
        }
    }

    private void refillField(int i, int y) {
        int currentRow = i;
        while (currentRow > 0)
        {
            _board[currentRow][y] = _board[currentRow - 1][y];
            currentRow--;
        }
        Random random = new Random();
        _board[0][y] = _symbols[random.nextInt(NumOfSymbols) + 1];
    }

    @Override
    public boolean isFinished() {
        for(int col = 0; col < BoardSize; col++){
            if(_board[BoardSize-1][col] == _symbols[0]){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean arePointsVerticallyOrHorizontallyAligned(int currTouch, Integer last) {
        return isColumnTheSame(currTouch, last) || isRowTheSame(currTouch, last);
    }

    @Override
    public boolean isImageTheSame(int currTouch, int firstTouch) {
        return getImageAt(currTouch) == getImageAt(firstTouch);
    }

}
