package com.heath.carouseleffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ImageView backImg;
    private GridView listView;
    private int[] dataList = new int[]{R.mipmap.bodyline, R.mipmap.darkvarder, R.mipmap.dudu, R.mipmap.hello, R.mipmap.hhhhh, R.mipmap.run, R.mipmap.wave};

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backImg = (ImageView) findViewById(R.id.backImg);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blue);
        Bitmap blurBitMap = this.bitmapBlur(this, bitmap, 25);
        backImg.setBackground(new BitmapDrawable(blurBitMap));
        listView = (GridView) findViewById(R.id.listview);
        setGrideView();
        MyAdapter adapter = new MyAdapter(MainActivity.this, dataList);
        listView.setAdapter(adapter);
    }

    private void setGrideView () {
        int size = dataList.length;
        int gridviewWidth = (int) (size * (600 + 20));
        int itemWidth = (int) 600;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(params);
        listView.setColumnWidth(itemWidth);
        listView.setHorizontalSpacing(20);
        listView.setStretchMode(GridView.NO_STRETCH);
        listView.setNumColumns(size);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap bitmapBlur (Context context, Bitmap source, int radius) {
        Bitmap inputBitmap = source;
        RenderScript renderScript = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBitmap);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(inputBitmap);
        renderScript.destroy();
        return inputBitmap;
    }
}
