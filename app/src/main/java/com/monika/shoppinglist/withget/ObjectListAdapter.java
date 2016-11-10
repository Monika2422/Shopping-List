package com.monika.shoppinglist.withget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.monika.shoppinglist.R;
import com.monika.shoppinglist.content.Object;

import java.util.List;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public class ObjectListAdapter  extends BaseAdapter {
    public static final String TAG = ObjectListAdapter.class.getSimpleName();
    private final Context mContext;
    private List<Object> mobjectList;

    public ObjectListAdapter(Context context, List<Object> objects) {
        mContext = context;
        mobjectList = objects;
    }

    @Override
    public int getCount() {
        return mobjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return mobjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View objectLayout = LayoutInflater.from(mContext).inflate(R.layout.object_detail, null);
        ((TextView) objectLayout.findViewById(R.id.object_text)).setText(mobjectList.get(position).getName());
        Log.d(TAG, "getView " + position);
        return objectLayout;
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
