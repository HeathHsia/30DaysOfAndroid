package com.heath.playlocalvideo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private MyAdapter adapter;
    private ArrayList<ItemBean> dataArr = new ArrayList<ItemBean>();
    private int[]  backImgSrcArr = new int[]{R.mipmap.video_screenshot01, R.mipmap.video_screenshot02, R.mipmap.video_screenshot03,R.mipmap.video_screenshot04,R.mipmap.video_screenshot05,R.mipmap.video_screenshot06};
    private String[] titleArr = new String[]{"Introduce 3DS Mario", "Emoji Among Us", "Seals Documentary", "Adventure Time","Facebook HQ", "Lijiang Lugu Lake"};
    private String[] sourceArr = new String[]{"Youtube - 06:32","Vimeo - 3:34","Vine - 00:06","Youtube - 02:39","Facebook - 10:20","Allen - 20:30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < 6; i++) {
            ItemBean bean = new ItemBean(backImgSrcArr[i],titleArr[i],sourceArr[i]);
            dataArr.add(bean);
        }
        adapter = new MyAdapter(MainActivity.this, dataArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
//                     Uri uri = Uri.parse("android.resource://" + getPackageName()+"/"+R.raw.emojizone);
                     Uri uri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
                     Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                     intent.setDataAndType(uri, "video/mp4");
                     MainActivity.this.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                     e.printStackTrace();
                }

            }
        });

    }
}
