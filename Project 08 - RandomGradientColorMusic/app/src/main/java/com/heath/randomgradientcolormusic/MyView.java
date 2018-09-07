package com.heath.randomgradientcolormusic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    public int[] colors = new int[]{getResources().getColor(R.color.colorRed), getResources().getColor(R.color.colorBlue), getResources().getColor(R.color.colorGreen)};

    public MyView (Context context) {
        super(context);
    }

    public MyView (Context context, @Nullable AttributeSet attr) {
        super(context, attr);
    }
    // 初始化
    public MyView (Context context, @Nullable AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);
    }

    /**
     *  Custom Gradient View
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint = new Paint();
        LinearGradient backGradient = new LinearGradient(0, 0, width, height, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(backGradient);
        canvas.drawRect(0,0, width, height, paint);
    }
}
