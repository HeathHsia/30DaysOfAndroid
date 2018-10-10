package com.heath.imagescroller;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;


public class MyZoomImageView extends AppCompatImageView {

    /** ImageView高度 height */
    private int imgHeight;
    /** ImageView宽度 width */
    private int imgWidth;
    /** 图片高度  */
    private int intrinsicHeight;
    /** 图片宽度 */
    private int intrinsicWidth;
    /** 最大缩放级别 */
    private float mMaxScale = 2.0f;
    /** 最小缩放级别 */
    private float mMinScale = 0.2f;
    /** 用于记录拖拉图片移动的坐标位置 */
    private Matrix matrix = new Matrix();
    /** 用于记录图片要进行拖拉时候的坐标位置 */
    private Matrix currentMatrix = new Matrix();
    /** 记录第一次点击的时间 */
    private long firstTouchTime = 0;
    /** 时间点击的间隔 */
    private int intervalTime = 250;
    /** 第一次点完坐标 */
    private PointF firstPointF;


    /**
     *  初始化方法
     */
    public MyZoomImageView (Context context) {
        super(context);
        initUI();
    }

    public MyZoomImageView (Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public MyZoomImageView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI();
    }

    // Init UI
    public void initUI () {
        this.setScaleType(ScaleType.FIT_CENTER);
        this.setOnTouchListener(new TouchListener());

        getImageWidthHeight();
        getIntrinsicWidthHeight();

    }

    /**
     * 获取ImageView的宽高
      */
    private void getImageWidthHeight () {
        ViewTreeObserver vto2 = getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                imgWidth = getWidth();
                imgHeight = getHeight();
            }
        });
    }

    /**
     * 获取图片内在宽高
     */
    private void getIntrinsicWidthHeight () {
        Drawable drawable = this.getDrawable();
        // 初始化bitmap的宽高
        intrinsicHeight = drawable.getIntrinsicHeight();
        intrinsicWidth = drawable.getIntrinsicWidth();
    }

    // 图片双击事件
    private void setDoubleTouchEvent (MotionEvent event) {
        float values[] = new float[9];
        matrix.getValues(values);
        // 存储当前时间
        long currentTime = System.currentTimeMillis();
        // 判断两次点击间隔时间是否符合
        if (currentTime - firstTouchTime >= intervalTime) {
            firstTouchTime = currentTime;
            firstPointF = new PointF(event.getX(), event.getY());
        }else  {
            // 判断两次点击之间的距离是否小鱼30f
            if (Math.abs(event.getX() - firstPointF.x) < 30f && Math.abs(event.getY() - firstPointF.y) < 30f) {
                // 判断当前缩放比例与最大最小的比例
                if (values[Matrix.MSCALE_X] < mMaxScale) {
                    matrix.postScale(mMaxScale / values[Matrix.MSCALE_X], mMaxScale / values[Matrix.MSCALE_X], event.getX(), event.getY());
                }else  {
                    matrix.postScale(mMinScale / values[Matrix.MSCALE_X], mMinScale / values[Matrix.MSCALE_X], event.getX(), event.getY());
                }
            }
        }

    }

    // 设置img开始呈现模式
    private  void  makeImageViewFit () {
        if (getScaleType() != ScaleType.MATRIX) {
            setScaleType(ScaleType.MATRIX);
            matrix.postScale(1.0f, 1.0f, imgWidth / 2, imgHeight / 2);
        }
    }

    // 设置图片的最大和最小缩放比例
    public void setPicZoomHeightWidth (float mMaxScale, float mMinScale) {
        this.mMaxScale = mMaxScale;
        this.mMinScale = mMinScale;
    }

    /**
     * 添加touch接口
     */
    private final class TouchListener implements OnTouchListener {

        // 记录当前照片模式 放大 缩小
        private int mode = 0; // 初始状态

        // 拖拉照片模式
        private static final int MODE_DRAG = 1;

        // 放大缩小照片模式
        private static final int MODE_ZOOM = 2;

        // 用于记录开始的坐标
        private PointF startPoint = new PointF();

        // 两个手指的开始距离
        private float startDis;

        // 两个手指的中间点
        private PointF midPoint;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 手指向下压屏幕
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    // 记录ImageView 当前的移动位置
                    currentMatrix.set(getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    matrix.set(currentMatrix);
                    makeImageViewFit();
                    break;
                // 手指在屏幕上移动，该事件一直触发执行
                case MotionEvent.ACTION_MOVE:
                    // 拖拉图片
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPoint.x;
                        float dy = event.getY() - startPoint.y;
                        // 在没有移动之前的位置上进行移动
                        matrix.set(currentMatrix);
                        float[] values = new float[9];
                        matrix.getValues(values);
                        dx = checkBxBound(values, dx);
                        dy = checkByBound(values, dy);
                        matrix.postTranslate(dx, dy);
                    }else if (mode == MODE_ZOOM) {
                        // 结束距离
                        float endDis = distance(event);
                        if (endDis > 10f) {
                            float scale = endDis / startDis;
                            matrix.set(currentMatrix);

                            float[] values = new float[9];
                            matrix.getValues(values);

                            scale = checkFitScale(scale, values);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    setDoubleTouchEvent(event);
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    float[] values = new float[9];
                    matrix.getValues(values);
                    makeImgCenter(values);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    startDis = distance(event);
                    if (startDis > 10f) {
                        midPoint = mid(event);
                        currentMatrix.set(getImageMatrix());
                    }
                    break;
            }
            setImageMatrix(matrix);
            return true;
        }

        // 计算两个手指间的距离
        private float distance (MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            return (float)Math.sqrt(dx * dx + dy * dy);
        }

        // 计算两个手指间的中间点
        private PointF mid (MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }

        // 和当前矩阵对比，检验dy，使图像移动后不超过Image边界
        private float checkByBound (float[] values, float dy) {
            float height = imgHeight;
            if (intrinsicHeight * values[Matrix.MSCALE_Y] < height) {
                return 0;
            }
            if (values[Matrix.MTRANS_Y] + dy > 0) {
                dy = -values[Matrix.MTRANS_Y];
            }else if (values[Matrix.MTRANS_Y] + dy < - (intrinsicHeight * values[Matrix.MSCALE_Y] - height)) {
                dy = - (intrinsicHeight * values[Matrix.MSCALE_Y] - height) - values[Matrix.MTRANS_Y];
            }
            return dy;
        }

        // 和当前举证对比，校验dx, 使图像移动后不超过Image边界
        private  float checkBxBound (float[] values, float dx) {
            float width = imgWidth;
            if (intrinsicWidth * values[Matrix.MSCALE_X] < width) {
                return 0;
            }
            if (values[Matrix.MTRANS_X] + dx > 0) {
                dx = -values[Matrix.MTRANS_X];
            }else if (values[Matrix.MTRANS_X] + dx < -(intrinsicWidth * values[Matrix.MSCALE_X] - width)) {
                dx = -(intrinsicWidth * values[Matrix.MSCALE_X] - width) - values[Matrix.MTRANS_X];
            }
            return dx;
        }

        // 检验scala，使图像缩放后不会超出最大倍数
        private float checkFitScale (float scale, float[] values) {
            if (scale * values[Matrix.MSCALE_X] > mMaxScale)
                scale = mMaxScale / values[Matrix.MSCALE_X];
            else if (scale * values[Matrix.MSCALE_X] < mMinScale)
                scale = mMinScale / values[Matrix.MSCALE_X];
            return scale;
        }

        // 使图片居中
        private void makeImgCenter (float[] values) {
            // 缩放后的图片的宽高
            float zoomY = intrinsicHeight * values[Matrix.MSCALE_Y];
            float zoomX = intrinsicWidth * values[Matrix.MSCALE_X];
            // 图片左上角坐标
            float leftY = values[Matrix.MTRANS_Y];
            float leftX = values[Matrix.MTRANS_X];
            // 图片右下角坐标
            float rightY = leftY + zoomY;
            float rightX = leftX + zoomX;
            // 使图片垂直居中
            if (zoomY < imgHeight) {
                float marY = (imgHeight - zoomY) / 2.0f;
                matrix.postTranslate(0, marY - leftY);
            }
            // 使图片水平居中
            if (zoomX < imgWidth) {
                float marX = (imgWidth - zoomX) / 2.0f;
                matrix.postTranslate(marX - leftX, 0);
            }
            // 使图片缩放后上下不留白
            if (zoomY >= imgHeight) {
                if (leftY > 0) {
                    // 判断图片上面留白
                    matrix.postTranslate(0, -leftY);
                }
                if (rightY < imgHeight) {
                    // 判断图片下面留白
                    matrix.postTranslate(0, imgHeight - rightY);
                }
            }

            // 图片左右不留白
            if (zoomX >= imgWidth) {
                if (leftX > 0) {
                    matrix.postTranslate(-leftX, 0);
                }
                if (rightX < imgWidth) {
                    matrix.postTranslate(imgWidth - rightX, 0);
                }
            }
        }
    }

}
