package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CommentCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CommentListManager {

    private static CommentListManager instance;

    public static CommentListManager getInstance() {
        if (instance == null)
            instance = new CommentListManager();
        return instance;
    }

    private Context mContext;
    private CommentCollectionDao dao;

    private CommentListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public CommentCollectionDao getDao() {
        return dao;
    }

    public void setDao(CommentCollectionDao dao) {
        this.dao = dao;
    }
}
