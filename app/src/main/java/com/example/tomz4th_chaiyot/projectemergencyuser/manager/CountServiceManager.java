package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CountServiceManager {

    private static CountServiceManager instance;

    public static CountServiceManager getInstance() {
        if (instance == null)
            instance = new CountServiceManager();
        return instance;
    }

    private Context mContext;
    private ServiceCollectionDao dao;

    private CountServiceManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public ServiceCollectionDao getDao() {
        return dao;
    }

    public void setDao(ServiceCollectionDao dao) {
        this.dao = dao;
    }
}
