package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ServiceListManager {

    private static ServiceListManager instance;

    public static ServiceListManager getInstance() {
        if (instance == null)
            instance = new ServiceListManager();
        return instance;
    }

    private Context mContext;
    ServiceCollectionDao dao;

    private ServiceListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public ServiceCollectionDao getDao() {
        return dao;
    }

    public void setDao(ServiceCollectionDao dao) {
        this.dao = dao;
    }
}
