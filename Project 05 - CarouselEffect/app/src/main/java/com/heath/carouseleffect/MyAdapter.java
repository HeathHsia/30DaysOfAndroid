package com.heath.carouseleffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int[] dataList;
    private Context mcontext;

    public MyAdapter (Context context, int[] data) {
        inflater = LayoutInflater.from(context);
        dataList = data;
        mcontext = context;
    }


    @Override
    public Object getItem(int position) {
        return dataList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return dataList.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.imgView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap = BitmapFactory.decodeResource(mcontext.getResources(), dataList[position]);
        viewHolder.imageView.setBackground(new BitmapDrawable(bitmap));
        return convertView;
    }

    private final class ViewHolder {
        ImageView imageView;
    }
}
