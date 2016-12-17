package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.ServiceCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ServiceListsManager {

    private static ServiceListsManager instance;

    public static ServiceListsManager getInstance() {
        if (instance == null)
            instance = new ServiceListsManager();
        return instance;
    }

    private Context mContext;
    ServiceCollectionDao dao;

    private ServiceListsManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public ServiceCollectionDao getDao() {
        return dao;
    }

    public void setDao(ServiceCollectionDao dao) {
        this.dao = dao;
    }
}
