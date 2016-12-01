package com.example.tomz4th_chaiyot.projectemergencyuser.manager;

import android.content.Context;
import com.example.tomz4th_chaiyot.projectemergencyuser.manager.http.ApiService;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {

    private static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private Context mContext;
    private ApiService service;

    private HttpManager() {
        mContext = Contextor.getInstance().getContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.1:8080/db_connection/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);
    }

    public ApiService getService(){
        return service;
    }

}
