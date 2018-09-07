package com.heath.randomgradientcolormusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MyView myView;

    // 渐变数据源
    private ArrayList<int[]> colors = new ArrayList<int[]>();
    // 定时器
    private Timer timer;
    // index
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = findViewById(R.id.my_view);
        // 初始化渐变数据源
        colors.add(new int[]{getResources().getColor(R.color.colorGreen), getResources().getColor(R.color.colorRed), getResources().getColor(R.color.colorBlue)});
        colors.add(new int[]{getResources().getColor(R.color.colorGreen), getResources().getColor(R.color.colorBlue), getResources().getColor(R.color.colorRed)});
        colors.add(new int[]{getResources().getColor(R.color.colorRed), getResources().getColor(R.color.colorBlue), getResources().getColor(R.color.colorGreen)});
        colors.add(new int[]{getResources().getColor(R.color.colorRed), getResources().getColor(R.color.colorGreen), getResources().getColor(R.color.colorBlue)});
        colors.add(new int[]{getResources().getColor(R.color.colorBlue), getResources().getColor(R.color.colorGreen), getResources().getColor(R.color.colorRed)});
        colors.add(new int[]{getResources().getColor(R.color.colorBlue), getResources().getColor(R.color.colorRed), getResources().getColor(R.color.colorGreen)});
        // 添加定时器
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // UI Thread update UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myViewClick(myView);
                    }
                });
            }
        }, 100,100);
    }

    public void myViewClick(View view) {
        Log.d("Update", "Color");
        if (index == 6) {
            index = 0;
        }
        myView.colors = colors.get(index);
        myView.invalidate();
        index += 1;
    }
}
