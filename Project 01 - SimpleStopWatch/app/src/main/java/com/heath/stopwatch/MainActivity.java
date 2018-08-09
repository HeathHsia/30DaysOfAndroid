package com.heath.stopwatch;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView timeText;
    private ImageButton playBtn;
    private ImageButton pauseBtn;
    private Timer timer;
    private double time = 0.0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeText = (TextView) findViewById(R.id.timetext);
        pauseBtn = (ImageButton) findViewById(R.id.pause);
        playBtn = (ImageButton) findViewById(R.id.play);
        timer = new Timer();
    }

    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.reset :
            {
                Log.d("onClick", "reset");
                timeText.setText(R.string.text_default);
                playBtn.setEnabled(true);
                pauseBtn.setEnabled(true);
                reset();
            }
                break;
            case R.id.play :
            {
                start();
                playBtn.setEnabled(false);
                pauseBtn.setEnabled(true);

            }
                break;
            case R.id.pause :
            {
                pause();
                playBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
            }
                break;
            default: break;
        }
    }

    private void reset () {
        pause();
        time = 0.0;
    }

    private void start () {
        try {
            if (timer == null) {
                timer = new Timer();
            }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message msg = Message.obtain(handler, new Runnable() {
                        @Override
                        public void run() {
                            time += 0.1;
                            String timeStr = String.format("%.1f", time);
                            timeText.setText(timeStr);
                        }
                    });
                    msg.sendToTarget();
                }
            }, 0, 1000);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    private void pause () {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
