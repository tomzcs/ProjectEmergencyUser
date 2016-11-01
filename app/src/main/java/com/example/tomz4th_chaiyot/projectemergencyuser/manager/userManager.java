package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class userManager {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    UsersCollectionDao dao;


    private Context mContext;



    public userManager() {

        mContext = Contextor.getInstance().getContext();

        sp = mContext.getSharedPreferences("user",Context.MODE_PRIVATE);
        editor = sp.edit();
        LoadCache();
    }

    public void SaveCache(){
        UsersCollectionDao cacheDao = new UsersCollectionDao();
        if (dao != null &dao.getUser() != null)
            cacheDao.setUser(dao.getUser());

        String json = new Gson().toJson(cacheDao);


        editor.putString("user_data_json" ,json);
        editor.apply();

    }

    public void LoadCache(){

        String json = sp.getString("user_data_json",null);
        if (json == null){
            return;
        }
        dao = new Gson().fromJson(json, UsersCollectionDao.class);
    }

    public UsersCollectionDao getDao() {
        return dao;
    }

    public void setDao(UsersCollectionDao dao) {
        this.dao = dao;
    }

    public void logOut(){
        editor.clear();
        editor.apply();
    }
}
