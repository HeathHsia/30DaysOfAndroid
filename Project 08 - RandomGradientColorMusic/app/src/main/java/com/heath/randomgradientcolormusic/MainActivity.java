package com.heath.randomgradientcolormusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = (MyView) findViewById(R.id.my_view);

    }

    /**
     * BackView 点击方法
     * @param view
     */
    public void myViewClick(View view) {
        Log.d("click", "view");
        myView.colors = new int[]{getResources().getColor(R.color.colorGreen), getResources().getColor(R.color.colorRed), getResources().getColor(R.color.colorBlue)};
        myView.invalidate();
    }
}
