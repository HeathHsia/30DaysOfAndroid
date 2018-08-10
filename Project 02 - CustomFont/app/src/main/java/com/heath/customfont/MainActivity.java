package com.heath.customfont;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] textList = {"30 Days Android", "这些字体特别适合打「奋斗」和「理想」", "谢谢「造字工房」，本案例不涉及商业使用", "使用到造字工房劲黑体，致黑体，童心体", "呵呵，再见 See you next Project", "Github 给个Star",
            "测试测试测试测试测试测试",
            "123",
            "Heath",
            "@@@@@@"};
    private int index = 0;
    private String[] fontList = {"font/MFTongXin_Noncommercial-Regular.ttf","font/MFJinHei_Noncommercial-Regular.ttf","font/MFZhiHei_Noncommercial-Regular.ttf","font/Gaspar Regular.otf"};
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        List dataArrList = Arrays.asList(textList);
        adapter = new CustomAdapter(MainActivity.this, dataArrList);
        listView.setAdapter(adapter);
    }

    /**
     * Change Font
     * @param view
     */
    public void changeFont (View view) {
        Log.d("randomIndex",fontList[index]);
        Log.d("ChangeFont", "Change Font");
        for (int i = 0; i < listView.getChildCount(); i++) {
            CustomAdapter.ViewHolder holder = (CustomAdapter.ViewHolder) listView.getChildAt(i).getTag();
            Typeface typeface = Typeface.createFromAsset(getAssets(), fontList[index]);
            holder.textView.setTypeface(typeface);
        }
        if (index == 3) {
            index = 0;
        }else {
            index +=1 ;
        }
    }
}
