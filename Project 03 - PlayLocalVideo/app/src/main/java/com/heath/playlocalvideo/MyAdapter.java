package com.heath.playlocalvideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ItemBean> dataList;

    public MyAdapter (Context context, List<ItemBean> list) {
        inflater = LayoutInflater.from(context);
        dataList = list;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.backImg = convertView.findViewById(R.id.backImg);
            holder.titleView = convertView.findViewById(R.id.titleView);
            holder.sourceView = convertView.findViewById(R.id.sourceView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ItemBean itemBean = dataList.get(position);
        holder.backImg.setImageResource(itemBean.backImgSrc);
        holder.titleView.setText(itemBean.titleStr);
        holder.sourceView.setText(itemBean.sourceStr);
        return convertView;
    }

    private final class ViewHolder {
        ImageView backImg;
        TextView titleView;
        TextView sourceView;
    }
}
