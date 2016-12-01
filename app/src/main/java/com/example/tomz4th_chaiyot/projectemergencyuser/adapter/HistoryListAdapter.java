package com.example.tomz4th_chaiyot.projectemergencyuser.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RequestDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.HistoryListManager;
import com.example.tomz4th_chaiyot.projectemergencyuser.view.HistoryListItem;

/**
 * Created by toMz4th-ChaiYot on 11/24/2016.
 */

public class HistoryListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        if (HistoryListManager.getInstance().getDao() == null)
            return 0;
        if (HistoryListManager.getInstance().getDao().getRequest() == null)
            return 0;
        return HistoryListManager.getInstance().getDao().getRequest().size();
    }

    @Override
    public Object getItem(int position) {
        return HistoryListManager.getInstance().getDao().getRequest().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryListItem item;
        if (convertView != null)
            item = (HistoryListItem) convertView;
        else
            item = new HistoryListItem(parent.getContext());

        RequestDao dao = (RequestDao) getItem(position);
        item.setTvRequestDetail(dao.getRequestDetail());
        item.setTvRequestDetailCar(dao.getRequestDetailCar());
        item.setTvStatus(dao.getStatusName());
        item.setTvDate(dao.getRequestCreatedAt());
        item.setTvUserIdService(dao.getUserName());

        return item;
    }
}
