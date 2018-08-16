package com.heath.pulltorefresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SwipeRefreshLayout swipeRefresh;
    private List<String> dataList = new ArrayList<String>(Arrays.asList("😀😁😅😂", "😄😉😊😍", "😘😙😜😝", "😎😊😳😅"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        final MyAdapter myAdapter = new MyAdapter(this, dataList);
        listView.setAdapter(myAdapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for (int i = 0; i < 4; i++) {
                    dataList.add(dataList.get(i));
                }
                myAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       swipeRefresh.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }
}
