package com.example.tomz4th_chaiyot.projectemergencyuser.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.ServiceListManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.view.ServiceListItem;

/**
 * Created by toMz4th-ChaiYot on 12/4/2016.
 */

public class ServiceListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        if (ServiceListManager.getInstance().getDao() == null)
            return 0;
        if (ServiceListManager.getInstance().getDao().getService() == null)
            return 0;
        return ServiceListManager.getInstance().getDao().getService().size();
    }

    @Override
    public Object getItem(int position) {
        return ServiceListManager.getInstance().getDao().getService().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceListItem item;
        if (convertView != null)
            item = (ServiceListItem) convertView;
        else
            item = new ServiceListItem(parent.getContext());

        ServiceDao dao = (ServiceDao) getItem(position);
        item.setTvName(dao.getServiceName());
        item.setTvDetail(dao.getServiceDetail());
        item.setTvAdd(dao.getServiceAdd());

        return item;
    }
}
