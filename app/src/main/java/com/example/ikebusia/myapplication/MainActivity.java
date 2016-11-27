package com.example.ikebusia.myapplication;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int prevTouch = -1;
    private int currTouch = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        final IBoardManager boardManager = BoardManager.Create();
        gridview.setAdapter(new ImageAdapter(this, boardManager));

        gridview.setOnTouchListener(new AdapterView.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        if (gridview.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY()) != -1)
                        {
                            prevTouch = gridview.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
                        }
                        Toast.makeText(MainActivity.this, "row: " + prevTouch / 8 + " col: " + prevTouch % 8, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (gridview.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY()) != -1)
                        {
                            currTouch = gridview.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
                            if (boardManager.CanRemoveFields(prevTouch, currTouch))
                            {
                                boardManager.RemoveFields(prevTouch, currTouch);
                                gridview.invalidateViews();
                                SystemClock.sleep(1000);
                                boardManager.RefillBlanks(prevTouch, currTouch);
                                Toast.makeText(MainActivity.this, "row: " + currTouch / 8 + " col: " + currTouch % 8, Toast.LENGTH_SHORT).show();
                                prevTouch = -1;
                                currTouch = -1;
                            }
                        }
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

/*        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                boardManager.RemoveFields(position, position + 1);
                gridview.invalidateViews();
            }
        });*/
    }
}
