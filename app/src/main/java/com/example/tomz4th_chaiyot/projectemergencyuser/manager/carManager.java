package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tomz4th_chaiyot.projectemergencyuser.dao.CarsCollectionDao;
import com.example.tomz4th_chaiyot.projectemergencyuser.dao.UsersCollectionDao;
import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class carManager {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    CarsCollectionDao daocar;


    private Context mContext;



    public carManager() {

        mContext = Contextor.getInstance().getContext();

        sp = mContext.getSharedPreferences("car",Context.MODE_PRIVATE);
        editor = sp.edit();
        LoadCache();
    }

    public void SaveCache(){
        CarsCollectionDao cacheDao = new CarsCollectionDao();
        if (daocar != null & daocar.getCar() != null)
            cacheDao.setCar(daocar.getCar());

        String json = new Gson().toJson(cacheDao);


        editor.putString("car_data_json" ,json);
        editor.apply();

    }

    public void LoadCache(){

        String json = sp.getString("car_data_json",null);
        if (json == null){
            return;
        }
        daocar = new Gson().fromJson(json, CarsCollectionDao.class);
    }

    public CarsCollectionDao getDao() {
        return daocar;
    }

    public void setDao(CarsCollectionDao dao) {
        this.daocar = dao;
    }

}
