package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.RequestCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HistoryListManager {

    private static HistoryListManager instance;

    public static HistoryListManager getInstance() {
        if (instance == null)
            instance = new HistoryListManager();
        return instance;
    }

    private Context mContext;
    private RequestCollectionDao dao;

    private HistoryListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public RequestCollectionDao getDao() {
        return dao;
    }

    public void setDao(RequestCollectionDao dao) {
        this.dao = dao;
    }
}
