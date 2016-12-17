package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ComplaintCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ComplaintListManager {

    private static ComplaintListManager instance;

    public static ComplaintListManager getInstance() {
        if (instance == null)
            instance = new ComplaintListManager();
        return instance;
    }

    private Context mContext;
    private ComplaintCollectionDao dao;

    private ComplaintListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public ComplaintCollectionDao getDao() {
        return dao;
    }

    public void setDao(ComplaintCollectionDao dao) {
        this.dao = dao;
    }
}
