package com.example.tomz4th_chaiyot.projectemergencyuser.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tomz4th_chaiyot.projectemergencyuser.view.ServiceListItem;

/**
 * Created by toMz4th-ChaiYot on 11/3/2016.
 */

public class ServiceListAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 10000;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceListItem item;
        if (convertView != null){
            item = (ServiceListItem) convertView;
        }else{
            item = new ServiceListItem(parent.getContext());
        }
        return item;
    }
}
