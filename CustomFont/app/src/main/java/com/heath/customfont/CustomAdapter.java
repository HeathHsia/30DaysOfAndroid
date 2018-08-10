package com.heath.customfont;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<String> dataList;

    public CustomAdapter (Context context, List dataArr) {
        inflater = LayoutInflater.from(context);
        dataList = dataArr;
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item, viewGroup, false);
            holder = new ViewHolder();
            holder.textView = view.findViewById(R.id.text);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        String textStr = dataList.get(i);
        holder.textView.setText(textStr);
        return view;
    }

    public final class ViewHolder {
        TextView textView;
    }
}
