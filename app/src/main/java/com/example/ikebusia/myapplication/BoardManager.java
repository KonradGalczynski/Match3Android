package com.example.ikebusia.myapplication;

import android.util.Pair;

import java.util.Random;

/**
 * Created by Ikebusia on 2016-11-25.
 */

public class BoardManager implements IBoardManager {

    private static final int BoardSize = 8;
    private static final int NumOfSymbols = 5;
    private Integer[][] _board;
    private Integer[] _symbols = {
            R.drawable.brightness, R.drawable.danger,
            R.drawable.favourites, R.drawable.flower,
            R.drawable.hint
    };

    public static IBoardManager Create()
    {
        return new BoardManager();
    }

    private BoardManager()
    {
        _board = new Integer[BoardSize][BoardSize];
        Random random = new Random();
        for (int row = 0; row < BoardSize; row++)
        {
            for (int col = 0; col < BoardSize; col++)
            {
                _board[row][col] = _symbols[random.nextInt(NumOfSymbols)];
            }
        }
    }

    @Override
    public int GetLength() {
        return BoardSize * BoardSize;
    }

    @Override
    public int GetImageAt(int position) {
        return _board[GetRowFor(position)][GetColumnFor(position)];
    }

    private int GetColumnFor(int position) {
        return position % BoardSize;
    }

    private int GetRowFor(int position) {
        return (int)Math.floor(position / BoardSize);
    }

    @Override
    public void RemoveFields(int startPosition, int endPosition) {
        int startRow = GetRowFor(startPosition);
        int startCol = GetColumnFor(startPosition);

        int endRow = GetRowFor(endPosition);
        int endCol = GetColumnFor(endPosition);

        if (startRow == endRow)
            RemoveRow(startCol, endCol, startRow);

        if (startCol == endCol)
            RemoveColumn(startRow, endRow, startCol);
    }

    private void RemoveColumn(int startRow, int endRow, int col) {
        if (startRow == endRow)
            return;

        Pair<Integer, Integer> rows = DetermineOrder(startRow, endRow);
        for (int i = rows.first; i <= rows.second; i++)
        {
            _board[i][col] = R.drawable.blank;
        }
    }

    private void RemoveRow(int startCol, int endCol, int row) {
        if (startCol == endCol)
            return;

        Pair<Integer, Integer> cols = DetermineOrder(startCol, endCol);
        for (int i = cols.first; i <= cols.second; i++)
        {
            _board[row][i] = R.drawable.blank;
        }
    }


    @Override
    public boolean CanRemoveFields(int prevTouch, int currTouch) {
        if (prevTouch < 0 || currTouch < 0)
            return false;

        int prevRow = GetRowFor(prevTouch);
        int prevCol = GetColumnFor(prevTouch);

        int currRow = GetRowFor(currTouch);
        int currCol = GetColumnFor(currTouch);

        if (prevRow == currRow) {
            return AreSymbolsInRowIdentical(prevRow, prevCol, currCol);
        }

        if (prevCol == currCol) {
            return AreSymbolsInColumnIdentical(prevCol, prevRow, currRow);
        }

        return false;
    }

    private boolean AreSymbolsInColumnIdentical(int col, int prevRow, int currRow) {
        Pair<Integer, Integer> rows = DetermineOrder(prevRow, currRow);
        for (int i = rows.first; i <= rows.second; i++)
        {
            if (_board[prevRow][col] != _board[i][col])
                return false;
        }
        return true;
    }

    private boolean AreSymbolsInRowIdentical(int row, int prevCol, int currCol) {
        Pair<Integer, Integer> cols = DetermineOrder(prevCol, currCol);
        for (int i = cols.first; i <= cols.second; i++)
        {
            if (_board[row][prevCol] != _board[row][i])
                return false;
        }
        return true;
    }

    public void RefillBlanks(int startPosition, int endPosition) {
        int startRow = GetRowFor(startPosition);
        int startCol = GetColumnFor(startPosition);

        int endRow = GetRowFor(endPosition);
        int endCol = GetColumnFor(endPosition);

        if (startRow == endRow)
            RefillRemovedRow(startCol, endCol, startRow);

        if (startCol == endCol)
            RefillRemovedColumn(startRow, endRow, startCol);
    }

    private void RefillRemovedColumn(int startRow, int endRow, int col) {
        Pair<Integer, Integer> rows = DetermineOrder(startRow, endRow);
        for (int i = rows.first; i <= rows.second; i++)
        {
            RefillColumn(i, col);
        }
    }

    private Pair<Integer, Integer> DetermineOrder(int x, int y)
    {
        int start, end;
        if (x > y)
        {
            start = y;
            end = x;
        }
        else
        {
            start = x;
            end = y;
        }

        return new Pair<>(start, end);
    }

    private void RefillColumn(int i, int y) {
        int currentRow = i;
        while (currentRow > 0)
        {
            _board[currentRow][y] = _board[currentRow - 1][y];
            currentRow--;
        }
        Random random = new Random();
        _board[0][y] = _symbols[random.nextInt(NumOfSymbols)];
    }

    private void RefillRemovedRow(int startCol, int endCol, int row) {
        Pair<Integer, Integer> cols = DetermineOrder(startCol, endCol);
        for (int i = cols.first; i <= cols.second; i++)
        {
            RefillColumn(row, i);
        }
    }


}
