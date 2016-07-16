package com.tjusyj.listviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SmartAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int count = 500;

    public SmartAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addRows(int n) {
        count += n;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.list_item, parent, false);
        ((TextView) view.findViewById(R.id.message)).setText("Row " + (position + 1));
        ((TextView) view.findViewById(R.id.message2)).setText("   ---tjusyj");
        return view;
    }
}
