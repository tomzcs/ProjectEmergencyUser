package com.example.tomz4th_chaiyot.projectemergencyuser.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.ServiceListManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.ServiceListsManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.view.ServiceListItem;
import com.example.tomz4th_chaiyot.projectemergencyuser.view.ServiceListItems;

/**
 * Created by toMz4th-ChaiYot on 12/4/2016.
 */

public class ServiceListAllAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        if (ServiceListsManager.getInstance().getDao() == null)
            return 0;
        if (ServiceListsManager.getInstance().getDao().getService() == null)
            return 0;
        return ServiceListsManager.getInstance().getDao().getService().size();
    }

    @Override
    public Object getItem(int position) {
        return ServiceListsManager.getInstance().getDao().getService().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceListItems item;
        if (convertView != null)
            item = (ServiceListItems) convertView;
        else
            item = new ServiceListItems(parent.getContext());

        ServiceDao dao = (ServiceDao) getItem(position);
        item.setTvName(dao.getServiceName());
        item.setTvDetail(dao.getServiceDetail());
        item.setTvAdd(dao.getServiceAdd());
        item.setImgPhoto(dao.getUserImg());
        item.setImgPhotoService(dao.getServiceImg());

        return item;
    }
}
