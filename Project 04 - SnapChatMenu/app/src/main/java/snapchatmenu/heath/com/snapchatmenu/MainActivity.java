package snapchatmenu.heath.com.snapchatmenu;

import android.content.pm.ApplicationInfo;
import android.graphics.ImageFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.HorizontalScrollView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Camera.PreviewCallback {

    private HorizontalScrollView scrollView;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (HorizontalScrollView) findViewById(R.id.rootScrollView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // 打开摄像头

                try {
                    camera = Camera.open();
                    camera.setPreviewDisplay(surfaceHolder);
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setPreviewSize(1920, 1080);
                    parameters.setPreviewFormat(ImageFormat.NV21);
                    parameters.setPictureSize(1920, 1080);
                    camera.setDisplayOrientation(90);
                    parameters.setRotation(90);
                    List<Integer> list = parameters.getSupportedPreviewFormats();
                    for (Integer val : list) {
                        Log.i("支持的格式", "" + val);
                    }
                    List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
                    if (sizeList.size() > 1) {
                        Iterator<Camera.Size> itor = sizeList.iterator();
                        while (itor.hasNext()) {
                            Camera.Size cur = itor.next();
                            Log.i("预览大小", "size==" + cur.width + "--" + cur.height);
                        }
                    }
                    camera.setParameters(parameters);
                    camera.setPreviewCallback((Camera.PreviewCallback) MainActivity.this);
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camera.setPreviewCallback(null);
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        });
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}
