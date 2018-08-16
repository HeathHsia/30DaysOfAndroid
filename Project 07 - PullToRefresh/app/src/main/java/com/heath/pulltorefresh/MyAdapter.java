package com.heath.pulltorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<String> mdataList;
    private LayoutInflater inflater;

    public MyAdapter (Context context, List<String> dataList) {
        inflater = LayoutInflater.from(context);
        mdataList = dataList;
    }

    @Override
    public Object getItem(int position) {
        return mdataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mdataList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }else  {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(mdataList.get(position));
        return convertView;
    }

    private final class ViewHolder {
        TextView textView;
    }

}
